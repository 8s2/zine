package com.eightsidedsquare.zine.common.text;

import com.mojang.serialization.MapCodec;
import net.minecraft.text.NbtDataSource;
import net.minecraft.text.TextContent;
import net.minecraft.text.object.TextObjectContents;
import net.minecraft.util.Identifier;

public final class TextUtil {

    public static void registerTextContent(Identifier id, MapCodec<? extends TextContent> codec) {
        TextUtilImpl.registerTextContent(id, codec);
    }

    public static void registerTextObjectContents(Identifier id, MapCodec<? extends TextObjectContents> codec) {
        TextUtilImpl.registerTextObjectContents(id, codec);
    }

    public static void registerNbtDataSource(Identifier id, MapCodec<? extends NbtDataSource> codec) {
        TextUtilImpl.registerNbtDataSource(id, codec);
    }

    private TextUtil() {
    }
}
