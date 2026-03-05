package com.eightsidedsquare.zine.client.util;

import com.eightsidedsquare.zine.client.materialmapping.MappableModel;
import com.eightsidedsquare.zine.client.materialmapping.MaterialMapping;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public interface ZineMappableModelHolder {

    ScopedValue<ZineMappableModelHolder> HOLDER = ScopedValue.newInstance();

    default void zine$setMappableModels(Map<Identifier, MappableModel.Unbaked> mappableModels) {

    }

    default Map<Identifier, MappableModel.Unbaked> zine$getMappableModels() {
        return Map.of();
    }

    default void zine$setMaterialMappings(Map<Identifier, MaterialMapping.UnbakedSet> materialMappings) {

    }

    default void zine$copyFrom(ZineMappableModelHolder holder) {
        this.zine$setMappableModels(holder.zine$getMappableModels());
        this.zine$setMaterialMappings(holder.zine$getMaterialMappings());
    }

    default Map<Identifier, MaterialMapping.UnbakedSet> zine$getMaterialMappings() {
        return Map.of();
    }

    class Impl implements ZineMappableModelHolder {
        private Map<Identifier, MappableModel.Unbaked> mappableModels;
        private Map<Identifier, MaterialMapping.UnbakedSet> materialMappings;

        public Impl(Map<Identifier, MappableModel.Unbaked> mappableModels, Map<Identifier, MaterialMapping.UnbakedSet> materialMappings) {
            this.mappableModels = mappableModels;
            this.materialMappings = materialMappings;
        }

        public Impl() {
            this(Map.of(), Map.of());
        }

        @Override
        public void zine$setMappableModels(Map<Identifier, MappableModel.Unbaked> mappableModels) {
            this.mappableModels = mappableModels;
        }

        @Override
        public Map<Identifier, MappableModel.Unbaked> zine$getMappableModels() {
            return this.mappableModels;
        }

        @Override
        public void zine$setMaterialMappings(Map<Identifier, MaterialMapping.UnbakedSet> materialMappings) {
            this.materialMappings = materialMappings;
        }

        @Override
        public Map<Identifier, MaterialMapping.UnbakedSet> zine$getMaterialMappings() {
            return this.materialMappings;
        }
    }

}
