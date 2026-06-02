package esq.hyplugin.setup;

import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import esq.hyplugin.HyPlugin;

/**
 * Registers custom data-driven asset stores (called from {@code setup()}).
 *
 * <p>Use this when your plugin defines its own JSON asset type loaded from
 * {@code Server/&lt;YourNamespace&gt;/...}. Typical shape:
 *
 * <pre>{@code
 * AssetRegistry.register(
 *     HytaleAssetStore.builder(MyAsset.class, new DefaultAssetMap<String, MyAsset>())
 *         .setPath("YourNamespace/Things")   // under Server/
 *         .setCodec(MyAsset.CODEC)
 *         .setKeyFunction(MyAsset::getId)
 *         .build());
 * }</pre>
 *
 * Left empty by default — most plugins start without custom asset types.
 * The {@code AssetRegistry} / {@code HytaleAssetStore} / {@code DefaultAssetMap}
 * imports are kept as a ready reference.
 */
public class AssetRegistryManager {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final HyPlugin plugin;

    public AssetRegistryManager(HyPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        // No custom asset stores by default. See the class javadoc for the pattern.
    }
}