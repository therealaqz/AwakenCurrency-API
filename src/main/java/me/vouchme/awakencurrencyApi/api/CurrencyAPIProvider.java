package me.vouchme.awakencurrencyApi.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class CurrencyAPIProvider {

    private static ICurrencyAPI instance;

    private CurrencyAPIProvider() { }

    public static ICurrencyAPI getAPI() {
        if (instance == null) {
            initialize();
        }
        return instance;
    }

    private static void initialize() {
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
        }
    }
}
