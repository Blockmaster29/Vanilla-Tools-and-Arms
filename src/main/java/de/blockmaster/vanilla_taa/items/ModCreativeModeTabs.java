package de.blockmaster.vanilla_taa.items;

import de.blockmaster.vanilla_taa.Vanilla_taa;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Vanilla_taa.MODID);

    public static final RegistryObject<CreativeModeTab> WEAPONS_TAB = CREATIVE_MODE_TABS.register("weapons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DIAMOND_DAGGER.get()))
                    .title(Component.translatable("creativetab.weapons_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.WOODEN_DAGGER.get());
                        pOutput.accept(ModItems.WOODEN_BATTLE_AXE.get());
                        pOutput.accept(ModItems.WOODEN_SPEAR.get());
                        pOutput.accept(ModItems.STONE_DAGGER.get());
                        pOutput.accept(ModItems.STONE_BATTLE_AXE.get());
                        pOutput.accept(ModItems.STONE_SPEAR.get());
                        pOutput.accept(ModItems.IRON_DAGGER.get());
                        pOutput.accept(ModItems.IRON_BATTLE_AXE.get());
                        pOutput.accept(ModItems.IRON_SPEAR.get());
                        pOutput.accept(ModItems.GOLDEN_DAGGER.get());
                        pOutput.accept(ModItems.GOLDEN_BATTLE_AXE.get());
                        pOutput.accept(ModItems.GOLDEN_SPEAR.get());
                        pOutput.accept(ModItems.DIAMOND_DAGGER.get());
                        pOutput.accept(ModItems.DIAMOND_BATTLE_AXE.get());
                        pOutput.accept(ModItems.DIAMOND_SPEAR.get());
                        pOutput.accept(ModItems.NETHERITE_DAGGER.get());
                        pOutput.accept(ModItems.NETHERITE_BATTLE_AXE.get());
                        pOutput.accept(ModItems.NETHERITE_SPEAR.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
