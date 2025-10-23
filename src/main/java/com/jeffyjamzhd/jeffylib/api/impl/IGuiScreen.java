package com.jeffyjamzhd.jeffylib.api.impl;

public interface IGuiScreen {
    /**
     * Callback for mouse scrolling in GUI. {@code true} if blocks RetroEMI
     * @param x Mouse x position
     * @param y Mouse y position
     * @param scroll Scroll value (1 - up, -1 - down)
     */
    default boolean jl$handleMouseScroll(int x, int y, int scroll) {
        return false;
    }

    /**
     * {@code true} if scroll input has been consumed
     */
    default boolean jl$isScrollConsumed() {
        return false;
    }
}
