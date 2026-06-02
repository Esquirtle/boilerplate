package esq.hyplugin.util;

import org.bson.BsonDocument;
import org.bson.BsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Minimal JSON config loader for a plugin's data directory, using the server-bundled
 * BSON parser (no extra dependency).
 *
 * <p>Usage in {@code setup()}:
 * <pre>{@code
 * Path dir = getDataDirectory();           // from JavaPlugin
 * PluginConfig cfg = PluginConfig.loadOrCreate(
 *         dir.resolve("config.json"),
 *         "{ \"greeting\": \"Hello\", \"maxUses\": 3 }");
 * String greeting = cfg.getString("greeting", "Hi");
 * int    maxUses  = cfg.getInt("maxUses", 1);
 * }</pre>
 *
 * <p>Reads are null-safe with defaults. This is intentionally small — for complex,
 * versioned config prefer a typed {@code BuilderCodec}.
 */
public final class PluginConfig {

    private final BsonDocument root;

    private PluginConfig(@Nonnull BsonDocument root) {
        this.root = root;
    }

    /**
     * Loads JSON from {@code path}. If the file does not exist, writes
     * {@code defaultJson} there first and loads that. Parent directories are created.
     */
    @Nonnull
    public static PluginConfig loadOrCreate(@Nonnull Path path, @Nonnull String defaultJson) {
        try {
            if (Files.notExists(path)) {
                if (path.getParent() != null) {
                    Files.createDirectories(path.getParent());
                }
                Files.writeString(path, defaultJson, StandardCharsets.UTF_8);
                Log.info("Created default config: %s", path);
            }
            String json = Files.readString(path, StandardCharsets.UTF_8);
            return new PluginConfig(BsonDocument.parse(json));
        } catch (IOException | RuntimeException e) {
            Log.error(e, "Failed to load config %s — using empty config", path);
            return new PluginConfig(new BsonDocument());
        }
    }

    /** Wraps an already-parsed document (e.g. for tests). */
    @Nonnull
    public static PluginConfig of(@Nonnull BsonDocument doc) {
        return new PluginConfig(doc);
    }

    @Nonnull
    public String getString(@Nonnull String key, @Nonnull String def) {
        BsonValue v = root.get(key);
        return v != null && v.isString() ? v.asString().getValue() : def;
    }

    public int getInt(@Nonnull String key, int def) {
        BsonValue v = root.get(key);
        return v != null && v.isNumber() ? v.asNumber().intValue() : def;
    }

    public long getLong(@Nonnull String key, long def) {
        BsonValue v = root.get(key);
        return v != null && v.isNumber() ? v.asNumber().longValue() : def;
    }

    public double getDouble(@Nonnull String key, double def) {
        BsonValue v = root.get(key);
        return v != null && v.isNumber() ? v.asNumber().doubleValue() : def;
    }

    public boolean getBoolean(@Nonnull String key, boolean def) {
        BsonValue v = root.get(key);
        return v != null && v.isBoolean() ? v.asBoolean().getValue() : def;
    }

    /** Returns the raw value for advanced cases (arrays/nested docs), or {@code null}. */
    @Nullable
    public BsonValue getRaw(@Nonnull String key) {
        return root.get(key);
    }

    /** The underlying parsed document. */
    @Nonnull
    public BsonDocument document() {
        return root;
    }
}
