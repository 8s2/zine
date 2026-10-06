package com.eightsidedsquare.zine.data.tags;

import com.eightsidedsquare.zine.common.registry.holder.IdSupplier;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public interface ZineTagAppender<T> {
    default TagAppender<T> zine$add(IdSupplier<ResourceKey<T>> element) {
        return ((TagAppender<T>) this).add(element.id());
    }

    default TagAppender<T> zine$add(IdSupplier<ResourceKey<T>>... elements) {
        return this.zine$addAll(Arrays.stream(elements));
    }

    default TagAppender<T> zine$addAll(final Collection<IdSupplier<ResourceKey<T>>> elements) {
        elements.forEach(this::zine$add);
        return (TagAppender<T>) this;
    }

    default TagAppender<T> zine$addAll(final Stream<IdSupplier<ResourceKey<T>>> elements) {
        elements.forEach(this::zine$add);
        return (TagAppender<T>) this;
    }

    default TagAppender<T> zine$addOptional(IdSupplier<ResourceKey<T>> element) {
        return ((TagAppender<T>) this).addOptional(element.id());
    }

    default TagAppender<T> zine$remove(IdSupplier<ResourceKey<T>> element) {
        return ((TagAppender<T>) this).remove(element.id());
    }

    default TagAppender<T> zine$remove(IdSupplier<ResourceKey<T>>... elements) {
        return this.zine$removeAll(Arrays.stream(elements));
    }

    default TagAppender<T> zine$removeAll(final Collection<IdSupplier<ResourceKey<T>>> elements) {
        elements.forEach(this::zine$remove);
        return (TagAppender<T>) this;
    }

    default TagAppender<T> zine$removeAll(final Stream<IdSupplier<ResourceKey<T>>> elements) {
        elements.forEach(this::zine$remove);
        return (TagAppender<T>) this;
    }
}
