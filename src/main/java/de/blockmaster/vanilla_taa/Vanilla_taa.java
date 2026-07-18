package de.blockmaster.vanilla_taa;

import com.mojang.logging.LogUtils;
import de.blockmaster.vanilla_taa.entitys.ModEntityTypes;
import de.blockmaster.vanilla_taa.entitys.renderes.ThrownSpearRenderer;
import de.blockmaster.vanilla_taa.items.ModCreativeModeTabs;
import de.blockmaster.vanilla_taa.items.ModItemProperties;
import de.blockmaster.vanilla_taa.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Vanilla_taa.MODID)
public class Vanilla_taa {
    public static final String MODID = "vanilla_taa";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Vanilla_taa() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModEntityTypes.register(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = Vanilla_taa.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            event.enqueueWork(() -> {
                ModItemProperties.register(ModItems.WOODEN_SPEAR.get());
                ModItemProperties.register(ModItems.STONE_SPEAR.get());
                ModItemProperties.register(ModItems.IRON_SPEAR.get());
                ModItemProperties.register(ModItems.GOLDEN_SPEAR.get());
                ModItemProperties.register(ModItems.DIAMOND_SPEAR.get());
                ModItemProperties.register(ModItems.NETHERITE_SPEAR.get());
            });

        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(
                    ModEntityTypes.THROWN_SPEAR.get(),
                    ThrownSpearRenderer::new
            );
        }
    }
}
