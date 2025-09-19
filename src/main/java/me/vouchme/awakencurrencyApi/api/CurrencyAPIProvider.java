package me.vouchme.awakencurrencyApi.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Main entry point for accessing the Currency API
 * This is what other developers will use to access your API
 *
 * Example usage:
 * <pre>
 * ICurrencyAPI api = CurrencyAPIProvider.getAPI();
 * if (api != null) {
 *     api.giveCrystals(player, 100.0);
 * }
 * </pre>
 *
 * @author Aqz
 * @version 1.0
 */
public final class CurrencyAPIProvider {

    private static ICurrencyAPI instance;
    private static boolean initialized = false;

    /**
     * Private constructor to prevent instantiation
     */
    private CurrencyAPIProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Get the Currency API instance
     * @return The API instance, or null if AwakenCurrency is not loaded/enabled
     */
    public static ICurrencyAPI getAPI() {
        if (!initialized) {
            initialize();
        }
        return instance;
    }

    /**
     * Check if the Currency API is available and ready to use
     * @return true if the API is loaded and ready
     */
    public static boolean isAPIAvailable() {
        return getAPI() != null;
    }

    /**
     * Get the version of the loaded AwakenCurrency plugin
     * @return The plugin version, or null if not loaded
     */
    public static String getPluginVersion() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("AwakenCurrency");
        return plugin != null ? plugin.getDescription().getVersion() : null;
    }

    /**
     * Initialize the API instance
     */
    private static void initialize() {
        initialized = true;

        Plugin plugin = Bukkit.getPluginManager().getPlugin("AwakenCurrency");
        if (plugin != null && plugin.isEnabled()) {
            try {
                // Try to create API directly using factory if we're in the same plugin
                if (plugin.getClass().getName().startsWith("me.vouchme.awakenCurrency")) {
                    // We're in the same plugin, create API directly
                    instance = CurrencyAPIFactory.createAPI((me.vouchme.awakenCurrency.main.AwakenCurrency) plugin);
                } else {
                    // External plugin, use reflection to safely get the API instance
                    Object apiInstance = plugin.getClass().getMethod("getAPI").invoke(plugin);
                    if (apiInstance instanceof ICurrencyAPI) {
                        instance = (ICurrencyAPI) apiInstance;
                    }
                }
            } catch (Exception e) {
                // Silently fail - API not available
                instance = null;
            }
        } else {
            instance = null;
        }
    }

    /**
     * Force re-initialization of the API (useful after plugin reloads)
     * This method should rarely be needed
     */
    public static void refresh() {
        initialized = false;
        instance = null;
        initialize();
    }
}