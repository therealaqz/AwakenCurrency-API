package me.vouchme.awakencurrencyApi.api;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Public API for AwakenCurrency - Crystal Management System
 * This is the ONLY interface that should be exposed to other plugins
 *
 * @author Aqz
 * @version 1.0
 */
public interface ICurrencyAPI {

    /**
     * Give crystals to a player
     * @param player The player to give crystals to
     * @param amount The amount to give (must be positive)
     * @return true if successful, false otherwise
     */
    boolean giveCrystals(Player player, double amount);

    /**
     * Take crystals from a player
     * @param player The player to take crystals from
     * @param amount The amount to take (must be positive)
     * @return true if successful (player had enough), false otherwise
     */
    boolean takeCrystals(Player player, double amount);

    /**
     * Set a player's crystal balance
     * @param player The player
     * @param amount The amount to set (negative values will be set to 0)
     */
    void setCrystals(Player player, double amount);

    /**
     * Get a player's crystal balance
     * @param player The player
     * @return The player's balance, or 0.0 if player is null
     */
    double getCrystals(Player player);

    /**
     * Check if a player has at least the specified amount of crystals
     * @param player The player
     * @param amount The amount to check
     * @return true if player has enough crystals
     */
    boolean hasCrystals(Player player, double amount);

    /**
     * Format a crystal amount for display
     * @param amount The amount to format
     * @return Formatted string (e.g., "1,000 Crystals")
     */
    String formatCrystals(double amount);

    // UUID overloads for offline players

    /**
     * Give crystals to an offline player by UUID
     * @param uuid The player's UUID
     * @param amount The amount to give (must be positive)
     * @return true if successful, false otherwise
     */
    boolean giveCrystals(UUID uuid, double amount);

    /**
     * Take crystals from an offline player by UUID
     * @param uuid The player's UUID
     * @param amount The amount to take (must be positive)
     * @return true if successful (player had enough), false otherwise
     */
    boolean takeCrystals(UUID uuid, double amount);

    /**
     * Set an offline player's crystal balance by UUID
     * @param uuid The player's UUID
     * @param amount The amount to set (negative values will be set to 0)
     */
    void setCrystals(UUID uuid, double amount);

    /**
     * Get an offline player's crystal balance by UUID
     * @param uuid The player's UUID
     * @return The player's balance, or 0.0 if UUID is null
     */
    double getCrystals(UUID uuid);

    /**
     * Check if an offline player has at least the specified amount of crystals
     * @param uuid The player's UUID
     * @param amount The amount to check
     * @return true if player has enough crystals
     */
    boolean hasCrystals(UUID uuid, double amount);
}