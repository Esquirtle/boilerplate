package esq.hyplugin.examples;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * EXAMPLE — a persistent ECS component. Copy this as the template for your own
 * components, then delete it (and its registration in
 * {@code ComponentRegistryManager}).
 *
 * <p>Every entity component needs four things, all shown here:
 * <ol>
 *   <li>A no-arg <b>default constructor</b> (used by the registration factory).</li>
 *   <li>A <b>copy constructor</b> + {@link #clone()} (the ECS duplicates data via these).</li>
 *   <li>A {@link BuilderCodec} for save/load. Keyed IDs must be <b>Uppercase</b> and
 *       <b>globally unique across your whole plugin</b>.</li>
 *   <li>A static {@link ComponentType} handle, set once at registration, so other
 *       code can do {@code store.getComponent(ref, ExampleComponent.getComponentType())}.</li>
 * </ol>
 *
 * <p>Components are <b>pure data</b> — no game logic. Logic belongs in systems
 * (see {@link ExampleSystem}) or event handlers.
 */
public final class ExampleComponent implements Component<EntityStore> {

    // ── Codec (serialization) ───────────────────────────────────────────────
    public static final BuilderCodec<ExampleComponent> CODEC = BuilderCodec
            .builder(ExampleComponent.class, ExampleComponent::new)
            .append(new KeyedCodec<>("Counter", Codec.INTEGER),
                    (data, value) -> data.counter = value,
                    data -> data.counter)
            .add()
            .append(new KeyedCodec<>("Label", Codec.STRING),
                    (data, value) -> data.label = value,
                    data -> data.label)
            .add()
            .build();

    // ── Fields (the actual data) ────────────────────────────────────────────
    private int counter;
    private String label = "";

    // ── Constructors ────────────────────────────────────────────────────────
    /** Default constructor — required by the registration factory and codec. */
    public ExampleComponent() {
    }

    public ExampleComponent(int counter, @Nonnull String label) {
        this.counter = counter;
        this.label = label;
    }

    /** Copy constructor — used by {@link #clone()}. */
    public ExampleComponent(@Nonnull ExampleComponent source) {
        this.counter = source.counter;
        this.label = source.label;
    }

    @Nonnull
    @Override
    public Component<EntityStore> clone() {
        return new ExampleComponent(this);
    }

    // ── ComponentType handle ────────────────────────────────────────────────
    private static ComponentType<EntityStore, ExampleComponent> COMPONENT_TYPE;

    public static ComponentType<EntityStore, ExampleComponent> getComponentType() {
        return COMPONENT_TYPE;
    }

    public static void setComponentType(@Nonnull ComponentType<EntityStore, ExampleComponent> type) {
        COMPONENT_TYPE = type;
    }

    // ── Accessors ───────────────────────────────────────────────────────────
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Nullable
    public String getLabel() {
        return label;
    }

    public void setLabel(@Nonnull String label) {
        this.label = label;
    }
}
