package com.eightsidedsquare.zine.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

public interface ZineLevelAccessor {
    /**
     * Executes an entity-based client side event
     */
    default <T> void zine$clientSideEvent(ClientSideEvent<T> event, Entity entity, T data) {
    }

    /**
     * Executes a parameterless entity-based client side event
     */
    default void zine$clientSideEvent(ClientSideEvent<Void> event, Entity entity) {
        this.zine$clientSideEvent(event, entity, null);
    }

    /**
     * Executes a position-based client side event
     */
    default <T> void zine$clientSideEvent(ClientSideEvent<T> event, BlockPos pos, T data) {
    }

    /**
     * Executes a parameterless position-based client side event
     */
    default void zine$clientSideEvent(ClientSideEvent<Void> event, BlockPos pos) {
        this.zine$clientSideEvent(event, pos, null);
    }

    /**
     * Executes a client side event
     */
    default <T> void zine$clientSideEvent(ClientSideEvent<T> event, T data) {
    }

    /**
     * Executes a parameterless client side event
     */
    default void zine$clientSideEvent(ClientSideEvent<Void> event) {
        this.zine$clientSideEvent(event, (Void) null);
    }
}
