package com.eightsidedsquare.zine.common.text;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.data.DataSource;
import net.minecraft.network.chat.contents.data.DataSources;
import net.minecraft.network.chat.contents.objects.ObjectInfo;
import net.minecraft.network.chat.contents.objects.ObjectInfos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class TextUtilImpl {

    public static ExtraCodecs.LateBoundIdMapper<String, MapCodec<? extends ComponentContents>> textContentIds;

    public static void registerTextContent(Identifier id, MapCodec<? extends ComponentContents> codec) {
        textContentIds.put(id.toString(), codec);
    }

    public static void registerTextObjectContents(Identifier id, MapCodec<? extends ObjectInfo> codec) {
        ObjectInfos.ID_MAPPER.put(id.toString(), codec);
    }

    public static void registerNbtDataSource(Identifier id, MapCodec<? extends DataSource> codec) {
        DataSources.ID_MAPPER.put(id.toString(), codec);
    }

    private TextUtilImpl() {
    }
}
