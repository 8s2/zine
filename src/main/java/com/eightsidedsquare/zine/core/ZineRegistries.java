package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.text.CustomStyleAttribute;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;

public interface ZineRegistries {

    Registry<CustomStyleAttribute<?>> CUSTOM_STYLE_ATTRIBUTES = FabricRegistryBuilder
            .createSimple(ZineRegistryKeys.CUSTOM_STYLE_ATTRIBUTE)
            .buildAndRegister();

    static void init() {
    }

}
