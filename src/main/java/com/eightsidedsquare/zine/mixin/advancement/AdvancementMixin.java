package com.eightsidedsquare.zine.mixin.advancement;

import com.eightsidedsquare.zine.common.advancement.ZineAdvancement;
import com.eightsidedsquare.zine.common.util.ZineUtil;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Optional;

@Mixin(Advancement.class)
public abstract class AdvancementMixin implements ZineAdvancement {

    @Shadow @Final @Mutable
    private Optional<Identifier> parent;

    @Shadow @Final @Mutable
    private Optional<DisplayInfo> display;

    @Shadow @Final @Mutable
    private AdvancementRewards rewards;

    @Shadow @Final @Mutable
    private Map<String, Criterion<?>> criteria;

    @Shadow @Final @Mutable
    private boolean sendsTelemetryEvent;

    @Shadow @Final @Mutable
    private Optional<Component> name;

    @Shadow @Final @Mutable
    private AdvancementRequirements requirements;

    @Override
    public void zine$setParent(@Nullable Identifier parent) {
        this.parent = Optional.ofNullable(parent);
    }

    @Override
    public void zine$setDisplay(@Nullable DisplayInfo display) {
        this.display = Optional.ofNullable(display);
    }

    @Override
    public void zine$setRewards(AdvancementRewards rewards) {
        this.rewards = rewards;
    }

    @Override
    public void zine$addCriterion(String name, Criterion<?> criterion) {
        this.criteria = ZineUtil.putOrUnfreeze(this.criteria, name, criterion);
    }

    @Override
    public void zine$setCriteria(Map<String, Criterion<?>> criteria) {
        this.criteria = criteria;
    }

    @Override
    public void zine$setRequirements(AdvancementRequirements requirements) {
        this.requirements = requirements;
    }

    @Override
    public void zine$setSendsTelemetryEvent(boolean sendsTelemetryEvent) {
        this.sendsTelemetryEvent = sendsTelemetryEvent;
    }

    @Override
    public void zine$setName(@Nullable Component name) {
        this.name = Optional.ofNullable(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends CriterionTriggerInstance> Optional<T> zine$getCriterion(String name, CriterionTrigger<T> criterion) {
        Criterion<?> advancementCriterion = this.criteria.get(name);
        if(advancementCriterion != null && advancementCriterion.trigger().equals(criterion)) {
            return Optional.of((T) advancementCriterion.triggerInstance());
        }
        return Optional.empty();
    }
}
