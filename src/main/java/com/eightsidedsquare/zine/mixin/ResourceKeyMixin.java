package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.registry.ZineResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResourceKey.class)
public abstract class ResourceKeyMixin<T> implements ZineResourceKey<T> {
    @Shadow @Final private Identifier registryName;

    @Shadow @Final private Identifier identifier;

    @Override
    public String zine$getTranslationKey() {
        return Util.makeDescriptionId(this.registryName.getPath(), this.identifier);
    }

    @Override
    public MutableComponent zine$getName() {
        return Component.translatable(this.zine$getTranslationKey());
    }

    @Override
    public <U> ResourceKey<U> zine$withRegistry(ResourceKey<? extends Registry<U>> registry) {
        return ResourceKey.create(registry, this.identifier);
    }
}
