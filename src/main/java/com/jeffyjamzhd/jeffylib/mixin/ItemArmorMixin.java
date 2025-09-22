package com.jeffyjamzhd.jeffylib.mixin;

import com.jeffyjamzhd.jeffylib.api.impl.IItemArmor;
import net.minecraft.src.ItemArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemArmor.class)
public class ItemArmorMixin implements IItemArmor {
    @Unique
    public boolean jl$canBeWorn = true;

    @Override
    public boolean jl$canBeWorn() {
        return jl$canBeWorn;
    }

    @Override
    public ItemArmor jl$cantBeWorn() {
        this.jl$canBeWorn = false;
        return (ItemArmor) (Object) this;
    }
}
