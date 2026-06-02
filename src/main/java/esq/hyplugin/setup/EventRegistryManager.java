package esq.hyplugin.setup;
import esq.hyplugin.HyPlugin;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketFilter;


public class EventRegistryManager {
    private final HyPlugin plugin;

    public EventRegistryManager(HyPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {

    }
}
