package com.jeffyjamzhd.jeffylib.mixin.container;

import com.jeffyjamzhd.jeffylib.util.ContainerUtil;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Container.class)
public class ContainerMixin {
    @Inject(method = "slotClick", at = @At("HEAD"), cancellable = true)
    private void slotClick(int slotID, int clickType, int action,
                           EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
        ContainerUtil.handleSlotClick(slotID, clickType, action, player, cir);
    }
}
