package com.eightsidedsquare.zine.common.text;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public sealed interface CustomStyleAttributeContainer permits CustomStyleAttributeContainer.Empty, CustomStyleAttributeContainer.Single, CustomStyleAttributeContainer.MapBacked {

    Codec<Map<CustomStyleAttribute<?>, Object>> ATTRIBUTE_TO_VALUE_MAP_CODEC = Codec.dispatchedMap(CustomStyleAttribute.CODEC, CustomStyleAttribute::getCodec);
    Codec<CustomStyleAttributeContainer> CODEC = ATTRIBUTE_TO_VALUE_MAP_CODEC.xmap(
            map -> switch (map.size()) {
                case 0 -> Empty.INSTANCE;
                case 1 -> Single.ofEntry(map.entrySet().iterator().next());
                default -> new MapBacked(new Reference2ObjectArrayMap<>(map));
            },
            container -> switch (container) {
                case Single<?> single -> Map.of(single.attribute, single.value);
                case MapBacked mapBacked -> mapBacked.attributes;
                default -> Map.of();
            }
    );

    static CustomStyleAttributeContainer create() {
        return Empty.INSTANCE;
    }

    @Nullable
    <T> T get(CustomStyleAttribute<T> attribute);

    default <T> T getOrDefault(CustomStyleAttribute<T> attribute, T defaultValue) {
        T value = this.get(attribute);
        return value == null ? defaultValue : value;
    }

    boolean contains(CustomStyleAttribute<?> attribute);

    <T> CustomStyleAttributeContainer with(CustomStyleAttribute<T> attribute, T value);

    CustomStyleAttributeContainer with(CustomStyleAttributeContainer container);

    boolean isEmpty();

    int size();

    final class Empty implements CustomStyleAttributeContainer {

        public static final Empty INSTANCE = new Empty();

        Empty() {
        }

        @Override
        public <T> @Nullable T get(CustomStyleAttribute<T> attribute) {
            return null;
        }

        @Override
        public boolean contains(CustomStyleAttribute<?> attribute) {
            return false;
        }

        @Override
        public <T> CustomStyleAttributeContainer with(CustomStyleAttribute<T> attribute, T value) {
            return new Single<>(attribute, value);
        }

        @Override
        public CustomStyleAttributeContainer with(CustomStyleAttributeContainer container) {
            return container;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public String toString() {
            return "{}";
        }
    }

    final class Single<V> implements CustomStyleAttributeContainer {

        private final CustomStyleAttribute<V> attribute;
        private final V value;

        Single(CustomStyleAttribute<V> attribute, V value) {
            this.attribute = attribute;
            this.value = value;
        }

        static Single<?> ofEntry(Map.Entry<CustomStyleAttribute<?>, ?> entry) {
            return unchecked(entry.getKey(), entry.getValue());
        }

        @SuppressWarnings("unchecked")
        static <V> Single<V> unchecked(CustomStyleAttribute<V> attribute, Object value) {
            return new Single<>(attribute, (V) value);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> @Nullable T get(CustomStyleAttribute<T> attribute) {
            return attribute == this.attribute ? (T) this.value : null;
        }

        @Override
        public boolean contains(CustomStyleAttribute<?> attribute) {
            return this.attribute == attribute;
        }

        @Override
        public <T> CustomStyleAttributeContainer with(CustomStyleAttribute<T> attribute, T value) {
            if(attribute == this.attribute) {
                return value == this.value ? this : new Single<>(attribute, value);
            }
            Reference2ObjectMap<CustomStyleAttribute<?>, Object> attributes = new Reference2ObjectArrayMap<>();
            attributes.put(this.attribute, this.value);
            attributes.put(attribute, value);
            return new MapBacked(attributes);
        }

        @Override
        public CustomStyleAttributeContainer with(CustomStyleAttributeContainer container) {
            return switch (container) {
                case MapBacked mapBacked -> mapBacked.with(this.attribute, this.value);
                case Single<?> single -> single.with(this.attribute, this.value);
                default -> this;
            };
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Single<?> single)) {
                return false;
            }
            return Objects.equals(this.attribute, single.attribute) && Objects.equals(this.value, single.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.attribute, this.value);
        }

        @Override
        public String toString() {
            return "{" + this.attribute + "=>" + this.value + "}";
        }
    }

    final class MapBacked implements CustomStyleAttributeContainer {

        private final Reference2ObjectMap<CustomStyleAttribute<?>, Object> attributes;

        MapBacked(Reference2ObjectMap<CustomStyleAttribute<?>, Object> attributes) {
            this.attributes = attributes;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> @Nullable T get(CustomStyleAttribute<T> attribute) {
            Object value = this.attributes.get(attribute);
            return value == null ? null : (T) value;
        }

        @Override
        public boolean contains(CustomStyleAttribute<?> attribute) {
            return this.attributes.containsKey(attribute);
        }

        @Override
        public <T> CustomStyleAttributeContainer with(CustomStyleAttribute<T> attribute, T value) {
            Reference2ObjectMap<CustomStyleAttribute<?>, Object> attributes = new Reference2ObjectArrayMap<>(this.attributes);
            attributes.put(attribute, value);
            return new MapBacked(attributes);
        }

        @Override
        public CustomStyleAttributeContainer with(CustomStyleAttributeContainer container) {
            return switch (container) {
                case MapBacked mapBacked -> {
                    Reference2ObjectMap<CustomStyleAttribute<?>, Object> attributes = new Reference2ObjectArrayMap<>(this.attributes);
                    attributes.putAll(mapBacked.attributes);
                    yield new MapBacked(attributes);
                }
                case Single<?> single -> this.with(single);
                default -> this;
            };
        }

        @Override
        public boolean isEmpty() {
            return this.attributes.isEmpty();
        }

        @Override
        public int size() {
            return this.attributes.size();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof MapBacked mapBacked)) {
                return false;
            }
            return Objects.equals(this.attributes, mapBacked.attributes);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.attributes);
        }

        @Override
        public String toString() {
            return this.attributes.toString();
        }
    }

}
