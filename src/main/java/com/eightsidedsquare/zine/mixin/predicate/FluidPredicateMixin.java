package com.eightsidedsquare.zine.mixin.predicate;

import com.eightsidedsquare.zine.common.predicate.ZineFluidPredicate;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Optional;

@Mixin(FluidPredicate.class)
public abstract class FluidPredicateMixin implements ZineFluidPredicate {

    @Shadow @Final @Mutable
    private Optional<HolderSet<Fluid>> fluids;

    @Shadow @Final @Mutable
    private Optional<StatePropertiesPredicate> properties;

    @Override
    public void zine$setFluids(@Nullable HolderSet<Fluid> fluids) {
        this.fluids = Optional.ofNullable(fluids);
    }

    @Override
    public void zine$addFluid(Fluid fluid) {
        if(this.fluids.isPresent()) {
            this.fluids = Optional.of(ZineUtil.mergeValue(this.fluids.get(), BuiltInRegistries.FLUID::wrapAsHolder, fluid));
            return;
        }
        this.fluids = Optional.of(HolderSet.direct(BuiltInRegistries.FLUID::wrapAsHolder, fluid));
    }

    @Override
    public void zine$addFluids(Collection<Fluid> fluids) {
        if(this.fluids.isPresent()) {
            this.fluids = Optional.of(ZineUtil.mergeValues(this.fluids.get(), BuiltInRegistries.FLUID::wrapAsHolder, fluids));
            return;
        }
        this.fluids = Optional.of(HolderSet.direct(BuiltInRegistries.FLUID::wrapAsHolder, fluids));
    }

    @Override
    public void zine$setState(@Nullable StatePropertiesPredicate state) {
        this.properties = Optional.ofNullable(state);
    }
}
