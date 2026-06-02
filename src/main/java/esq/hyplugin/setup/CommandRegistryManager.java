package esq.hyplugin.setup;
import esq.hyplugin.HyPlugin;
import com.hypixel.hytale.logger.HytaleLogger;


public class CommandRegistryManager {
    
    private final HyPlugin plugin;
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public CommandRegistryManager(HyPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
    }
}
