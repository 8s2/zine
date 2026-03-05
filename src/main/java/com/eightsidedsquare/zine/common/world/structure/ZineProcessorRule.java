package com.eightsidedsquare.zine.common.world.structure;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;

public interface ZineProcessorRule {

    default RuleTest zine$getInputPredicate() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setInputPredicate(RuleTest inputPredicate) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default RuleTest zine$getLocationPredicate() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setLocationPredicate(RuleTest locationPredicate) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default PosRuleTest zine$getPositionPredicate() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setPositionPredicate(PosRuleTest positionPredicate) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default BlockState zine$getOutputState() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setOutputState(BlockState outputState) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default RuleBlockEntityModifier zine$getBlockEntityModifier() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setBlockEntityModifier(RuleBlockEntityModifier blockEntityModifier) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
