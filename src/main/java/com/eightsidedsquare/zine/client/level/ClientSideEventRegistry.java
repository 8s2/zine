package com.eightsidedsquare.zine.client.level;

import com.eightsidedsquare.zine.common.level.ClientSideEvent;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@Environment(EnvType.CLIENT)
public final class ClientSideEventRegistry {
    private static final Map<ClientSideEvent<?>, EntityCallback<?>> ENTITY_CALLBACKS = new Reference2ObjectOpenHashMap<>();
    private static final Map<ClientSideEvent<?>, PositionedCallback<?>> POSITIONED_CALLBACKS = new Reference2ObjectOpenHashMap<>();
    private static final Map<ClientSideEvent<?>, GlobalCallback<?>> GLOBAL_CALLBACKS = new Reference2ObjectOpenHashMap<>();

    public static <T> void registerEntity(ClientSideEvent<T> event, EntityCallback<T> callback) {
        ENTITY_CALLBACKS.put(event, callback);
    }

    public static void registerEntity(ClientSideEvent<Void> event, EntityCallback.Parameterless callback) {
        ENTITY_CALLBACKS.put(event, callback);
    }

    public static <T> void registerPositioned(ClientSideEvent<T> event, PositionedCallback<T> callback) {
        POSITIONED_CALLBACKS.put(event, callback);
        registerEntity(event, (minecraft, level, entity, data) -> callback.run(minecraft, level, entity.blockPosition(), data));
    }

    public static void registerPositioned(ClientSideEvent<Void> event, PositionedCallback.Parameterless callback) {
        POSITIONED_CALLBACKS.put(event, callback);
        registerEntity(event, (minecraft, level, entity) -> callback.run(minecraft, level, entity.blockPosition()));
    }

    public static <T> void registerGlobal(ClientSideEvent<T> event, GlobalCallback<T> callback) {
        GLOBAL_CALLBACKS.put(event, callback);
        registerPositioned(event, (minecraft, level, _, data) -> callback.run(minecraft, level, data));
    }

    public static void registerGlobal(ClientSideEvent<Void> event, GlobalCallback.Parameterless callback) {
        GLOBAL_CALLBACKS.put(event, callback);
        registerPositioned(event, (minecraft, level, _) -> callback.run(minecraft, level));
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> EntityCallback<T> getEntityCallback(ClientSideEvent<T> event) {
        return (EntityCallback<T>) ENTITY_CALLBACKS.get(event);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> PositionedCallback<T> getPositionedCallback(ClientSideEvent<T> event) {
        return (PositionedCallback<T>) POSITIONED_CALLBACKS.get(event);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> GlobalCallback<T> getGlobalCallback(ClientSideEvent<T> event) {
        return (GlobalCallback<T>) GLOBAL_CALLBACKS.get(event);
    }

    @FunctionalInterface
    public interface EntityCallback<T> {
        void run(Minecraft minecraft, ClientLevel level, Entity entity, T data);

        @FunctionalInterface
        interface Parameterless extends EntityCallback<Void> {
            @Override
            default void run(Minecraft minecraft, ClientLevel level, Entity entity, Void data) {
                this.run(minecraft, level, entity);
            }

            void run(Minecraft minecraft, ClientLevel level, Entity entity);
        }
    }

    @FunctionalInterface
    public interface PositionedCallback<T> {
        void run(Minecraft minecraft, ClientLevel level, BlockPos pos, T data);

        @FunctionalInterface
        interface Parameterless extends PositionedCallback<Void> {
            @Override
            default void run(Minecraft minecraft, ClientLevel level, BlockPos pos, Void data) {
                this.run(minecraft, level, pos);
            }

            void run(Minecraft minecraft, ClientLevel level, BlockPos pos);
        }
    }

    @FunctionalInterface
    public interface GlobalCallback<T> {
        void run(Minecraft minecraft, ClientLevel level, T data);

        @FunctionalInterface
        interface Parameterless extends GlobalCallback<Void> {
            @Override
            default void run(Minecraft minecraft, ClientLevel level, Void data) {
                this.run(minecraft, level);
            }

            void run(Minecraft minecraft, ClientLevel level);
        }
    }

    private ClientSideEventRegistry() {
    }
}
