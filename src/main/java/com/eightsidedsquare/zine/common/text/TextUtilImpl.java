package com.eightsidedsquare.zine.common.text;

import com.mojang.serialization.MapCodec;
import net.minecraft.text.NbtDataSource;
import net.minecraft.text.NbtDataSourceTypes;
import net.minecraft.text.TextContent;
import net.minecraft.text.object.TextObjectContentTypes;
import net.minecraft.text.object.TextObjectContents;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class TextUtilImpl {

    public static Codecs.IdMapper<String, MapCodec<? extends TextContent>> textContentIds;

    public static void registerTextContent(Identifier id, MapCodec<? extends TextContent> codec) {
        textContentIds.put(id.toString(), codec);
    }

    public static void registerTextObjectContents(Identifier id, MapCodec<? extends TextObjectContents> codec) {
        TextObjectContentTypes.ID_MAPPER.put(id.toString(), codec);
    }

    public static void registerNbtDataSource(Identifier id, MapCodec<? extends NbtDataSource> codec) {
        NbtDataSourceTypes.ID_MAPPER.put(id.toString(), codec);
    }

    private TextUtilImpl() {
    }
}
