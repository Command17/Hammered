package com.github.command17.hammered.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class BlockUtil {
    public static Stream<BlockPos> findBlocksInRadius(int radius, int depth, PlayerEntity player, BlockPos originPos, World world) {
        Stream<BlockPos> potentialBrokenBlocks = Stream.of();

        Vec3d eyePosition = player.getEyePos();
        Vec3d rotation = player.getRotationVec(1);

        double reach = player.isCreative() ? 5 : 4.5;

        Vec3d combined = eyePosition.add(rotation.x * reach, rotation.y * reach, rotation.z * reach);

        BlockHitResult result = world.raycast(new RaycastContext(player.getEyePos(), combined,
                RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));

        if (result.getType() == HitResult.Type.BLOCK) {
            Direction side = result.getSide();

            boolean doX = side.getOffsetX() == 0;
            boolean doY = side.getOffsetY() == 0;
            boolean doZ = side.getOffsetZ() == 0;

            BlockPos begin = new BlockPos(doX ? -radius : 0, doY ? -radius : 0, doZ ? -radius : 0);
            BlockPos end = new BlockPos(doX ? radius : depth * -side.getOffsetX(), doY ? radius : depth * -side.getOffsetY(), doZ ? radius : depth * -side.getOffsetZ());

            return BlockPos.stream(originPos.add(begin), originPos.add(end));
        }

        return potentialBrokenBlocks;
    }

    public static boolean canMineOther(ItemStack stack, BlockState state, BlockState otherState) {
        return stack.getItem().isSuitableFor(otherState) && otherState.getBlock().getHardness() <= state.getBlock().getHardness();
    }
}
