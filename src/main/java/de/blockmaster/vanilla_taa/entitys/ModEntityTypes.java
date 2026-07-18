package de.blockmaster.vanilla_taa.entitys;

import de.blockmaster.vanilla_taa.Vanilla_taa;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Vanilla_taa.MODID);

    public static final RegistryObject<EntityType<ThrownSpearEntity>> THROWN_SPEAR =
            ENTITY_TYPES.register("thrown_spear",
                    () -> EntityType.Builder
                            .<ThrownSpearEntity>of(
                                    ThrownSpearEntity::new,
                                    MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("thrown_spear"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
