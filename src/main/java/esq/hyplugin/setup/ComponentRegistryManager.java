package esq.hyplugin.setup;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import esq.hyplugin.HyPlugin;
import esq.hyplugin.examples.ExampleComponent;
import esq.hyplugin.examples.ExampleSystem;

/**
 * Registers ECS components (in {@link #register()}, called from {@code setup()}) and
 * ECS systems (in {@link #registerSystems()}, called from {@code start()}).
 *
 * <p>The split matters: components must exist before worlds load; systems must be
 * added after. Mixing them up is a common startup bug.
 */
public class ComponentRegistryManager {
    private final HyPlugin plugin;

    public ComponentRegistryManager(HyPlugin plugin) {
        this.plugin = plugin;
    }

    /** Register components here (called in {@code setup()}). */
    public void register() {
        // EXAMPLE — register the sample component and stash its ComponentType handle.
        // Replace with your own components, or delete this block + the examples package.
        ComponentType<EntityStore, ExampleComponent> exampleType =
                plugin.getEntityStoreRegistry().registerComponent(
                        ExampleComponent.class,
                        "ExampleComponent",   // Uppercase, globally-unique keyed id
                        ExampleComponent.CODEC);
        ExampleComponent.setComponentType(exampleType);
    }

    /** Register systems here (called in {@code start()}, after worlds load). */
    public void registerSystems() {
        // EXAMPLE — register the sample system against the component above.
        if (ExampleComponent.getComponentType() != null) {
            plugin.getEntityStoreRegistry()
                    .registerSystem(new ExampleSystem(ExampleComponent.getComponentType()));
        }
    }
}