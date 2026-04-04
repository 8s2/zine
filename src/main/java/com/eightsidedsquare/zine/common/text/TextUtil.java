package com.eightsidedsquare.zine.common.text;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.data.DataSource;
import net.minecraft.network.chat.contents.objects.ObjectInfo;
import net.minecraft.resources.Identifier;

public final class TextUtil {

    public static void registerTextContent(Identifier id, MapCodec<? extends ComponentContents> codec) {
        TextUtilImpl.registerTextContent(id, codec);
    }

    public static void registerTextObjectContents(Identifier id, MapCodec<? extends ObjectInfo> codec) {
        TextUtilImpl.registerTextObjectContents(id, codec);
    }

    public static void registerNbtDataSource(Identifier id, MapCodec<? extends DataSource> codec) {
        TextUtilImpl.registerNbtDataSource(id, codec);
    }

    private TextUtil() {
    }
}
