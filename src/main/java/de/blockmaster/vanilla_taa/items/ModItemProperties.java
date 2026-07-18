package de.blockmaster.vanilla_taa.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItemProperties {

    public static void register(Item item) {
        ItemProperties.register(
                item,
                ResourceLocation.fromNamespaceAndPath("vanilla_taa", "using"),
                (ItemStack stack, net.minecraft.client.multiplayer.ClientLevel level,
                 net.minecraft.world.entity.LivingEntity entity, int seed) -> {

                    if (entity == null)
                        return 0;

                    return entity.isUsingItem()
                            && entity.getUseItem().is(stack.getItem())
                            ? 1
                            : 0;
                }
        );
    }
}
