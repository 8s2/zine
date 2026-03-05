package com.eightsidedsquare.zine.mixin.structure;

import com.eightsidedsquare.zine.common.world.structure.ZineSinglePoolElement;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(SinglePoolElement.class)
public abstract class SinglePoolElementMixin implements ZineSinglePoolElement {

    @Shadow @Final @Mutable
    protected Either<Identifier, StructureTemplate> template;

    @Shadow @Final @Mutable
    protected Holder<StructureProcessorList> processors;

    @Shadow @Final @Mutable
    protected Optional<LiquidSettings> overrideLiquidSettings;

    @Override
    public void zine$setTemplate(Identifier template) {
        this.template = Either.left(template);
    }

    @Override
    public void zine$setTemplate(StructureTemplate template) {
        this.template = Either.right(template);
    }

    @Override
    public Either<Identifier, StructureTemplate> zine$getTemplate() {
        return this.template;
    }

    @Override
    public Holder<StructureProcessorList> zine$getProcessors() {
        return this.processors;
    }

    @Override
    public void zine$setProcessors(Holder<StructureProcessorList> processors) {
        this.processors = processors;
    }

    @Override
    public @Nullable LiquidSettings zine$getOverrideLiquidSettings() {
        return this.overrideLiquidSettings.orElse(null);
    }

    @Override
    public void zine$setOverrideLiquidSettings(@Nullable LiquidSettings overrideLiquidSettings) {
        this.overrideLiquidSettings = Optional.ofNullable(overrideLiquidSettings);
    }
}
