package esq.hyplugin.setup;

import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import esq.hyplugin.HyPlugin;
import esq.hyplugin.examples.ExampleEventHandler;

/**
 * Registers global event handlers and packet adapters (called from {@code setup()}).
 *
 * <p>Packet filters (e.g. {@code PacketAdapters.registerInbound(...)}) can also be
 * wired here if your plugin intercepts protocol packets.
 */
public class EventRegistryManager {
    private final HyPlugin plugin;

    public EventRegistryManager(HyPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        // EXAMPLE — react to player disconnects. Replace/extend or delete.
        plugin.getEventRegistry().registerGlobal(
                PlayerDisconnectEvent.class, new ExampleEventHandler());
    }
}