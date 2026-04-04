package com.eightsidedsquare.zine.common.world.structure;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;

import java.util.List;

public interface ZineRuleProcessor {

    default ImmutableList<ProcessorRule> zine$getRules() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setRules(ImmutableList<ProcessorRule> rules) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRule(ProcessorRule rule) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addRules(List<ProcessorRule> rules) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
