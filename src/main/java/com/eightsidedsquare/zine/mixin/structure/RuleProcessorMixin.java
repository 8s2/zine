package com.eightsidedsquare.zine.mixin.structure;

import com.eightsidedsquare.zine.common.world.structure.ZineRuleProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(RuleProcessor.class)
public abstract class RuleProcessorMixin implements ZineRuleProcessor {

    @Shadow @Final @Mutable
    private ImmutableList<ProcessorRule> rules;

    @Override
    public ImmutableList<ProcessorRule> zine$getRules() {
        return this.rules;
    }

    @Override
    public void zine$setRules(ImmutableList<ProcessorRule> rules) {
        this.rules = rules;
    }

    @Override
    public void zine$addRule(ProcessorRule rule) {
        this.rules = ImmutableList.<ProcessorRule>builder().addAll(this.rules).add(rule).build();
    }

    @Override
    public void zine$addRules(List<ProcessorRule> rules) {
        this.rules = ImmutableList.<ProcessorRule>builder().addAll(this.rules).addAll(rules).build();
    }
}
