package com.eightsidedsquare.zine.mixin.entity.spawn;

import com.eightsidedsquare.zine.common.entity.spawn.ZineSpawnContext;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.spawn.SpawnContext;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {CatEntity.class, ChickenEntity.class, CowEntity.class, FrogEntity.class, PigEntity.class, WolfEntity.class})
public abstract class VariantEntitiesMixin {

    @ModifyExpressionValue(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/spawn/SpawnContext;of(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/entity/spawn/SpawnContext;"))
    private SpawnContext zine$configure(SpawnContext ctx, ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        return ZineSpawnContext.of(ctx, spawnReason);
    }

}
