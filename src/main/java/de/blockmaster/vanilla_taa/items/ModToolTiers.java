package de.blockmaster.vanilla_taa.items;

import de.blockmaster.vanilla_taa.Vanilla_taa;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier COPPER = TierSortingRegistry.registerTier(
            new ForgeTier(2, 250, 5.5f, 1, 14,
                    BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.COPPER_INGOT)),
            new ResourceLocation(Vanilla_taa.MODID, "copper"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}
