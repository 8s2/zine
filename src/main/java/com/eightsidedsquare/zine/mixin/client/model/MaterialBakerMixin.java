package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.materialmapping.MaterialMappingStorage;
import com.eightsidedsquare.zine.client.model.ZineMaterialBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(MaterialBaker.class)
public interface MaterialBakerMixin extends ZineMaterialBaker {

    @Shadow
    Material.Baked get(Material material, ModelDebugName name);

    @Shadow
    Material.Baked reportMissingReference(String reference, ModelDebugName name);

    @Override
    default Material.Baked zine$get(Material material) {
        return this.get(material, () -> "Unknown Model");
    }

    @Override
    default Material.Baked zine$getMissing() {
        return this.reportMissingReference("Unknown", () -> "Unknown Model");
    }

    @Override
    default MaterialMappingStorage zine$getMappings() {
        return MaterialMappingStorage.EMPTY;
    }
}
