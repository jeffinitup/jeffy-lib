package com.jeffyjamzhd.jeffylib.mixin.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.GuiContainerCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = GuiContainerCreative.class)
public class GuiContainerCreativeMixin {
    @Inject(method = "handleMouseInput", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/src/InventoryEffectRenderer;handleMouseInput()V",
            shift = At.Shift.AFTER), cancellable = true)
    private void cancelScrollInCreative(CallbackInfo ci) {
        if (((GuiContainerCreative) (Object) this).jl$isScrollConsumed()) {
            ci.cancel();
        }
    }

}
