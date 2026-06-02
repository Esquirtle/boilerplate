package esq.hyplugin.examples;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

/**
 * EXAMPLE — a player command: {@code /hyexample [times]}.
 *
 * <p>Demonstrates:
 * <ul>
 *   <li>{@link AbstractPlayerCommand} — runs on the executing player's world thread,
 *       so {@code store}/{@code ref} are safe to use directly.</li>
 *   <li>A {@link DefaultArg} (optional positional argument with a default value).</li>
 *   <li>A permission gate. Hytale also auto-generates one per command; this makes the
 *       node explicit. Operators have all permissions by default.</li>
 * </ul>
 *
 * <p>Registered in {@code CommandRegistryManager}. Copy and delete as needed.
 */
public final class ExampleCommand extends AbstractPlayerCommand {

    private final DefaultArg<Integer> timesArg;

    public ExampleCommand() {
        super("hyexample", "Example command — greets you N times.");
        requirePermission("hyplugin.command.hyexample");
        this.timesArg = withDefaultArg("times", "How many times to greet",
                ArgTypes.INTEGER, 1, "Default: 1");
    }

    @Override
    protected void execute(@Nonnull CommandContext context,
                           @Nonnull Store<EntityStore> store,
                           @Nonnull Ref<EntityStore> ref,
                           @Nonnull PlayerRef playerRef,
                           @Nonnull World world) {
        int times = Math.max(1, Math.min(10, timesArg.get(context)));
        for (int i = 0; i < times; i++) {
            context.sendMessage(Message.raw("Hello from HyPlugin!").color("#43D69A"));
        }
    }
}
