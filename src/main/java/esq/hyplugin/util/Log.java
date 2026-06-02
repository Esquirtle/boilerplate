package esq.hyplugin.util;

import com.hypixel.hytale.logger.HytaleLogger;
import esq.hyplugin.HyPlugin;

import javax.annotation.Nonnull;

/**
 * Tiny logging helper that prefixes every line with {@code [PluginName]} and centralizes
 * level choice, so call sites stay terse and consistent.
 *
 * <pre>{@code
 * Log.info("Loaded %d things", count);
 * Log.warn("Missing config key %s", key);
 * Log.debug("verbose detail");           // FINE level — off in production by default
 * }</pre>
 *
 * <p>Prefer {@link #debug} for hot-path / per-event logs so production logs stay clean.
 */
public final class Log {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private static final String PREFIX = "[" + HyPlugin.NAME + "] ";

    private Log() {
    }

    public static void info(@Nonnull String msg, Object... args) {
        LOGGER.atInfo().log(PREFIX + fmt(msg, args));
    }

    public static void warn(@Nonnull String msg, Object... args) {
        LOGGER.atWarning().log(PREFIX + fmt(msg, args));
    }

    public static void error(@Nonnull String msg, Object... args) {
        LOGGER.atSevere().log(PREFIX + fmt(msg, args));
    }

    public static void error(@Nonnull Throwable cause, @Nonnull String msg, Object... args) {
        LOGGER.atSevere().withCause(cause).log(PREFIX + fmt(msg, args));
    }

    /** FINE level — verbose/diagnostic; not shown in production unless enabled. */
    public static void debug(@Nonnull String msg, Object... args) {
        LOGGER.atFine().log(PREFIX + fmt(msg, args));
    }

    private static String fmt(@Nonnull String msg, Object... args) {
        return args.length == 0 ? msg : String.format(msg, args);
    }
}
