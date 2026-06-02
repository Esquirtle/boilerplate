package esq.hyplugin;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import esq.hyplugin.setup.SetupManager;

import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * HyPlugin — a starter boilerplate for a Hytale server-side plugin.
 *
 * <p>Lifecycle:
 * <ul>
 *   <li>{@link #setup()}  — register components, assets, events, commands, interactions
 *       (called once, before worlds load).</li>
 *   <li>{@link #start()}  — register ECS systems / start runtime services
 *       (called once, after worlds load).</li>
 *   <li>{@link #shutdown()} — release listeners / background work.</li>
 * </ul>
 *
 * <p>All registration is delegated to {@link SetupManager}, which fans out to one
 * focused manager per domain (components, assets, events, commands, interactions).
 *
 * @author esquirtle
 * @version 1.0.0
 */
public class HyPlugin extends JavaPlugin {

    /** Prefix used for this plugin's log lines. Keep in sync with the manifest Name. */
    public static final String NAME = "HyPlugin";

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static HyPlugin instance;
    private SetupManager setupManager;

    public HyPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    /** Returns the running plugin instance. Available after construction. */
    public static HyPlugin getInstance() {
        return instance;
    }

    @Override
    protected void setup() {
        LOGGER.at(Level.INFO).log("[" + NAME + "] Setting up...");
        this.setupManager = new SetupManager(this);
        LOGGER.at(Level.INFO).log("[" + NAME + "] Setup complete!");
    }

    @Override
    protected void start() {
        if (setupManager != null) {
            // Systems must be registered in start() (after worlds load), not setup().
            setupManager.initializeRuntime();
        }
        LOGGER.at(Level.INFO).log("[" + NAME + "] Started!");
    }

    @Override
    protected void shutdown() {
        LOGGER.at(Level.INFO).log("[" + NAME + "] Shutting down...");
        if (setupManager != null) {
            setupManager.shutdown();
        }
        instance = null;
    }
}