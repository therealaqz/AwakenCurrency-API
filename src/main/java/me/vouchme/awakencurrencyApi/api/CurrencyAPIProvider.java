package me.vouchme.awakencurrencyApi.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class CurrencyAPIProvider {
    private static ICurrencyAPI instance;
    private static boolean initialized = false;

    // Expected currency type for validation
    private static final String EXPECTED_CURRENCY_TYPE = "crystals";

    private CurrencyAPIProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Get the Crystal Currency API
     * @return The ICurrencyAPI instance, or null if not available
     * @throws IllegalStateException if the found API doesn't handle crystals
     */
    public static ICurrencyAPI getAPI() {
        if (!initialized) {
            initialize();
        }
        return instance;
    }

    /**
     * Get Crystal Currency API with type validation
     * @return The ICurrencyAPI instance for crystals specifically
     * @throws IllegalStateException if the API doesn't handle crystals
     */
    public static ICurrencyAPI getCrystalAPI() {
        ICurrencyAPI api = getAPI();
        if (api != null && !api.handlesCurrency(EXPECTED_CURRENCY_TYPE)) {
            throw new IllegalStateException(
                    String.format("Expected %s currency API, but found %s from plugin %s",
                            EXPECTED_CURRENCY_TYPE,
                            api.getCurrencyType(),
                            api.getProviderPlugin())
            );
        }
        return api;
    }

    public static boolean isAPIAvailable() {
        return getAPI() != null;
    }

    /**
     * Check if the Crystal API specifically is available
     * @return true if crystal currency API is available and valid
     */
    public static boolean isCrystalAPIAvailable() {
        try {
            return getCrystalAPI() != null;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public static String getPluginVersion() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("AwakenCurrency");
        return plugin != null ? plugin.getDescription().getVersion() : null;
    }

    /**
     * Get information about the currently loaded currency API
     * @return Info string, or null if no API is loaded
     */
    public static String getAPIInfo() {
        ICurrencyAPI api = getAPI();
        if (api == null) {
            return null;
        }
        return String.format("Currency: %s (%s) from plugin: %s",
                api.getCurrencyType(),
                api.getCurrencyDisplayName(),
                api.getProviderPlugin());
    }

    private static void initialize() {
        initialized = true;
        Plugin plugin = Bukkit.getPluginManager().getPlugin("AwakenCurrency");
        if (plugin != null && plugin.isEnabled()) {
            try {
                // Try to get the API from the main plugin
                Object apiInstance = plugin.getClass().getMethod("getAPI").invoke(plugin);
                if (apiInstance instanceof ICurrencyAPI) {
                    ICurrencyAPI api = (ICurrencyAPI) apiInstance;

                    // Log what we found
                    Bukkit.getLogger().info(String.format(
                            "[CurrencyAPI] Found currency API: %s (%s) from %s",
                            api.getCurrencyType(),
                            api.getCurrencyDisplayName(),
                            api.getProviderPlugin()
                    ));

                    instance = api;
                } else {
                    Bukkit.getLogger().warning("[CurrencyAPI] AwakenCurrency plugin found but API is not ICurrencyAPI instance");
                }
            } catch (Exception e) {
                Bukkit.getLogger().warning("[CurrencyAPI] Failed to get API from AwakenCurrency: " + e.getMessage());
                instance = null;
            }
        } else {
            Bukkit.getLogger().info("[CurrencyAPI] AwakenCurrency plugin not found or not enabled");
            instance = null;
        }
    }

    public static void refresh() {
        initialized = false;
        instance = null;
        initialize();
    }
}