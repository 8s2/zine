package com.eightsidedsquare.zine.common.util;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ZineUtil {
    public static final ArmorType[] HUMANOID_EQUIPMENT_TYPES = new ArmorType[]{
            ArmorType.HELMET,
            ArmorType.CHESTPLATE,
            ArmorType.LEGGINGS,
            ArmorType.BOOTS
    };

    public static <T> List<T> addOrUnfreeze(List<T> list, T value) {
        try {
            list.add(value);
        } catch (UnsupportedOperationException e) {
            list = new ObjectArrayList<>(list);
            list.add(value);
        }
        return list;
    }

    public static <T> List<T> addAllOrUnfreeze(List<T> list, Collection<T> values) {
        try {
            list.addAll(values);
        } catch (UnsupportedOperationException e) {
            list = new ObjectArrayList<>(list);
            list.addAll(values);
        }
        return list;
    }

    public static <T> List<T> setOrUnfreeze(List<T> list, int index, T value) {
        try {
            list.set(index, value);
        } catch (UnsupportedOperationException e) {
            list = new ObjectArrayList<>(list);
            list.set(index, value);
        }
        return list;
    }

    public static <K, V> Map<K, V> putOrUnfreeze(Map<K, V> map, K key, V value) {
        try {
            map.put(key, value);
        } catch (UnsupportedOperationException e) {
            map = new Object2ObjectOpenHashMap<>(map);
            map.put(key, value);
        }
        return map;
    }

    public static <K, V> Map<K, V> putAllOrUnfreeze(Map<K, V> map, Map<K, V> values) {
        try {
            map.putAll(values);
        } catch (UnsupportedOperationException e) {
            map = new Object2ObjectOpenHashMap<>(map);
            map.putAll(values);
        }
        return map;
    }

    public static <E, T> HolderSet<T> mergeValue(HolderSet<T> registryEntryList, Function<E, Holder<T>> mapper, E value) {
        if(registryEntryList.size() == 0) {
            return HolderSet.direct(mapper, value);
        }
        List<Holder<T>> list = new ObjectArrayList<>(registryEntryList.iterator());
        list.add(mapper.apply(value));
        return HolderSet.direct(list);
    }

    public static <E, T> HolderSet<T> mergeValues(HolderSet<T> registryEntryList, Function<E, Holder<T>> mapper, Collection<E> values) {
        if(registryEntryList.size() == 0) {
            return HolderSet.direct(mapper, values);
        }
        List<Holder<T>> list = new ObjectArrayList<>(registryEntryList.iterator());
        values.forEach(value -> list.add(mapper.apply(value)));
        return HolderSet.direct(list);
    }

    public static <T> HolderSet<T> mergeValues(HolderSet<T> first, HolderSet<T> second) {
        if(first.size() == 0) {
            return second;
        }else if(second.size() == 0) {
            return first;
        }
        List<Holder<T>> list = new ObjectArrayList<>(first.iterator());
        second.forEach(list::add);
        return HolderSet.direct(list);
    }

    public static <KA, KB, V> Map<KB, V> mapKeys(Map<KA, V> map, Function<KA, KB> keyFunction) {
        ImmutableMap.Builder<KB, V> builder = ImmutableMap.builder();
        map.forEach((k, v) -> builder.put(keyFunction.apply(k), v));
        return builder.build();
    }

    public static <K, VA, VB> Map<K, VB> mapValues(Map<K, VA> map, Function<VA, VB> valueFunction) {
        ImmutableMap.Builder<K, VB> builder = ImmutableMap.builder();
        map.forEach((k, v) -> builder.put(k, valueFunction.apply(v)));
        return builder.build();
    }

    private ZineUtil() {
    }
}
