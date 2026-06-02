package esq.hyplugin.setup;

import esq.hyplugin.HyPlugin;

/**
 * Orchestrates plugin startup and shutdown by fanning out to one focused registry
 * manager per domain.
 *
 * <p>Phasing mirrors the plugin lifecycle:
 * <ul>
 *   <li>Constructor / {@link #start()} → runs in {@code setup()}: assets, components,
 *       events, commands, interactions.</li>
 *   <li>{@link #initializeRuntime()} → runs in {@code start()}: ECS systems.</li>
 *   <li>{@link #shutdown()} → runs in {@code shutdown()}: release listeners / tasks.</li>
 * </ul>
 */
public class SetupManager {
    private final AssetRegistryManager assetRegistryManager;
    private final ComponentRegistryManager componentRegistryManager;
    private final EventRegistryManager eventRegistryManager;
    private final CommandRegistryManager commandRegistryManager;
    private final InteractionSetupManager interactionSetupManager;

    public SetupManager(HyPlugin plugin) {
        this.assetRegistryManager = new AssetRegistryManager(plugin);
        this.componentRegistryManager = new ComponentRegistryManager(plugin);
        this.eventRegistryManager = new EventRegistryManager(plugin);
        this.commandRegistryManager = new CommandRegistryManager(plugin);
        this.interactionSetupManager = new InteractionSetupManager(plugin);
        this.start();
    }

    /** setup() phase — everything except ECS systems. */
    protected void start() {
        registerAssets();
        registerComponents();
        registerEvents();
        registerCommands();
        registerInteractions();
    }

    public void registerAssets() {
        assetRegistryManager.register();
    }

    public void registerComponents() {
        componentRegistryManager.register();
    }

    public void registerEvents() {
        eventRegistryManager.register();
    }

    public void registerCommands() {
        commandRegistryManager.register();
    }

    public void registerInteractions() {
        interactionSetupManager.register();
    }

    /** start() phase — register ECS systems after worlds have loaded. */
    public void initializeRuntime() {
        componentRegistryManager.registerSystems();
    }

    /** shutdown() phase — unsubscribe listeners, stop background tasks, etc. */
    public void shutdown() {
        // Nothing to release in the boilerplate. Stop schedulers / unsubscribe here.
    }
}