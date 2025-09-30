package com.eightsidedsquare.zine.mixin;

import com.eightsidedsquare.zine.common.util.ZineComponentMapBuilder;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ComponentMap.Builder.class)
public abstract class ComponentMapBuilderMixin implements ZineComponentMapBuilder {

    @Shadow public abstract <T> ComponentMap.Builder add(ComponentType<T> type, @Nullable T value);

    @Shadow @Final public Reference2ObjectMap<ComponentType<?>, Object> components;

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable T zine$get(ComponentType<T> type) {
        return (T) this.components.get(type);
    }

    @Override
    public <T> ComponentMap.Builder zine$add(Component<T> component) {
        return this.add(component.type(), component.value());
    }

    @Override
    public <T> ComponentMap.Builder zine$remove(ComponentType<T> type) {
        this.components.remove(type);
        return (ComponentMap.Builder) (Object) this;
    }
}
