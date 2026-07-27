package de.blockmaster.vanilla_taa.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.blockmaster.vanilla_taa.util.ModToolActions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class DaggerItem extends SwordItem implements Vanishable {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public static final AttributeModifier REACH_MODIFIER =
            new AttributeModifier(
                    UUID.fromString("f6a02b60-3719-4c55-9114-2f1169c789cf"),
                    "Weapon reach",
                    -1,
                    AttributeModifier.Operation.ADDITION);

    public DaggerItem(Tier tier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties itemProperties) {
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
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ModToolActions.DEFAULT_DAGGER_ACTIONS.contains(toolAction);
    }
}
