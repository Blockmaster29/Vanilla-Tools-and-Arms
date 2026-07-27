package de.blockmaster.vanilla_taa.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.blockmaster.vanilla_taa.entitys.ModEntityTypes;
import de.blockmaster.vanilla_taa.entitys.ThrownSpearEntity;
import de.blockmaster.vanilla_taa.util.ModToolActions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class SpearItem extends SwordItem implements Vanishable {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public static final AttributeModifier REACH_MODIFIER =
            new AttributeModifier(
                    UUID.fromString("f6a02b60-3719-4c55-9114-2f1169c789cf"),
                    "Weapon reach",
                    0.5,
                    AttributeModifier.Operation.ADDITION);

    public SpearItem(Tier tier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties itemProperties) {
        super(tier, pAttackDamageModifier, pAttackSpeedModifier, itemProperties);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        // Vanilla sword attributes
        builder.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(BASE_ATTACK_DAMAGE_UUID,
                        "Weapon damage",
                        pAttackDamageModifier,
                        AttributeModifier.Operation.ADDITION));

        builder.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(BASE_ATTACK_SPEED_UUID,
                        "Weapon speed",
                        pAttackSpeedModifier,
                        AttributeModifier.Operation.ADDITION));

        // Increase attack reach by 2 blocks
        builder.put(ForgeMod.ENTITY_REACH.get(), REACH_MODIFIER);

        defaultModifiers = builder.build();

    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND
                ? defaultModifiers
                : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level,
                                                  Player player,
                                                  InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        player.startUsingItem(hand);

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack,
                             Level level,
                             LivingEntity livingEntity,
                             int timeLeft) {

        int charge = getUseDuration(stack) - timeLeft;

        // Require about half a second of charging
        if (charge < 10)
            return;

        if (!level.isClientSide) {

            ThrownSpearEntity spear =
                    new ThrownSpearEntity(
                            ModEntityTypes.THROWN_SPEAR.get(),
                            livingEntity,
                            level,
                            stack);

            spear.shootFromRotation(
                    livingEntity,
                    livingEntity.getXRot(),
                    livingEntity.getYRot(),
                    0.0F,
                    2.5F,
                    1.0F);

            level.addFreshEntity(spear);

            if (livingEntity instanceof Player player) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                stack.shrink(1);
            }
        }

        level.playSound(
                null,
                livingEntity.getX(),
                livingEntity.getY(),
                livingEntity.getZ(),
                SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ModToolActions.DEFAULT_SPEAR_ACTIONS.contains(toolAction);
    }
}
