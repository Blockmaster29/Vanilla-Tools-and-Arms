package de.blockmaster.vanilla_taa.entitys.renderes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.blockmaster.vanilla_taa.entitys.ThrownSpearEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;

public class ThrownSpearRenderer extends EntityRenderer<ThrownSpearEntity> {

    private final ItemRenderer itemRenderer;

    public ThrownSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ThrownSpearEntity entity,
                       float yaw,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int light) {

        poseStack.pushPose();

        poseStack.scale(1.5F, 1.5F, 1.5F);

        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getXRot() - 45f));

        itemRenderer.renderStatic(
                entity.getSpear(),
                ItemDisplayContext.NONE,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId());

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownSpearEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
