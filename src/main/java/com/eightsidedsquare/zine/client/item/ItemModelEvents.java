package com.eightsidedsquare.zine.client.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public final class ItemModelEvents {
    private ItemModelEvents() {
    }

    /**
     * Event to replace or modify unbaked item models before they are baked.
     */
    public static final Event<BeforeBake> BEFORE_BAKE = EventFactory.createArrayBacked(BeforeBake.class, callbacks -> (id, unbaked) -> {
        for(BeforeBake callback : callbacks) {
            unbaked = callback.modifyBeforeBake(id, unbaked);
        }
        return unbaked;
    });

    /**
     * Event to dynamically add unbaked item models to the game, in a similar manner to data generation.
     */
    public static final Event<AddUnbaked> ADD_UNBAKED = EventFactory.createArrayBacked(AddUnbaked.class, callbacks -> consumer -> {
        for(AddUnbaked callback : callbacks) {
            callback.addUnbakedModels(consumer);
        }
    });

    @FunctionalInterface
    public interface BeforeBake {
        /**
         * Called to modify unbaked item models. Return {@code unbaked} if no modification is meant to take place.
         * <p>{@code id} may directly correlate to the id of an {@link net.minecraft.world.item.Item} in {@link net.minecraft.core.registries.BuiltInRegistries#ITEM}.
         * @param id the id of the unbaked item model
         * @param unbaked the unbaked item model
         * @return the modified unbaked item model
         */
        ItemModel.Unbaked modifyBeforeBake(Identifier id, ItemModel.Unbaked unbaked);
    }

    @FunctionalInterface
    public interface AddUnbaked {
        /**
         * Called to add unbaked item models to the game.
         * @param assetCollector a consumer that accepts unbaked item models or item assets with an identifier
         */
        void addUnbakedModels(ItemAssetCollector assetCollector);
    }

}
