package com.eightsidedsquare.zine.common.util.shape;

import com.eightsidedsquare.zine.common.state.StateMap;
import com.eightsidedsquare.zine.common.state.StateMapBuilder;
import com.mojang.math.Quadrant;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class VoxelShapeUtil {
    private VoxelShapeUtil() {
    }

    public static final VoxelShape EMPTY = Shapes.empty();

    /**
     * Creates the state map for {@link net.minecraft.world.level.block.WallBlock} properties with the voxel shapes combined for each permutation.
     * @param upShape the voxel shape of the center column
     * @param lowShape the voxel shape of the connecting north side when low
     * @param tallShape the voxel shape of the connecting north side when tall
     */
    public static StateMap<VoxelShape> createWallMap(VoxelShape upShape, VoxelShape lowShape, VoxelShape tallShape) {
        return createWallLikeMap(BlockStateProperties.UP, upShape, lowShape, tallShape);
    }

    /**
     * Creates the state map for {@link net.minecraft.world.level.block.MossyCarpetBlock} properties with the voxel shapes combined for each permutation.
     * @param bottomShape the voxel shape of the bottom carpet
     * @param lowShape the voxel shape of the connecting north side when low
     * @param tallShape the voxel shape of the connecting north side when tall
     */
    public static StateMap<VoxelShape> createMossyCarpetMap(VoxelShape bottomShape, VoxelShape lowShape, VoxelShape tallShape) {
        return createWallLikeMap(BlockStateProperties.BOTTOM, bottomShape, lowShape, tallShape);
    }

    /**
     * Creates the state map for {@link net.minecraft.world.level.block.CrossCollisionBlock} properties with the voxel shapes combined for each permutation.
     * @param centerShape the voxel shape at the center of each shape
     * @param sideShape the voxel shape of the connecting side facing north
     */
    public static StateMap<VoxelShape> createHorizontalConnectingMap(VoxelShape centerShape, VoxelShape sideShape) {
        VoxelShape eastShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R90));
        VoxelShape southShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R180));
        VoxelShape westShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R270));
        return StateMapBuilder.create(
                BlockStateProperties.NORTH,
                BlockStateProperties.EAST,
                BlockStateProperties.SOUTH,
                BlockStateProperties.WEST,
                (north, east, south, west) -> Shapes.or(
                        centerShape,
                        north ? sideShape : EMPTY,
                        east ? eastShape : EMPTY,
                        south ? southShape : EMPTY,
                        west ? westShape : EMPTY
                )
        );
    }

    /**
     * Creates the state map for {@link net.minecraft.world.level.block.PipeBlock} or {@link net.minecraft.world.level.block.MultifaceBlock} properties with the voxel shapes combined for each permutation.
     * @param centerShape the voxel shape at the center of each shape
     * @param sideShape the voxel shape of the connecting side facing north
     */
    public static StateMap<VoxelShape> createConnectingMap(VoxelShape centerShape, VoxelShape sideShape) {
        VoxelShape eastShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R90));
        VoxelShape southShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R180));
        VoxelShape westShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R270));
        VoxelShape upShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R270, Quadrant.R0));
        VoxelShape downShape = Shapes.rotate(sideShape, Quadrant.fromXYAngles(Quadrant.R90, Quadrant.R0));
        return StateMapBuilder.create(
                BlockStateProperties.NORTH,
                BlockStateProperties.EAST,
                BlockStateProperties.SOUTH,
                BlockStateProperties.WEST,
                BlockStateProperties.UP,
                BlockStateProperties.DOWN,
                (north, east, south, west, up, down) -> Shapes.or(
                        centerShape,
                        north ? sideShape : EMPTY,
                        east ? eastShape : EMPTY,
                        south ? southShape : EMPTY,
                        west ? westShape : EMPTY,
                        up ? upShape : EMPTY,
                        down ? downShape : EMPTY
                )
        );
    }

    private static StateMap<VoxelShape> createWallLikeMap(BooleanProperty centerProperty, VoxelShape centerShape, VoxelShape lowShape, VoxelShape tallShape) {
        VoxelShape[] northShapes = {EMPTY, lowShape, tallShape};
        VoxelShape[] eastShapes = {
                EMPTY,
                Shapes.rotate(lowShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R90)),
                Shapes.rotate(tallShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R90))
        };
        VoxelShape[] southShapes = {
                EMPTY,
                Shapes.rotate(lowShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R180)),
                Shapes.rotate(tallShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R180))
        };
        VoxelShape[] westShapes = {
                EMPTY,
                Shapes.rotate(lowShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R270)),
                Shapes.rotate(tallShape, Quadrant.fromXYAngles(Quadrant.R0, Quadrant.R270))
        };
        return StateMapBuilder.create(
                centerProperty,
                BlockStateProperties.NORTH_WALL,
                BlockStateProperties.EAST_WALL,
                BlockStateProperties.SOUTH_WALL,
                BlockStateProperties.WEST_WALL,
                (center, north, east, south, west) -> Shapes.or(
                        center ? centerShape : EMPTY,
                        northShapes[north.ordinal()],
                        eastShapes[east.ordinal()],
                        southShapes[south.ordinal()],
                        westShapes[west.ordinal()]
                )
        );
    }

}
