package com.eightsidedsquare.zine.mixin.item;

import com.eightsidedsquare.zine.client.trim.ArmorTrimRegistryImpl;
import com.eightsidedsquare.zine.common.item.ZineItem;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin implements ZineItem {

    @Shadow
    public abstract DataComponentMap components();

    @Override
    public boolean zine$modelEquals(Identifier modelId) {
        return modelId.equals(this.components().get(DataComponents.ITEM_MODEL));
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void zine$init(Item.Properties properties, CallbackInfo ci) {
        ArmorType armorType = properties.zine$getArmorType();
        if(armorType != null) {
            ArmorTrimRegistryImpl.addArmorItem((Item) (Object) this, armorType);
        }
    }
}
