package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.materialmapping.MaterialMappingStorage;
import com.eightsidedsquare.zine.client.model.ZineMaterialBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MaterialBaker.class)
public abstract class MaterialBakerMixin implements ZineMaterialBaker {
    @Shadow
    @Final
    private Material.Baked missingSprite;
    @Shadow
    @Final
    private Material.Baked missingSpriteForceTranslucent;
    @Unique
    private MaterialMappingStorage mappings = MaterialMappingStorage.EMPTY;

    @Shadow
    public abstract Material.Baked get(Material material, ModelDebugName name);

    @Override
    public Material.Baked zine$get(Material material) {
        return this.get(material, () -> "Unknown Model");
    }

    @Override
    public Material.Baked zine$getMissing(boolean forceTranslucent) {
        return forceTranslucent ? this.missingSpriteForceTranslucent : this.missingSprite;
    }

    @Override
    public MaterialMappingStorage zine$getMappings() {
        return this.mappings;
    }

    @Override
    public void zine$setMappings(MaterialMappingStorage mappings) {
        this.mappings = mappings;
    }
}
