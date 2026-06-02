package esq.hyplugin.examples;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

/**
 * EXAMPLE — an ECS system that ticks every entity carrying {@link ExampleComponent}.
 *
 * <p>This extends {@link DelayedEntitySystem} (an interval ticker) instead of a
 * per-frame {@code EntityTickingSystem}, so it runs once per second — cheaper and
 * the common case for gameplay timers.
 *
 * <p>Key rules shown here:
 * <ul>
 *   <li>{@link #getQuery()} filters which entities reach {@link #tick}. Only entities
 *       with the queried component(s) are visited.</li>
 *   <li>Mutations go through the {@link CommandBuffer}, never the {@code Store} directly,
 *       for thread safety.</li>
 *   <li>Read the component for the current entity via {@code archetypeChunk.getComponent}.</li>
 * </ul>
 *
 * <p>Registered in {@code ComponentRegistryManager.registerSystems()} (called from
 * {@code start()}). Copy and delete as needed.
 */
public final class ExampleSystem extends DelayedEntitySystem<EntityStore> {

    private final ComponentType<EntityStore, ExampleComponent> componentType;

    public ExampleSystem(@Nonnull ComponentType<EntityStore, ExampleComponent> componentType) {
        super(1.0f); // seconds between ticks
        this.componentType = componentType;
    }

    @Override
    public void tick(float dt, int index,
                     @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                     @Nonnull Store<EntityStore> store,
                     @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        ExampleComponent component = archetypeChunk.getComponent(index, componentType);
        if (component == null) {
            return;
        }
        // Pure-data mutation on the component instance; the ECS persists it.
        component.setCounter(component.getCounter() + 1);
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(componentType);
    }
}
