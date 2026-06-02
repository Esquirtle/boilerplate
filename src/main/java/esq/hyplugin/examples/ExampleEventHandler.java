package esq.hyplugin.examples;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import esq.hyplugin.HyPlugin;

import java.util.function.Consumer;

/**
 * EXAMPLE — a global event handler reacting to {@link PlayerDisconnectEvent}.
 *
 * <p>An event handler is just a {@link Consumer} of the event type. Register it in
 * {@code EventRegistryManager} via
 * {@code plugin.getEventRegistry().registerGlobal(SomeEvent.class, new Handler())}.
 *
 * <p>Use disconnect handlers to flush per-session state, cancel background work, or
 * remove the player from in-memory registries. Copy and delete as needed.
 */
public final class ExampleEventHandler implements Consumer<PlayerDisconnectEvent> {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    public void accept(PlayerDisconnectEvent event) {
        PlayerRef playerRef = event.getPlayerRef();
        if (playerRef == null) {
            return;
        }
        // Fine-level by default so it doesn't spam production logs.
        LOGGER.atFine().log("[" + HyPlugin.NAME + "] %s disconnected — cleanup hook ran",
                playerRef.getUsername());
    }
}
