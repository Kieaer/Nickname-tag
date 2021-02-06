import arc.Core;
import arc.Events;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.mod.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static mindustry.Vars.netServer;

public class Tag extends Plugin {
    private final Map<String, Object> obj;

    public Tag(){
        String format = "%1: %2";
        String config =
                "# %1 - nickname\n"+
                "# %2 - message\n"+
                "# Example: [guest] %1: %2"+
                "format: "+format;
        if(!Core.settings.getDataDirectory().child("tag.yml").exists()) Core.settings.getDataDirectory().child("tag.yml").writeString(config);
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("tag.yml").readString()));

        Events.on(EventType.PlayerChatEvent.class, e -> Call.sendMessage(getValue().replace("%1", e.player.name).replace("%2",e.message)));
    }

    @Override
    public void init() {
        netServer.admins.addChatFilter((player, text) -> null);
    }

    public String getValue(){
        return obj != null && obj.get("format") != null ? (String) obj.get("format") : "%1: %2";
    }
}
