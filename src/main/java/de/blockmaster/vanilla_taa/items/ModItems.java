package de.blockmaster.vanilla_taa.items;

import de.blockmaster.vanilla_taa.Vanilla_taa;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Vanilla_taa.MODID);

    //Items
    public static final RegistryObject<Item> WOODEN_DAGGER = ITEMS.register("wooden_dagger",
            () -> new DaggerItem(Tiers.WOOD, 1, -1, new Item.Properties()));

    public static final RegistryObject<Item> STONE_DAGGER = ITEMS.register("stone_dagger",
            () -> new DaggerItem(Tiers.STONE, 2, -1, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_DAGGER = ITEMS.register("copper_dagger",
            () -> new DaggerItem(ModToolTiers.COPPER, 2, -1, new Item.Properties()));

    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger",
            () -> new DaggerItem(Tiers.IRON, 3, -1, new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_DAGGER = ITEMS.register("golden_dagger",
            () -> new DaggerItem(Tiers.GOLD, 1, -1, new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_DAGGER = ITEMS.register("diamond_dagger",
            () -> new DaggerItem(Tiers.DIAMOND, 4, -1, new Item.Properties()));

    public static final RegistryObject<Item> NETHERITE_DAGGER = ITEMS.register("netherite_dagger",
            () -> new DaggerItem(Tiers.NETHERITE, 5, -1, new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_BATTLE_AXE = ITEMS.register("wooden_battle_axe",
            () -> new BattleAxeItem(Tiers.WOOD, 10, -3.5f, new Item.Properties()));

    public static final RegistryObject<Item> STONE_BATTLE_AXE = ITEMS.register("stone_battle_axe",
            () -> new BattleAxeItem(Tiers.STONE, 10, -3.5f, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_BATTLE_AXE = ITEMS.register("copper_battle_axe",
            () -> new BattleAxeItem(ModToolTiers.COPPER, 10, -3.5f, new Item.Properties()));

    public static final RegistryObject<Item> IRON_BATTLE_AXE = ITEMS.register("iron_battle_axe",
            () -> new BattleAxeItem(Tiers.IRON, 10, -3.5f, new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_BATTLE_AXE = ITEMS.register("golden_battle_axe",
            () -> new BattleAxeItem(Tiers.GOLD, 10, -3.5f, new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_BATTLE_AXE = ITEMS.register("diamond_battle_axe",
            () -> new BattleAxeItem(Tiers.DIAMOND, 10, -3.5f, new Item.Properties()));

    public static final RegistryObject<Item> NETHERITE_BATTLE_AXE = ITEMS.register("netherite_battle_axe",
            () -> new BattleAxeItem(Tiers.NETHERITE, 10, -3.5f, new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new SpearItem(Tiers.WOOD, 2, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new SpearItem(Tiers.STONE, 3, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_SPEAR = ITEMS.register("copper_spear",
            () -> new SpearItem(ModToolTiers.COPPER, 3, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new SpearItem(Tiers.IRON, 4, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
            () -> new SpearItem(Tiers.GOLD, 2, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new SpearItem(Tiers.DIAMOND, 5, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new SpearItem(Tiers.NETHERITE, 6, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModToolTiers.COPPER, 3, -2.4f, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModToolTiers.COPPER, 1.5f, -3, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COPPER, 1, -2.6f, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModToolTiers.COPPER, 7, -3.2f, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModToolTiers.COPPER, -1, -2, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
