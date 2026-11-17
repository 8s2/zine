package com.eightsidedsquare.zine.common.util;

import com.eightsidedsquare.zine.common.util.network.StreamCodecUtil;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.IdMapper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public final class InteractionResultSerialization {
    public static final Codec<InteractionResult> NAMED_CODEC = createNamedCodec();
    public static final Codec<InteractionResult> CODEC = createCodec();
    public static final StreamCodec<ByteBuf, InteractionResult> NAMED_STREAM_CODEC = createNamedStreamCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, InteractionResult> STREAM_CODEC = createStreamCodec();

    private static Codec<InteractionResult> createNamedCodec() {
        ExtraCodecs.LateBoundIdMapper<String, InteractionResult> idMapper = new ExtraCodecs.LateBoundIdMapper<>();
        idMapper.put("success", InteractionResult.SUCCESS);
        idMapper.put("success_server", InteractionResult.SUCCESS_SERVER);
        idMapper.put("consume", InteractionResult.CONSUME);
        idMapper.put("fail", InteractionResult.FAIL);
        idMapper.put("pass", InteractionResult.PASS);
        idMapper.put("try_with_empty_hand", InteractionResult.TRY_WITH_EMPTY_HAND);
        return idMapper.codec(Codec.STRING);
    }

    private static Codec<InteractionResult> createCodec() {
        Codec<InteractionResult.Success> successCodec = RecordCodecBuilder.create(i -> i.group(
                createSwingSourceCodec().fieldOf("swing_source").forGetter(InteractionResult.Success::swingSource),
                Codec.BOOL.fieldOf("was_item_interaction").forGetter(InteractionResult.Success::wasItemInteraction),
                ItemStack.OPTIONAL_CODEC.optionalFieldOf("held_item_transformed_to")
                        .forGetter(success -> Optional.ofNullable(success.heldItemTransformedTo()))
        ).apply(i, InteractionResultSerialization::createSuccess));
        return Codec.either(NAMED_CODEC, successCodec).xmap(
                Either::unwrap,
                InteractionResultSerialization::toEither
        );
    }

    private static Codec<InteractionResult.SwingSource> createSwingSourceCodec() {
        ExtraCodecs.LateBoundIdMapper<String, InteractionResult.SwingSource> idMapper = new ExtraCodecs.LateBoundIdMapper<>();
        idMapper.put("none", InteractionResult.SwingSource.NONE);
        idMapper.put("predicted", InteractionResult.SwingSource.PREDICTED);
        idMapper.put("server_only", InteractionResult.SwingSource.SERVER_ONLY);
        return idMapper.codec(Codec.STRING);
    }

    private static StreamCodec<ByteBuf, InteractionResult> createNamedStreamCodec() {
        IdMapper<InteractionResult> idMapper = new IdMapper<>(6);
        idMapper.add(InteractionResult.SUCCESS);
        idMapper.add(InteractionResult.SUCCESS_SERVER);
        idMapper.add(InteractionResult.CONSUME);
        idMapper.add(InteractionResult.FAIL);
        idMapper.add(InteractionResult.PASS);
        idMapper.add(InteractionResult.TRY_WITH_EMPTY_HAND);
        return ByteBufCodecs.idMapper(idMapper);
    }

    private static StreamCodec<RegistryFriendlyByteBuf, InteractionResult> createStreamCodec() {
        StreamCodec<RegistryFriendlyByteBuf, InteractionResult.Success> successStreamCodec = StreamCodec.composite(
                StreamCodecUtil.ofEnum(InteractionResult.SwingSource.values()),
                InteractionResult.Success::swingSource,
                ByteBufCodecs.BOOL,
                InteractionResult.Success::wasItemInteraction,
                ItemStack.OPTIONAL_UNTRUSTED_STREAM_CODEC.apply(ByteBufCodecs::optional),
                success -> Optional.ofNullable(success.heldItemTransformedTo()),
                InteractionResultSerialization::createSuccess
        );
        return ByteBufCodecs.either(NAMED_STREAM_CODEC, successStreamCodec).map(
                Either::unwrap,
                InteractionResultSerialization::toEither
        );
    }

    private static InteractionResult.Success createSuccess(
            InteractionResult.SwingSource swingSource,
            boolean wasItemInteraction,
            Optional<ItemStack> heldItemTransformedTo
    ) {
        return new InteractionResult.Success(swingSource, new InteractionResult.ItemContext(wasItemInteraction, heldItemTransformedTo.orElse(null)));
    }

    private static Either<InteractionResult, InteractionResult.Success> toEither(InteractionResult interactionResult) {
        if (interactionResult instanceof InteractionResult.Success success
                && success != InteractionResult.SUCCESS
                && success != InteractionResult.SUCCESS_SERVER) {
            return Either.right(success);
        }
        return Either.left(interactionResult);
    }

    private InteractionResultSerialization() {
    }
}
