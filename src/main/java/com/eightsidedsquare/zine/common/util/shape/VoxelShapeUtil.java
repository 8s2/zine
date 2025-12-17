package com.eightsidedsquare.zine.common.util.shape;

import com.eightsidedsquare.zine.common.state.StateMap;
import com.eightsidedsquare.zine.common.state.StateMapBuilder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public final class VoxelShapeUtil {
    private VoxelShapeUtil() {
    }

    public static final VoxelShape EMPTY = VoxelShapes.empty();

    /**
     * Creates the state map for {@link net.minecraft.block.WallBlock} properties with the voxel shapes combined for each permutation.
     * @param upShape the voxel shape of the center column
     * @param lowShape the voxel shape of the connecting north side when low
     * @param tallShape the voxel shape of the connecting north side when tall
     */
    public static StateMap<VoxelShape> createWallMap(VoxelShape upShape, VoxelShape lowShape, VoxelShape tallShape) {
        return createWallLikeMap(Properties.UP, upShape, lowShape, tallShape);
    }

    /**
     * Creates the state map for {@link net.minecraft.block.PaleMossCarpetBlock} properties with the voxel shapes combined for each permutation.
     * @param bottomShape the voxel shape of the bottom carpet
     * @param lowShape the voxel shape of the connecting north side when low
     * @param tallShape the voxel shape of the connecting north side when tall
     */
    public static StateMap<VoxelShape> createMossyCarpetMap(VoxelShape bottomShape, VoxelShape lowShape, VoxelShape tallShape) {
        return createWallLikeMap(Properties.BOTTOM, bottomShape, lowShape, tallShape);
    }

    /**
     * Creates the state map for {@link net.minecraft.block.HorizontalConnectingBlock} properties with the voxel shapes combined for each permutation.
     * @param centerShape the voxel shape at the center of each shape
     * @param sideShape the voxel shape of the connecting side facing north
     */
    public static StateMap<VoxelShape> createHorizontalConnectingMap(VoxelShape centerShape, VoxelShape sideShape) {
        VoxelShape eastShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R90));
        VoxelShape southShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R180));
        VoxelShape westShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R270));
        return StateMapBuilder.create(
                Properties.NORTH,
                Properties.EAST,
                Properties.SOUTH,
                Properties.WEST,
                (north, east, south, west) -> VoxelShapes.union(
                        centerShape,
                        north ? sideShape : EMPTY,
                        east ? eastShape : EMPTY,
                        south ? southShape : EMPTY,
                        west ? westShape : EMPTY
                )
        );
    }

    /**
     * Creates the state map for {@link net.minecraft.block.ConnectingBlock} or {@link net.minecraft.block.MultifaceBlock} properties with the voxel shapes combined for each permutation.
     * @param centerShape the voxel shape at the center of each shape
     * @param sideShape the voxel shape of the connecting side facing north
     */
    public static StateMap<VoxelShape> createConnectingMap(VoxelShape centerShape, VoxelShape sideShape) {
        VoxelShape eastShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R90));
        VoxelShape southShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R180));
        VoxelShape westShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R270));
        VoxelShape upShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R270, AxisRotation.R0));
        VoxelShape downShape = VoxelShapes.transform(sideShape, AxisRotation.method_76599(AxisRotation.R90, AxisRotation.R0));
        return StateMapBuilder.create(
                Properties.NORTH,
                Properties.EAST,
                Properties.SOUTH,
                Properties.WEST,
                Properties.UP,
                Properties.DOWN,
                (north, east, south, west, up, down) -> VoxelShapes.union(
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
                VoxelShapes.transform(lowShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R90)),
                VoxelShapes.transform(tallShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R90))
        };
        VoxelShape[] southShapes = {
                EMPTY,
                VoxelShapes.transform(lowShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R180)),
                VoxelShapes.transform(tallShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R180))
        };
        VoxelShape[] westShapes = {
                EMPTY,
                VoxelShapes.transform(lowShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R270)),
                VoxelShapes.transform(tallShape, AxisRotation.method_76599(AxisRotation.R0, AxisRotation.R270))
        };
        return StateMapBuilder.create(
                centerProperty,
                Properties.NORTH_WALL_SHAPE,
                Properties.EAST_WALL_SHAPE,
                Properties.SOUTH_WALL_SHAPE,
                Properties.WEST_WALL_SHAPE,
                (center, north, east, south, west) -> VoxelShapes.union(
                        center ? centerShape : EMPTY,
                        northShapes[north.ordinal()],
                        eastShapes[east.ordinal()],
                        southShapes[south.ordinal()],
                        westShapes[west.ordinal()]
                )
        );
    }

}
