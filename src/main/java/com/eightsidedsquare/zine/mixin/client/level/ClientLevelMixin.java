package com.eightsidedsquare.zine.mixin.client.level;

import com.eightsidedsquare.zine.client.level.ClientSideEventRegistry;
import com.eightsidedsquare.zine.common.level.ClientSideEvent;
import com.eightsidedsquare.zine.mixin.level.LevelAccessorMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin implements LevelAccessorMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Override
    public <T> void zine$clientSideEvent(ClientSideEvent<T> event, Entity entity, T data) {
        ClientSideEventRegistry.EntityCallback<T> callback = ClientSideEventRegistry.getEntityCallback(event);
        if (callback != null) {
            callback.run(this.minecraft, (ClientLevel) (Object) this, entity, data);
        }
    }

    @Override
    public <T> void zine$clientSideEvent(ClientSideEvent<T> event, BlockPos pos, T data) {
        ClientSideEventRegistry.PositionedCallback<T> callback = ClientSideEventRegistry.getPositionedCallback(event);
        if (callback != null) {
            callback.run(this.minecraft, (ClientLevel) (Object) this, pos, data);
        }
    }

    @Override
    public <T> void zine$clientSideEvent(ClientSideEvent<T> event, T data) {
        ClientSideEventRegistry.GlobalCallback<T> callback = ClientSideEventRegistry.getGlobalCallback(event);
        if (callback != null) {
            callback.run(this.minecraft, (ClientLevel) (Object) this, data);
        }
    }
}
