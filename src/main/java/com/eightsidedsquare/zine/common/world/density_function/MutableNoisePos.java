package com.eightsidedsquare.zine.common.world.density_function;

import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.DensityFunction;

public class MutableNoisePos implements DensityFunction.FunctionContext {

    private int x;
    private int y;
    private int z;

    public MutableNoisePos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MutableNoisePos setX(int x) {
        this.x = x;
        return this;
    }

    public MutableNoisePos setY(int y) {
        this.y = y;
        return this;
    }

    public MutableNoisePos setZ(int z) {
        this.z = z;
        return this;
    }

    public MutableNoisePos set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public MutableNoisePos add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public MutableNoisePos add(Vec3i vec3i) {
        return this.add(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    @Override
    public int blockX() {
        return this.x;
    }

    @Override
    public int blockY() {
        return this.y;
    }

    @Override
    public int blockZ() {
        return this.z;
    }
}
