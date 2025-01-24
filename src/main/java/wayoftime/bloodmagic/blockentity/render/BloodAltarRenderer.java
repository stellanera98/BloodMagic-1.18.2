package wayoftime.bloodmagic.blockentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import wayoftime.bloodmagic.blockentity.BloodAltarTile;
import wayoftime.bloodmagic.fluid.BMFluids;
import wayoftime.bloodmagic.util.helper.RenderHelper;

public class BloodAltarRenderer implements BlockEntityRenderer<BloodAltarTile> {

    public BloodAltarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BloodAltarTile tileAltar, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack inputStack = tileAltar.inv.getStackInSlot(0);
        this.renderItem(inputStack, tileAltar.getLevel(), poseStack, bufferSource, packedLight, packedOverlay);

        float level = ((float) tileAltar.mainTank / (float) tileAltar.getMainCapacity());
        this.renderFluid(level, poseStack, bufferSource, packedLight, packedOverlay);
    }

    private void renderItem(ItemStack inputStack, Level level , PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        //inputStack = new ItemStack(Blocks.DIAMOND_BLOCK);
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemRenderer = mc.getItemRenderer();
        if (!inputStack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5, 1, 0.5);
            float rotation = (float) (720.0F * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            BakedModel bakedModel = itemRenderer.getModel(inputStack, level, (LivingEntity) null, 1);
            itemRenderer.render(inputStack, ItemDisplayContext.FIXED, true, poseStack, bufferSource, packedLight, packedOverlay, bakedModel);
            poseStack.popPose();
        }
    }

    private void renderFluid(float fluidLevel, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        IClientFluidTypeExtensions fluidClientInfo = IClientFluidTypeExtensions.of(BMFluids.LIFE_ESSENCE_TYPE.get());
        RenderType blockRenderType = ItemBlockRenderTypes.getRenderLayer(BMFluids.LIFE_ESSENCE_SOURCE.get().defaultFluidState());
        TextureAtlasSprite sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidClientInfo.getStillTexture());
        int color = fluidClientInfo.getTintColor();

        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();

        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Vector3f norm = new Vector3f();
        pose.transformNormal(0, 1, 0, norm);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderTypeHelper.getEntityRenderType(blockRenderType, false));
        float minHeight = 8F/16F;
        float maxHeight = 12F/16F;
        float start = 3F/16F;
        float end = 13F/16F;
        float height = minHeight + fluidLevel * (maxHeight - minHeight);

        RenderHelper.addVertex(vertexConsumer, matrix, end, height, start, u0, v0, color, packedLight, packedOverlay, norm);
        RenderHelper.addVertex(vertexConsumer, matrix, start, height, start, u1, v0, color, packedLight, packedOverlay, norm);
        RenderHelper.addVertex(vertexConsumer, matrix, end, height, end, u0, v1, color, packedLight, packedOverlay, norm);
        RenderHelper.addVertex(vertexConsumer, matrix, start, height, end, u1, v1, color, packedLight, packedOverlay, norm);

        poseStack.popPose();
    }
}
