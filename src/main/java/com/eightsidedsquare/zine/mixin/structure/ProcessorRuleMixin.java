package com.eightsidedsquare.zine.mixin.structure;

import com.eightsidedsquare.zine.common.world.structure.ZineProcessorRule;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ProcessorRule.class)
public abstract class ProcessorRuleMixin implements ZineProcessorRule {

    @Shadow @Final @Mutable
    private RuleTest inputPredicate;

    @Shadow @Final @Mutable
    private RuleTest locPredicate;

    @Shadow @Final @Mutable
    private PosRuleTest posPredicate;

    @Shadow @Final @Mutable
    private BlockState outputState;

    @Shadow @Final @Mutable
    private RuleBlockEntityModifier blockEntityModifier;

    @Override
    public RuleTest zine$getInputPredicate() {
        return this.inputPredicate;
    }

    @Override
    public void zine$setInputPredicate(RuleTest inputPredicate) {
        this.inputPredicate = inputPredicate;
    }

    @Override
    public RuleTest zine$getLocationPredicate() {
        return this.locPredicate;
    }

    @Override
    public void zine$setLocationPredicate(RuleTest locationPredicate) {
        this.locPredicate = locationPredicate;
    }

    @Override
    public PosRuleTest zine$getPositionPredicate() {
        return this.posPredicate;
    }

    @Override
    public void zine$setPositionPredicate(PosRuleTest positionPredicate) {
        this.posPredicate = positionPredicate;
    }

    @Override
    public BlockState zine$getOutputState() {
        return this.outputState;
    }

    @Override
    public void zine$setOutputState(BlockState outputState) {
        this.outputState = outputState;
    }

    @Override
    public RuleBlockEntityModifier zine$getBlockEntityModifier() {
        return this.blockEntityModifier;
    }

    @Override
    public void zine$setBlockEntityModifier(RuleBlockEntityModifier blockEntityModifier) {
        this.blockEntityModifier = blockEntityModifier;
    }
}
