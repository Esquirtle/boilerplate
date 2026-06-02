package esq.hyplugin.setup;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import esq.hyplugin.HyPlugin;

/**
 * Registers custom item interactions (called from {@code setup()}).
 *
 * <p>An interaction is a class extending {@code SimpleInstantInteraction} (or another
 * {@code Interaction} subtype) with a {@code BuilderCodec}, registered under a key that
 * an item's JSON references in its {@code Interactions} block. Typical shape:
 *
 * <pre>{@code
 * plugin.getCodecRegistry(Interaction.CODEC)
 *       .register("my_interaction", MyInteraction.class, MyInteraction.CODEC);
 * }</pre>
 *
 * and in the item JSON (shipped in your asset pack):
 *
 * <pre>{@code
 * "Interactions": { "Primary": { "Interactions": [ { "Type": "my_interaction" } ] } }
 * }</pre>
 *
 * Left empty by default — add interactions when your plugin has interactive items.
 */
public class InteractionSetupManager {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final HyPlugin plugin;

    public InteractionSetupManager(HyPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        // No custom interactions by default. See the class javadoc for the pattern.
    }
}