package com.eightsidedsquare.zine.mixin.client.model;

import com.eightsidedsquare.zine.client.model.ZineModelBaker;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelBaker.class)
public interface ModelBakerMixin extends ZineModelBaker {

    @Shadow
    MaterialBaker materials();

    @Override
    default Material.Baked zine$get(Material material) {
        return this.materials().zine$get(material);
    }

    @Override
    default Material.Baked zine$getMissing() {
        return this.materials().zine$getMissing();
    }
}
