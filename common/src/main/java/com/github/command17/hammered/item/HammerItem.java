package com.github.command17.hammered.item;

import com.github.command17.hammered.enchantment.ModEnchantments;
import com.github.command17.hammered.util.BlockUtil;
import com.github.command17.hammered.util.EnchantmentUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.stream.Stream;

public class HammerItem extends MiningToolItem implements Vanishable {
    public static final UUID BASE_KNOCKBACK_UUID = UUID.fromString("A6873DB4-C086-42A1-80DD-3998691C1C9C"); // Random UUID

    public final float knockback;

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public HammerItem(ToolMaterial material, int attackDamage, float attackSpeed, float knockback, Item.Settings settings) {
        super(attackDamage, attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings);

        this.knockback = knockback;

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        this.addAttributeModifiers(builder);

        super.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach(builder::put);

        this.attributeModifiers = builder.build();
    }

    public static Stream<BlockPos> findBlocks(ItemStack stack, PlayerEntity player, BlockPos originPos, World world) {
        int level = EnchantmentUtil.getEnchantmentLevel(stack, ModEnchantments.HAMMERED.get());

        if (level > 0) return BlockUtil.findBlocksInRadius(1, level - 1, player, originPos, world);

        return Stream.of();
    }

    public void addAttributeModifiers(ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> attributeList) {
        attributeList.put(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,
                new EntityAttributeModifier(BASE_KNOCKBACK_UUID, "Weapon modifier", this.knockback, EntityAttributeModifier.Operation.ADDITION));
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) return this.miningSpeed;
        else if (state.isIn(BlockTags.HOE_MINEABLE)) return this.miningSpeed;
        else if (state.isIn(BlockTags.AXE_MINEABLE)) return this.miningSpeed;
        else if (state.isIn(BlockTags.SHOVEL_MINEABLE)) return this.miningSpeed;

        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

        int level = EnchantmentUtil.getEnchantmentLevel(stack, ModEnchantments.IMPACT.get());

        if (level > 0 && attacker.fallDistance > 0) {
            target.addVelocity(0, this.knockback / 5 * level, 0);
            target.velocityModified = true;

            if (attacker.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.CLOUD, target.getX(), target.getY(), target.getZ(),
                        20, 0.05, 0, 0.05, 0.12);
            }
        }

        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient) {
            if (miner instanceof PlayerEntity player) {
                if (!player.isSneaking()) {
                    findBlocks(stack, player, pos, world).forEach((blockPos -> {
                        if (blockPos != pos) {
                            BlockState otherState = world.getBlockState(blockPos);

                            if (BlockUtil.canMineOther(stack, state, otherState)) {
                                world.breakBlock(blockPos, false, player);

                                otherState.getBlock().afterBreak(world, player, blockPos, otherState, world.getBlockEntity(blockPos), stack);

                                if (player.getRandom().nextBoolean()) stack.damage(1, player, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                            }
                        }
                    }));
                }
            }
        }

        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) return this.attributeModifiers;

        return super.getAttributeModifiers(slot);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        if (state.isOf(Blocks.COBWEB)) return true;
        else if (state.isIn(BlockTags.HOE_MINEABLE)) return true;
        else if (state.isIn(BlockTags.AXE_MINEABLE)) return true;
        else if (state.isIn(BlockTags.SHOVEL_MINEABLE)) return true;

        return super.isSuitableFor(state);
    }
}
