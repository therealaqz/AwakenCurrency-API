package me.vouchme.awakencurrencyApi.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class CurrencyAPIProvider {
    private static ICurrencyAPI instance;
    private static boolean initialized = false;

    private CurrencyAPIProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ICurrencyAPI getAPI() {
        if (!initialized) {
            initialize();
        }
        return instance;
    }

    public static boolean isAPIAvailable() {
        return getAPI() != null;
    }

    public static String getPluginVersion() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("AwakenCurrency");
        return plugin != null ? plugin.getDescription().getVersion() : null;
    }

    private static void initialize() {
        initialized = true;
        Plugin plugin = Bukkit.getPluginManager().getPlugin("AwakenCurrency");
        if (plugin != null && plugin.isEnabled()) {
            try {
                Object apiInstance = plugin.getClass().getMethod("getAPI").invoke(plugin);
                if (apiInstance instanceof ICurrencyAPI) {
                    instance = (ICurrencyAPI) apiInstance;
                }
            } catch (Exception e) {
                instance = null;
            }
        } else {
            instance = null;
        }
    }

    public static void refresh() {
        initialized = false;
        instance = null;
        initialize();
    }
}