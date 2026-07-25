package de.blockmaster.vanilla_taa.goals;

import de.blockmaster.vanilla_taa.items.SpearItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ThowSpearGoal extends Goal {
    private final Mob mob;
    private int timeLeft = 30;

    public ThowSpearGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null;
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();

        if (target != null) {
            if (mob.getMainHandItem().getItem() instanceof SpearItem) {
                if (mob.distanceToSqr(target) < 100.0D) {
                    if (!mob.isUsingItem()) {
                        mob.startUsingItem(InteractionHand.MAIN_HAND);
                    } else {
                        timeLeft--;
                        if (timeLeft == 0) {
                            mob.releaseUsingItem();
                            timeLeft = 30;
                        }
                    }
                }
            }
        }
    }
}
