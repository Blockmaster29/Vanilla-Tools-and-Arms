package de.blockmaster.vanilla_taa.events;

import de.blockmaster.vanilla_taa.Vanilla_taa;
import de.blockmaster.vanilla_taa.goals.ThowSpearGoal;
import de.blockmaster.vanilla_taa.items.BattleAxeItem;
import de.blockmaster.vanilla_taa.items.DaggerItem;
import de.blockmaster.vanilla_taa.items.ModItems;
import de.blockmaster.vanilla_taa.items.SpearItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Vanilla_taa.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
        if (event.getSlotType() != EquipmentSlot.MAINHAND) {
            return;
        }

        if (!(event.getItemStack().getItem() instanceof SpearItem) && !(event.getItemStack().getItem() instanceof DaggerItem)) {
            return;
        }

        event.addModifier(
                ForgeMod.ENTITY_REACH.get(),
                SpearItem.REACH_MODIFIER
        );
    }

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(BuiltInLootTables.BASTION_BRIDGE)) {
            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_DAGGER.get())
                            .setWeight(1))
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_BATTLE_AXE.get())
                            .setWeight(1))
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_SPEAR.get())
                            .setWeight(1))
                    .build();

            event.getTable().addPool(pool);
        }

        if (event.getName().equals(BuiltInLootTables.BASTION_TREASURE)) {
            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_DAGGER.get())
                            .setWeight(6))
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_BATTLE_AXE.get())
                            .setWeight(6))
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_SPEAR.get())
                            .setWeight(6))
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_DAGGER.get())
                            .setWeight(6)
                            .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
                            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8f, 1f))))
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_BATTLE_AXE.get())
                            .setWeight(6)
                            .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
                            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8f, 1f))))
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_SPEAR.get())
                            .setWeight(6)
                            .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
                            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8f, 1f))))
                    .build();

            event.getTable().addPool(pool);
        }

        if (event.getName().equals(BuiltInLootTables.RUINED_PORTAL)) {
            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_DAGGER.get())
                            .setWeight(15)
                            .apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_BATTLE_AXE.get())
                            .setWeight(15)
                            .apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_SPEAR.get())
                            .setWeight(15)
                            .apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                    .build();

            event.getTable().addPool(pool);
        }

        if (event.getName().equals(BuiltInLootTables.END_CITY_TREASURE)) {
            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_DAGGER.get())
                            .setWeight(3)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20f, 39f))
                                    .allowTreasure()))
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_BATTLE_AXE.get())
                            .setWeight(3)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20f, 39f))
                                    .allowTreasure()))
                    .add(LootItem.lootTableItem(ModItems.DIAMOND_SPEAR.get())
                            .setWeight(3)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20f, 39f))
                                    .allowTreasure()))
                    .build();

            event.getTable().addPool(pool);
        }
    }

    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {
        if (!(event.getEntity() instanceof Zombie zombie)) {
            return;
        }

        if (event.getEntity() instanceof ZombifiedPiglin) {
            return;
        }

        zombie.goalSelector.addGoal(1, new ThowSpearGoal(zombie));

        float chance = 0.05f;

        if (zombie.getRandom().nextFloat() < chance) {
            Item[] items = new Item[2];
            items[0] = ModItems.IRON_DAGGER.get();
            items[1] = ModItems.IRON_SPEAR.get();

            int selectedItem = zombie.getRandom().nextInt(items.length);

            zombie.setItemSlot(
                    EquipmentSlot.MAINHAND,
                    new ItemStack(items[selectedItem])
            );

            zombie.setDropChance(EquipmentSlot.MAINHAND, 0.05F);
        }
    }
}
