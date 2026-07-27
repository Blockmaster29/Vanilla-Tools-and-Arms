package de.blockmaster.vanilla_taa.items;

import de.blockmaster.vanilla_taa.util.ModToolActions;
import net.minecraft.world.item.*;

public class BattleAxeItem extends AxeItem implements Vanishable {
    public BattleAxeItem(Tier tier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties itemProperties) {
        super(tier, pAttackDamageModifier, pAttackSpeedModifier, itemProperties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ModToolActions.DEFAULT_BATTLE_AXE_ACTIONS.contains(toolAction);
    }
}
