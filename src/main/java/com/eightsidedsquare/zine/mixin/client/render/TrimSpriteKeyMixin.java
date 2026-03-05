package com.eightsidedsquare.zine.mixin.client.render;

import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistryImpl;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(targets = "net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer$TrimSpriteKey")
public abstract class TrimSpriteKeyMixin {

    @Shadow @Final private ArmorTrim trim;

    @ModifyReturnValue(method = "spriteId", at = @At("RETURN"))
    private Identifier zine$changeTextureNamespace(Identifier original) {
        Optional<ResourceKey<TrimMaterial>> optional = this.trim.material().unwrapKey();
        if(optional.isPresent() && ArmorTrimRegistryImpl.containsMaterial(optional.get())) {
            return Identifier.fromNamespaceAndPath(optional.get().identifier().getNamespace(), original.getPath());
        }
        return original;
    }

}
