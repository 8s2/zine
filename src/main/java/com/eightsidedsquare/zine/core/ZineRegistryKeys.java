package com.eightsidedsquare.zine.core;

import com.eightsidedsquare.zine.common.text.CustomStyleAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public interface ZineRegistryKeys {

    RegistryKey<Registry<CustomStyleAttribute<?>>> CUSTOM_STYLE_ATTRIBUTE = RegistryKey.ofRegistry(ZineMod.id("custom_style_attribute"));

}
