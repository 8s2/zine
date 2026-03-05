package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.util.ZineDataComponentMapBuilder;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DataComponentMap.Builder.class)
public abstract class DataComponentMapBuilderMixin implements ZineDataComponentMapBuilder {

    @Shadow public abstract <T> DataComponentMap.Builder set(DataComponentType<T> type, @Nullable T value);

    @Shadow @Final public Reference2ObjectMap<DataComponentType<?>, Object> map;

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable T zine$get(DataComponentType<T> type) {
        return (T) this.map.get(type);
    }

    @Override
    public <T> DataComponentMap.Builder zine$add(TypedDataComponent<T> component) {
        return this.set(component.type(), component.value());
    }

    @Override
    public <T> DataComponentMap.Builder zine$remove(DataComponentType<T> type) {
        this.map.remove(type);
        return (DataComponentMap.Builder) (Object) this;
    }
}
