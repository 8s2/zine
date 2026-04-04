package com.eightsidedsquare.zine.client.block;

import com.eightsidedsquare.zine.client.util.ConnectedPattern;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConnectedPatternCalculator {

    public static final Map<Direction, ConnectedPatternCalculator> FAST_CUBE = ImmutableMap.<Direction, ConnectedPatternCalculator>builder()
            .put(Direction.NORTH, create(Direction.NORTH, Direction.UP, Direction.WEST))
            .put(Direction.EAST, create(Direction.EAST, Direction.UP, Direction.NORTH))
            .put(Direction.SOUTH, create(Direction.SOUTH, Direction.UP, Direction.EAST))
            .put(Direction.WEST, create(Direction.WEST, Direction.UP, Direction.SOUTH))
            .put(Direction.UP, create(Direction.UP, Direction.NORTH, Direction.EAST))
            .put(Direction.DOWN, create(Direction.DOWN, Direction.SOUTH, Direction.EAST))
            .build();
    public static final Map<Direction, ConnectedPatternCalculator> FANCY_CUBE = ImmutableMap.<Direction, ConnectedPatternCalculator>builder()
            .put(Direction.NORTH, createFancy(FAST_CUBE.get(Direction.NORTH), Direction.NORTH, Direction.UP, Direction.WEST))
            .put(Direction.EAST, createFancy(FAST_CUBE.get(Direction.EAST), Direction.EAST, Direction.UP, Direction.NORTH))
            .put(Direction.SOUTH, createFancy(FAST_CUBE.get(Direction.SOUTH), Direction.SOUTH, Direction.UP, Direction.EAST))
            .put(Direction.WEST, createFancy(FAST_CUBE.get(Direction.WEST), Direction.WEST, Direction.UP, Direction.SOUTH))
            .put(Direction.UP, createFancy(FAST_CUBE.get(Direction.UP), Direction.UP, Direction.NORTH, Direction.EAST))
            .put(Direction.DOWN, createFancy(FAST_CUBE.get(Direction.DOWN), Direction.DOWN, Direction.SOUTH, Direction.EAST))
            .build();

    private final Instruction[] instructions;

    private ConnectedPatternCalculator(Instruction[] instructions) {
        this.instructions = instructions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(ConnectedPatternCalculator calculator) {
        return new Builder(calculator);
    }

    public static InstructionBuilder instruction() {
        return new InstructionBuilder();
    }

    public ConnectedPattern calculate(BlockAndTintGetter world, BlockPos pos, BlockState state, AppearancePredicate predicate) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        ConnectedPattern pattern = ConnectedPattern.NNNN;
        for (Instruction instruction : this.instructions) {
            pattern = instruction.apply(pattern, pos, state, mutable, world, predicate);
        }
        return pattern;
    }

    public ConnectedPattern calculate(BlockAndTintGetter world, BlockPos pos, BlockState state) {
        return this.calculate(world, pos, state, AppearancePredicate.block(state.getBlock()));
    }

    public static ConnectedPatternCalculator create(Direction face, Direction up, Direction right) {
        Direction down = up.getOpposite();
        Direction left = right.getOpposite();
        BlockPos.MutableBlockPos offset = new BlockPos.MutableBlockPos().move(up).move(right);
        Builder builder = builder();
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 1, 2 -> offset.move(left);
                case 3, 4 -> offset.move(down);
                case 5, 6 -> offset.move(right);
                case 7 -> offset.move(up);
            }
            InstructionBuilder instruction = instruction().face(face).offset(offset).inverted();
            switch (i) {
                case 0 -> instruction.or(ConnectedPattern.NCNN);
                case 1 -> instruction.or(ConnectedPattern.HHNN);
                case 2 -> instruction.or(ConnectedPattern.CNNN);
                case 3 -> instruction.or(ConnectedPattern.VNNV);
                case 4 -> instruction.or(ConnectedPattern.NNNC);
                case 5 -> instruction.or(ConnectedPattern.NNHH);
                case 6 -> instruction.or(ConnectedPattern.NNCN);
                case 7 -> instruction.or(ConnectedPattern.NVVN);
            }
            builder.then(instruction);
        }
        return builder.build();
    }

    public static ConnectedPatternCalculator createFancy(ConnectedPatternCalculator base, Direction face, Direction up, Direction right) {
        Direction down = up.getOpposite();
        Direction left = right.getOpposite();
        BlockPos.MutableBlockPos offset = new BlockPos.MutableBlockPos().move(up).move(right).move(face);
        Builder builder = builder(base);
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 1, 2 -> offset.move(left);
                case 3, 4 -> offset.move(down);
                case 5, 6 -> offset.move(right);
                case 7 -> offset.move(up);
            }
            InstructionBuilder instruction = instruction().offset(offset);
            switch (i) {
                case 0 -> instruction.or(ConnectedPattern.NCNN).face(left, down);
                case 1 -> instruction.or(ConnectedPattern.HHNN).face(down);
                case 2 -> instruction.or(ConnectedPattern.CNNN).face(right, down);
                case 3 -> instruction.or(ConnectedPattern.VNNV).face(right);
                case 4 -> instruction.or(ConnectedPattern.NNNC).face(right, up);
                case 5 -> instruction.or(ConnectedPattern.NNHH).face(up);
                case 6 -> instruction.or(ConnectedPattern.NNCN).face(left, up);
                case 7 -> instruction.or(ConnectedPattern.NVVN).face(left);
            }
            builder.then(instruction);
        }
        return builder.build();
    }

    interface Instruction {
        ConnectedPattern apply(ConnectedPattern pattern, BlockPos origin, BlockState state, BlockPos.MutableBlockPos mutable, BlockAndTintGetter world, AppearancePredicate predicate);
    }

    @FunctionalInterface
    public interface AppearancePredicate {
        boolean test(BlockPos origin, BlockState originState, BlockPos.MutableBlockPos mutable, BlockState appearanceState, BlockAndTintGetter world);

        static AppearancePredicate block(Block block) {
            return (origin, originState, mutable, appearanceState, world) ->
                    appearanceState.is(block);
        }
    }

    public static class Builder {
        private final List<Instruction> instructions = new ArrayList<>();

        private Builder() {
        }

        private Builder(ConnectedPatternCalculator calculator) {
            this.instructions.addAll(Arrays.asList(calculator.instructions));
        }

        public Builder then(InstructionBuilder instructionBuilder) {
            this.instructions.add(instructionBuilder.build());
            return this;
        }

        public ConnectedPatternCalculator build() {
            return new ConnectedPatternCalculator(this.instructions.toArray(new Instruction[0]));
        }
    }

    public static class InstructionBuilder {
        private Direction[] faces = new Direction[0];
        private int x;
        private int y;
        private int z;
        private ConnectedPattern pattern = ConnectedPattern.NNNN;
        private boolean or = true;
        private boolean inverted;

        private InstructionBuilder() {
        }

        public InstructionBuilder face(Direction... faces) {
            this.faces = faces;
            return this;
        }

        public InstructionBuilder offset(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public InstructionBuilder offset(Vec3i offset) {
            return this.offset(offset.getX(), offset.getY(), offset.getZ());
        }

        public InstructionBuilder or(ConnectedPattern pattern) {
            this.or = true;
            this.pattern = pattern;
            return this;
        }

        public InstructionBuilder and(ConnectedPattern pattern) {
            this.or = false;
            this.pattern = pattern;
            return this;
        }

        public InstructionBuilder inverted() {
            this.inverted = true;
            return this;
        }

        private Instruction build() {
            final int x = this.x;
            final int y = this.y;
            final int z = this.z;
            final ConnectedPattern connectedPattern = this.pattern;
            final boolean or = this.or;
            final boolean inverted = this.inverted;
            if(this.faces.length == 1) {
                Direction face = this.faces[0];
                return (pattern, origin, state, mutable, world, predicate) -> {
                    mutable.setWithOffset(origin, x, y, z);
                    BlockState appearanceState = world.getBlockState(mutable).getAppearance(world, mutable, face, state, origin);
                    if(predicate.test(origin, state, mutable, appearanceState, world) != inverted) {
                        return or ? pattern.or(connectedPattern) : pattern.and(connectedPattern);
                    }
                    return pattern;
                };
            }
            final Direction[] faces = this.faces;
            return (pattern, origin, state, mutable, world, predicate) -> {
                mutable.setWithOffset(origin, x, y, z);
                for (Direction face : faces) {
                    BlockState appearanceState = world.getBlockState(mutable).getAppearance(world, mutable, face, state, origin);
                    if(predicate.test(origin, state, mutable, appearanceState, world) != inverted) {
                        return or ? pattern.or(connectedPattern) : pattern.and(connectedPattern);
                    }
                }
                return pattern;
            };
        }

    }
}
