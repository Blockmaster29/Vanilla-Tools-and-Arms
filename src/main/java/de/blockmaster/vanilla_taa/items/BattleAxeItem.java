package de.blockmaster.vanilla_taa.items;

import de.blockmaster.vanilla_taa.util.ModToolActions;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class BattleAxeItem extends AxeItem implements Vanishable {
    public BattleAxeItem(Tier tier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties itemProperties) {
        super(tier, pAttackDamageModifier, pAttackSpeedModifier, itemProperties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.BANE_OF_ARTHROPODS
                || enchantment == Enchantments.SHARPNESS
                || enchantment == Enchantments.SMITE
                || enchantment == Enchantments.FIRE_ASPECT
                || enchantment == Enchantments.KNOCKBACK
                || enchantment == Enchantments.MOB_LOOTING
                || enchantment == Enchantments.MENDING
                || enchantment == Enchantments.UNBREAKING
                || enchantment == Enchantments.VANISHING_CURSE
                || enchantment == Enchantments.BLOCK_EFFICIENCY
                || enchantment == Enchantments.BLOCK_FORTUNE
                || enchantment == Enchantments.SILK_TOUCH
                || enchantment == Enchantments.SWEEPING_EDGE;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ModToolActions.DEFAULT_BATTLE_AXE_ACTIONS.contains(toolAction);
    }
}
