package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineNbtPredicate;
import net.minecraft.advancements.criterion.NbtPredicate;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NbtPredicate.class)
public abstract class NbtPredicateMixin implements ZineNbtPredicate {

    @Shadow @Final @Mutable
    private CompoundTag tag;

    @Override
    public void zine$setNbt(CompoundTag nbt) {
        this.tag = nbt;
    }
}
