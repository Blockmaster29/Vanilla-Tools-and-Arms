package de.blockmaster.vanilla_taa.entitys;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class ThrownSpearEntity extends AbstractArrow implements IEntityAdditionalSpawnData {

    private ItemStack spearStack = ItemStack.EMPTY;
    private LivingEntity spearShooter;
    private boolean returning;
    private int loyaltyLevel;

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type, Level level) {
        super(type, level);
    }

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type,
                             LivingEntity shooter,
                             Level level,
                             ItemStack spear) {
        super(type, shooter, level);

        spearShooter = getOwner() instanceof LivingEntity living ? living : null;;

        this.spearStack = spear.copy();
        this.pickup = Pickup.ALLOWED;

        this.loyaltyLevel = spearStack.getAllEnchantments()
                .getOrDefault(Enchantments.LOYALTY, 0);
    }

    public void setSpear(ItemStack stack) {
        this.spearStack = stack.copy();
    }

    public ItemStack getSpear() {
        return spearStack;
    }

    @Override
    protected ItemStack getPickupItem() {
        return spearStack.isEmpty() ? ItemStack.EMPTY : spearStack.copy();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        double damage = spearStack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_DAMAGE)
                .stream()
                .mapToDouble(AttributeModifier::getAmount)
                .sum() + 1;

        if (spearStack.getAllEnchantments().containsKey(Enchantments.SHARPNESS)) {
            double value = (double) spearStack.getAllEnchantments().get(Enchantments.SHARPNESS);
            value = value / 2 + 0.5f;
            damage += value;
        }

        if (spearStack.getAllEnchantments().containsKey(Enchantments.SMITE)) {
            if (result.getEntity() instanceof LivingEntity living &&
            living.getMobType() == MobType.UNDEAD) {
                double value = (double) spearStack.getAllEnchantments().get(Enchantments.SMITE);
                value = value * 2.5f;
                damage += value;
            }
        }

        if (spearStack.getAllEnchantments().containsKey(Enchantments.BANE_OF_ARTHROPODS)) {
            if (result.getEntity() instanceof LivingEntity living &&
                    living.getMobType() == MobType.ARTHROPOD) {
                double value = (double) spearStack.getAllEnchantments().get(Enchantments.BANE_OF_ARTHROPODS);
                value = value * 2.5f;
                damage += value;
            }
        }

        if (spearStack.getAllEnchantments().containsKey(Enchantments.FIRE_ASPECT)) {
            int seconds = spearStack.getAllEnchantments().get(Enchantments.FIRE_ASPECT) * 4;
            if (result.getEntity().getRemainingFireTicks() < seconds * 20) {
                result.getEntity().setRemainingFireTicks(seconds * 20);
            }
        }

        if (spearStack.getAllEnchantments().containsKey(Enchantments.KNOCKBACK)) {
            if (result.getEntity() instanceof LivingEntity living) {
                int knockback = spearStack.getAllEnchantments().get(Enchantments.KNOCKBACK);

                double dx = getX() - result.getEntity().getX();
                double dz = getZ() - result.getEntity().getZ();

                living.knockback(knockback, dx, dz);
            }
        }

        result.getEntity().hurt(
                damageSources().trident(this, this.getOwner()),
                (float) damage
        );

        if (spearShooter instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) spearShooter;

            if (!(player.gameMode.getGameModeForPlayer() == GameType.CREATIVE)) {
                if (spearStack.getAllEnchantments().containsKey(Enchantments.UNBREAKING)) {
                    if (random.nextInt(spearStack.getAllEnchantments().get(Enchantments.UNBREAKING) + 1) == 0) {
                        spearStack.setDamageValue(spearStack.getDamageValue() + 1);
                    }
                } else {
                    spearStack.setDamageValue(spearStack.getDamageValue() + 1);
                }

                if (spearStack.getDamageValue() > spearStack.getMaxDamage()) {
                    level().playSound(
                            null,
                            getX(),
                            getY(),
                            getZ(),
                            SoundEvents.ITEM_BREAK,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                    );

                    if (level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(
                                new ItemParticleOption(ParticleTypes.ITEM, spearStack),
                                getX(),
                                getY(),
                                getZ(),
                                10,
                                0.0,
                                0.0,
                                0.0,
                                0.2
                        );
                    }

                    this.discard();
                }
            }
        }

        this.setDeltaMovement(0.0D, 0.0D, 0.0D);

        if (loyaltyLevel > 0) {
            returning = true;
            this.pickup = Pickup.DISALLOWED;

            level().playSound(
                    null,
                    getX(),
                    getY(),
                    getZ(),
                    SoundEvents.TRIDENT_RETURN,
                    SoundSource.PLAYERS,
                    10.0F,
                    1.0F
            );
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (returning) {
            return; // Ignore blocks while flying back
        }

        super.onHitBlock(result);

        if (loyaltyLevel > 0) {
            returning = true;
            this.pickup = Pickup.DISALLOWED;

            level().playSound(
                    null,
                    getX(),
                    getY(),
                    getZ(),
                    SoundEvents.TRIDENT_RETURN,
                    SoundSource.PLAYERS,
                    10.0F,
                    1.0F
            );
        }
    }

    @Override
    public void tick() {
        if (returning) {
            this.setNoGravity(true);
            this.noPhysics = true;
            this.inGround = false;
        } else {
            this.noPhysics = false;
        }

        super.tick();

        // Slightly heavier than an arrow
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.01D, 0.0D));
        }

        if (!this.inGround && !returning) {
            Vec3 velocity = getDeltaMovement();

            if (velocity.lengthSqr() > 0.0001D) {
                float yaw = (float)(Mth.atan2(velocity.x, velocity.z) * Mth.RAD_TO_DEG);
                float pitch = (float)(Mth.atan2(
                        velocity.y,
                        Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)
                ) * Mth.RAD_TO_DEG);

                setYRot(yaw);
                setXRot(pitch);
            }
        }

        if (returning && getOwner() instanceof LivingEntity owner && owner.isAlive()) {
            Vec3 direction = owner.getEyePosition().subtract(position()).normalize();

            setDeltaMovement(
                    getDeltaMovement()
                            .scale(0.95D)
                            .add(direction.scale(0.12D * loyaltyLevel))
            );

            hasImpulse = true;
            setNoGravity(true);
        }

        if (returning && getOwner() instanceof ServerPlayer player) {

            if (distanceToSqr(player) < 5.0D) {

                if (!(player.gameMode.getGameModeForPlayer() == GameType.CREATIVE)) {
                    if (!player.getInventory().add(spearStack.copy())) {
                        player.drop(spearStack.copy(), false);
                    }
                }

                level().playSound(
                        null,
                        getX(),
                        getY(),
                        getZ(),
                        SoundEvents.ITEM_PICKUP,
                        SoundSource.PLAYERS,
                        10.0F,
                        1.0F
                );

                discard();
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        if (!spearStack.isEmpty()) {
            tag.put("Spear", spearStack.save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("Spear")) {
            spearStack = ItemStack.of(tag.getCompound("Spear"));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeItem(spearStack);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        spearStack = buffer.readItem();
    }
}
