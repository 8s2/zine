package com.eightsidedsquare.zine.client.model;

import com.eightsidedsquare.zine.client.materialmapping.MappableModel;
import com.eightsidedsquare.zine.client.materialmapping.MaterialMappingStorage;
import net.minecraft.resources.Identifier;

public interface ZineModelBaker extends ZineMaterialBaker {

    default MappableModel.Unbaked zine$getMappableModel(Identifier id) {
        return MappableModel.Unbaked.EMPTY;
    }

    @Override
    default MaterialMappingStorage zine$getMappings() {
        return MaterialMappingStorage.EMPTY;
    }
}
