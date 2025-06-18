package com.eightsidedsquare.zine.common.predicate;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ZineDamageSourcePredicate {

    default void zine$setTags(List<TagPredicate<DamageType>> tags) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addTag(TagPredicate<DamageType> tag) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$addTags(List<TagPredicate<DamageType>> tags) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDirectEntity(@Nullable EntityPredicate directEntity) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setSourceEntity(@Nullable EntityPredicate sourceEntity) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

    default void zine$setDirect(@Nullable Boolean direct) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }

}
