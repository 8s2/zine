package com.eightsidedsquare.zine.common.advancement;

import net.minecraft.advancements.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public interface ZineAdvancement {

    default void zine$setParent(@Nullable Identifier parent) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDisplay(@Nullable DisplayInfo display) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setRewards(AdvancementRewards rewards) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addCriterion(String name, Criterion<?> criterion) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <T extends CriterionTriggerInstance> void zine$addCriterion(String name, CriterionTrigger<T> trigger, T conditions) {
        this.zine$addCriterion(name, new Criterion<>(trigger, conditions));
    }

    default void zine$setCriteria(Map<String, Criterion<?>> criteria) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setRequirements(AdvancementRequirements requirements) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSendsTelemetryEvent(boolean sendsTelemetryEvent) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setName(@Nullable Component name) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default <T extends CriterionTriggerInstance> Optional<T> zine$getCriterion(String name, CriterionTrigger<T> criterion) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
