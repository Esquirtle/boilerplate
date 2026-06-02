package esq.hyplugin.setup;

import com.hypixel.hytale.logger.HytaleLogger;
import esq.hyplugin.HyPlugin;
import esq.hyplugin.examples.ExampleCommand;

/**
 * Registers chat/console commands (called from {@code setup()}).
 */
public class CommandRegistryManager {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final HyPlugin plugin;

    public CommandRegistryManager(HyPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        // EXAMPLE — /hyexample. Replace with your own commands, or delete this line.
        plugin.getCommandRegistry().registerCommand(new ExampleCommand());
    }
}