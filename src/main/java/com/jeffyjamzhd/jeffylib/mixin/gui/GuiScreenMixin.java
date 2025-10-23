package com.jeffyjamzhd.jeffylib.mixin.gui;

import com.jeffyjamzhd.jeffylib.api.impl.IGuiScreen;
import com.jeffyjamzhd.jeffylib.api.IItemExtendedInteraction;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
@Environment(EnvType.CLIENT)
public abstract class GuiScreenMixin implements IGuiScreen {
    @Shadow public static boolean isShiftKeyDown() {
        return false;
    }
    @Shadow protected Minecraft mc;

    @Unique
    long jl$lastScroll = 0L;
    @Unique
    boolean jl$scrollConsumed = false;

    @Inject(method = "handleMouseInput",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButton()I"), cancellable = true)
    private void mouseScroll(CallbackInfo ci, @Local(ordinal = 0) int x, @Local(ordinal = 1) int y) {
        // Get scroll
        long sysTime = Minecraft.getSystemTime();
        if (sysTime > jl$lastScroll + 25L)
            jl$scrollConsumed = false;
        int dWheel = Mouse.getEventDWheel();

        if (dWheel != 0 && sysTime > jl$lastScroll + 25L) {
            // Handle scroll and consume input
            jl$scrollConsumed = jl$handleMouseScroll(x, y, dWheel);
            jl$lastScroll = sysTime;

            ci.cancel();
        }
    }

    @Unique
    @Override
    public boolean jl$handleMouseScroll(int x, int y, int scroll) {
        if (((GuiScreen) (Object) this) instanceof GuiContainer container) {
            // Do event handling for slot
            Slot slot = container.getSlotAtPosition(x, y);
            EntityPlayer player = mc.thePlayer;

            // Do not process crafting slot
            if (slot instanceof SlotCrafting) {
                return false;
            }

            if (slot != null && slot.getHasStack()) {
                // Handle item in slot first
                ItemStack stack = slot.getStack();
                if (stack.getItem() instanceof IItemExtendedInteraction ext) {
                    ext.itemScrolled(stack, player, player.getEntityWorld(),
                            scroll > 0 ? 1 : -1, isShiftKeyDown());
                    return true;
                }
            }

            // Check for item in cursor?
            ItemStack stack = mc.thePlayer.inventory.getItemStack();
            if (stack != null && stack.getItem() instanceof IItemExtendedInteraction ext) {
                ext.itemScrolled(stack, player, player.getEntityWorld(),
                        scroll > 0 ? 1 : -1, isShiftKeyDown());
                return true;
            }
        }
        return false;
    }

    @Unique
    @Override
    public boolean jl$isScrollConsumed() {
        return jl$scrollConsumed;
    }
}
