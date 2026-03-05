package com.eightsidedsquare.zine.mixin.entity.spawn;

import com.eightsidedsquare.zine.common.entity.spawn.ZineStructureCheck;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.StructureCheck;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.function.Function;

@Mixin(StructureCheck.class)
public abstract class StructureCheckMixin implements ZineStructureCheck {

    @Shadow @Final @Mutable
    private HolderSet<Structure> requiredStructures;

    @Override
    public void zine$setRequiredStructures(HolderSet<Structure> requiredStructures) {
        this.requiredStructures = requiredStructures;
    }

    @Override
    public void zine$addRequiredStructure(Holder<Structure> requiredStructure) {
        this.requiredStructures = ZineUtil.mergeValue(this.requiredStructures, Function.identity(), requiredStructure);
    }

    @Override
    public void zine$addRequiredStructure(HolderGetter<Structure> structureLookup, ResourceKey<Structure> requiredStructure) {
        this.requiredStructures = ZineUtil.mergeValue(this.requiredStructures, structureLookup::getOrThrow, requiredStructure);
    }

    @Override
    public void zine$addRequiredStructures(HolderGetter<Structure> structureLookup, Collection<ResourceKey<Structure>> requiredStructures) {
        this.requiredStructures = ZineUtil.mergeValues(this.requiredStructures, structureLookup::getOrThrow, requiredStructures);
    }

    @Override
    public void zine$addRequiredStructures(HolderSet<Structure> requiredStructures) {
        this.requiredStructures = ZineUtil.mergeValues(this.requiredStructures, requiredStructures);
    }
}
