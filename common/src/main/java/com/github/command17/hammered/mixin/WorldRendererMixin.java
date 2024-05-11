package com.github.command17.hammered.mixin;

import com.github.command17.hammered.item.HammerItem;
import com.github.command17.hammered.util.BlockUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow
    private static void drawCuboidShapeOutline(MatrixStack matrices, VertexConsumer vertexConsumer, VoxelShape shape, double offsetX, double offsetY, double offsetZ, float red, float green, float blue, float alpha) {
    }

    @Inject(method = "drawBlockOutline", at = @At("HEAD"))
    private void Hammered$drawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        if(this.client.player == null || this.client.world == null) {
            return;
        }

        ItemStack stack = this.client.player.getMainHandStack();

        if (stack.getItem() instanceof HammerItem) {
            if (client.crosshairTarget instanceof BlockHitResult crosshairTarget) {
                BlockPos crosshairPos = crosshairTarget.getBlockPos();
                BlockState crosshairState = client.world.getBlockState(crosshairPos);

                if (!entity.isSneaking()) {
                    if (entity instanceof PlayerEntity player) {
                        if (player.isCreative()) return;
                    }

                    HammerItem.findBlocks(stack, this.client.player, crosshairPos, this.client.world).forEach((blockPos) -> {
                        BlockState otherState = this.client.world.getBlockState(blockPos);

                        VoxelShape outlineShape = otherState.getOutlineShape(this.client.world, blockPos, ShapeContext.of(entity));

                        if (BlockUtil.canMineOther(stack, crosshairState, otherState)) {
                            drawCuboidShapeOutline(
                                    matrixStack,
                                    vertexConsumer,
                                    outlineShape,
                                    (double) blockPos.getX() - cameraX,
                                    (double) blockPos.getY() - cameraY,
                                    (double) blockPos.getZ() - cameraZ,
                                    0,
                                    0,
                                    0,
                                    0.4f
                            );
                        }
                    });
                }
            }
        }
    }
}