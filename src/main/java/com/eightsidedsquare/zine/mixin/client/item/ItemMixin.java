package com.eightsidedsquare.zine.mixin.client.item;

import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistryImpl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void zine$init(Item.Properties properties, CallbackInfo ci) {
        ArmorType armorType = properties.zine$getArmorType();
        if(armorType != null) {
            ArmorTrimRegistryImpl.addArmorItem((Item) (Object) this, armorType);
        }
    }
}
