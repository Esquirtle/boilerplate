package esq.hyplugin;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import esq.hyplugin.setup.SetupManager;

import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * Hymod - A Hytale server plugin providing an in-game phone device framework.
 *
 * @author esquirtle
 * @version 1.0.0
 */
public class HyPlugin extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static HyPlugin instance;
    private SetupManager setupManager;

    public HyPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    public static HyPlugin getInstance() {
        return instance;
    }

    @Override
    protected void setup() {
        LOGGER.at(Level.INFO).log("[Hymod] Setting up...");
        this.setupManager = new SetupManager(this);
        LOGGER.at(Level.INFO).log("[Hymod] Setup complete!");
    }

    @Override
    protected void start() {
        if (setupManager != null) {
            setupManager.initializeRuntime();
        }
        LOGGER.at(Level.INFO).log("[Hymod] Started!");
        LOGGER.at(Level.INFO).log("=======================================");
        LOGGER.at(Level.INFO).log("=  ***    Powered by Hymod    ***  =");
        LOGGER.at(Level.INFO).log("=======================================");
    }

    @Override
    protected void shutdown() {
        LOGGER.at(Level.INFO).log("[Hymod] Shutting down...");
        if (setupManager != null) {
            setupManager.shutdown();
        }
        instance = null;
    }
}