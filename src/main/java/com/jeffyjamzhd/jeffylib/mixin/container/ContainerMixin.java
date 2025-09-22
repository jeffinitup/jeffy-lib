package com.jeffyjamzhd.jeffylib.mixin.container;

import com.jeffyjamzhd.jeffylib.api.IItemExtendedInteraction;
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
        ItemStack cursorStack = player.inventory.getItemStack();
        if (clickType == 1 && slotID >= 0 && slotID < player.openContainer.getInventory().size()) {
            // Get stack
            Slot slotAt = player.openContainer.getSlot(slotID);
            ItemStack stackAt = slotAt.getStack();

            // Do right-click with stack if applicable
            if (stackAt != null && stackAt.getItem() instanceof IItemExtendedInteraction ext) {
                // Special client stuff
                if (player.worldObj.isRemote) {
                    // Call pre interaction hook, if not in creative inventory
                    if (!(player.openContainer instanceof ContainerCreative)) {
                        ext.beforeExtendedInteraction(stackAt, slotID, action == 1);
                    }
                }

                if (cursorStack != null) {
                    // Check if this interaction can happen
                    if (cursorStack.getItem() instanceof IItemExtendedInteraction &&
                        !ext.canInteractWithSelf(cursorStack, stackAt)) {
                        return;
                    }

                    // Extended interaction item right-clicked with stack
                    ext.itemRightClickedWithStack(stackAt, cursorStack,
                            player, player.getEntityWorld(), action == 1);
                } else {
                    // Extended interaction item right-clicked
                    ext.itemRightClicked(stackAt, player,
                            player.getEntityWorld(), action == 1);
                }

                cir.setReturnValue(stackAt);
            }

            // Do right-click with cursor stack if applicable
            if (cursorStack != null &&
                    cursorStack.getItem() instanceof IItemExtendedInteraction ext) {
                // Special client stuff
                if (player.worldObj.isRemote) {
                    // Call pre interaction hook, if not in creative inventory
                    if (!(player.openContainer instanceof ContainerCreative)) {
                        ext.beforeExtendedInteraction(cursorStack, -999, action == 1);
                    }
                }

                // Do not allow nesting
                if (stackAt != null && stackAt.getItem() instanceof IItemExtendedInteraction &&
                        !ext.canInteractWithSelf(stackAt, cursorStack)) {
                    return;
                }

                // Stack right-clicked with extended interaction item
                ItemStack result;
                if (stackAt != null) {
                    result = ext.itemRightClickedWithStackAsMouseStack(stackAt, cursorStack, player,
                            player.getEntityWorld(), action == 1);
                } else {
                    result = ext.itemRightClickAsMouseStack(cursorStack, player,
                            player.getEntityWorld(), action == 1);
                }
                slotAt.putStack(result);

                cir.setReturnValue(cursorStack);
            }
        }
    }
}
