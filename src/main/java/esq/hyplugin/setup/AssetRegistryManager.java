package esq.hyplugin.setup;
import esq.hyplugin.HyPlugin;
import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;

public class AssetRegistryManager {
        private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
        private final HyPlugin plugin;

        public AssetRegistryManager(HyPlugin plugin) {
                this.plugin = plugin;
        }
        public void register() {

        }
}
