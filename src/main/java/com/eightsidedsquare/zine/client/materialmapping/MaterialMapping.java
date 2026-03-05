package com.eightsidedsquare.zine.client.materialmapping;

import com.eightsidedsquare.zine.common.util.ZineUtil;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public sealed interface MaterialMapping<K, V> {

    Map<K, V> mappings();

    @Nullable
    default V get(K key) {
        return this.mappings().get(key);
    }

    record Unbaked(Map<String, Material> mappings) implements MaterialMapping<String, Material> {
        public static final Codec<Unbaked> CODEC = Codec.unboundedMap(Codec.STRING, Material.CODEC).xmap(
                Unbaked::new, Unbaked::mappings
        );

        public Baked bake(Function<Material, Material.Baked> bakeFunction) {
            return new Baked(ZineUtil.mapValues(this.mappings, bakeFunction));
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            ImmutableMap.Builder<String, Material> mappings = ImmutableMap.builder();

            public Builder add(String name, Material material) {
                this.mappings.put(name, material);
                return this;
            }

            public Builder add(String name, Identifier sprite) {
                return this.add(name, new Material(sprite));
            }

            public Builder add(String name, Identifier sprite, boolean forceTranslucent) {
                return this.add(name, new Material(sprite, forceTranslucent));
            }

            public Unbaked build() {
                return new Unbaked(this.mappings.buildKeepingLast());
            }

            private Builder() {
            }
        }
    }

    record Baked(Map<String, Material.Baked> mappings) implements MaterialMapping<String, Material.Baked> {
        public static final Baked EMPTY = new Baked(Map.of());
    }

    record UnbakedSet(Map<Identifier, Unbaked> mappings) implements MaterialMapping<Identifier, Unbaked> {
        public static final Codec<UnbakedSet> CODEC = Codec.unboundedMap(Identifier.CODEC, Unbaked.CODEC).xmap(
                UnbakedSet::new, UnbakedSet::mappings
        );

        public BakedSet bake(Function<Material, Material.Baked> bakeFunction) {
            return new BakedSet(ZineUtil.mapValues(this.mappings, texture -> texture.bake(bakeFunction)));
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            ImmutableMap.Builder<Identifier, Unbaked> mappings = ImmutableMap.builder();

            public Builder add(Identifier id, Unbaked texture) {
                this.mappings.put(id, texture);
                return this;
            }

            public Builder add(Identifier id, Unbaked.Builder builder) {
                return this.add(id, builder.build());
            }

            public Builder addAll(Builder builder) {
                this.mappings.putAll(builder.mappings.buildKeepingLast());
                return this;
            }

            public Builder addAll(UnbakedSet mappings) {
                this.mappings.putAll(mappings.mappings());
                return this;
            }

            public UnbakedSet build() {
                return new UnbakedSet(this.mappings.buildKeepingLast());
            }

            private Builder() {
            }
        }
    }

    record BakedSet(Map<Identifier, Baked> mappings) implements MaterialMapping<Identifier, Baked> {
        public static final BakedSet EMPTY = new BakedSet(Map.of());
        public Baked getOrEmpty(Identifier id) {
            return this.mappings.getOrDefault(id, Baked.EMPTY);
        }
    }

    record Template(Map<String, MaterialTemplate> mappings) implements MaterialMapping<String, MaterialTemplate> {

        public Unbaked create(Identifier id) {
            return new Unbaked(ZineUtil.mapValues(this.mappings, template -> template.create(id)));
        }

        public Unbaked create(ResourceKey<?> key) {
            return new Unbaked(ZineUtil.mapValues(this.mappings, template -> template.create(key)));
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private final ImmutableMap.Builder<String, MaterialTemplate> mappings = ImmutableMap.builder();

            public Builder add(String name, MaterialTemplate template) {
                this.mappings.put(name, template);
                return this;
            }

            public Builder add(String name, String template) {
                return this.add(name, MaterialTemplate.of(template));
            }

            public Builder add(String name, String template, boolean forceTranslucent) {
                return this.add(name, MaterialTemplate.of(template, forceTranslucent));
            }

            public Builder addSuffixed(String name) {
                return this.add(name, "%s_" + name);
            }

            public Builder addSuffixed(String name, boolean forceTranslucent) {
                return this.add(name, "%s_" + name, forceTranslucent);
            }

            public Template build() {
                return new Template(this.mappings.buildKeepingLast());
            }

            private Builder() {
            }
        }
    }

    record TemplateSet(Map<Identifier, Template> mappings) implements MaterialMapping<Identifier, Template> {

        public UnbakedSet create(Identifier id) {
            return new UnbakedSet(ZineUtil.mapValues(this.mappings, template -> template.create(id)));
        }

        public UnbakedSet create(ResourceKey<?> key) {
            return new UnbakedSet(ZineUtil.mapValues(this.mappings, template -> template.create(key)));
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private final ImmutableMap.Builder<Identifier, Template> mappings = ImmutableMap.builder();

            public Builder add(Identifier id, Template template) {
                this.mappings.put(id, template);
                return this;
            }

            public Builder add(Identifier id, Template.Builder builder) {
                return this.add(id, builder.build());
            }

            public TemplateSet build() {
                return new TemplateSet(this.mappings.buildKeepingLast());
            }

            private Builder() {
            }
        }
    }
}
