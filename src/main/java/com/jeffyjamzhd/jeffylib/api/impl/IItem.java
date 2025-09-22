package com.jeffyjamzhd.jeffylib.api.impl;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface IItem {
    /**
     * Sets the mod namespace in item
     */
    default Item jl$setModNamespace(String namespace) {
        return null;
    }

    /**
     * Called when this item is destroyed (dropped item entity is destroyed, burned, etc.)
     * @param stack {@link ItemStack} that was destroyed
     */
    @SuppressWarnings(value = "unused")
    default void jl$onItemDestroyed(ItemStack stack, World world, double x, double y, double z) {
    }

    /**
     * Returns item ID to pull icon from for eating particles
     * @param stack {@link ItemStack} that is being eaten
     */
    default int jl$getIDForEatingParticle(ItemStack stack) {
        return stack.itemID;
    }
}
