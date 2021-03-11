package maninhouse.epicfight.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import maninhouse.epicfight.animation.types.ActionAnimation;
import maninhouse.epicfight.animation.types.AimingAnimation;
import maninhouse.epicfight.animation.types.DynamicAnimation;
import maninhouse.epicfight.client.capabilites.entity.ClientPlayerData;
import maninhouse.epicfight.client.model.ClientModel;
import maninhouse.epicfight.client.model.ClientModels;
import maninhouse.epicfight.client.renderer.entity.ArmatureRenderer;
import maninhouse.epicfight.client.renderer.layer.HeldItemLayer;
import maninhouse.epicfight.client.renderer.layer.WearableItemLayer;
import maninhouse.epicfight.model.Armature;
import maninhouse.epicfight.utils.math.Vec4f;
import maninhouse.epicfight.utils.math.VisibleMatrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class FirstPersonRenderer extends ArmatureRenderer<ClientPlayerEntity, ClientPlayerData> {
	public FirstPersonRenderer() {
		super();
		layers.add(new HeldItemLayer<>());
		layers.add(new WearableItemLayer<>(EquipmentSlotType.CHEST));
		layers.add(new WearableItemLayer<>(EquipmentSlotType.LEGS));
		layers.add(new WearableItemLayer<>(EquipmentSlotType.FEET));
	}
	
	@Override
	public void render(ClientPlayerEntity entityIn, ClientPlayerData entitydata, EntityRenderer<? extends Entity> renderer, IRenderTypeBuffer buffer,
			MatrixStack matStackIn, int packedLightIn, float partialTicks) {
		ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
		Vector3d projView = renderInfo.getProjectedView();
		double x = MathHelper.lerp(partialTicks, entityIn.prevPosX, entityIn.getPosX()) - projView.getX();
		double y = MathHelper.lerp(partialTicks, entityIn.prevPosY, entityIn.getPosY()) - projView.getY();
		double z = MathHelper.lerp(partialTicks, entityIn.prevPosZ, entityIn.getPosZ()) - projView.getZ();
		ClientModel model = entitydata.getEntityModel(ClientModels.LOGICAL_CLIENT);
		Armature armature = model.getArmature();
		armature.initializeTransform();
		entitydata.getClientAnimator().setPoseToModel(partialTicks);
		VisibleMatrix4f[] poses = armature.getJointTransforms();
		
		matStackIn.push();
		Vec4f headPos = new Vec4f(0, entityIn.getEyeHeight(), 0, 1.0F);
		VisibleMatrix4f.transform(poses[9], headPos, headPos);
		float pitch = renderInfo.getPitch();
		
		DynamicAnimation base = entitydata.getClientAnimator().getPlayer().getPlay();
		DynamicAnimation mix = entitydata.getClientAnimator().mixLayer.animationPlayer.getPlay();
		
		boolean flag1 = base instanceof ActionAnimation;
		boolean flag2 = mix instanceof AimingAnimation;
		
		float zCoord = flag1 ? 0 : poses[0].m32;
		float posZ = Math.min(headPos.z - zCoord, 0);
		
		if (headPos.z > poses[0].m32) {
			posZ += (poses[0].m32 - headPos.z);
		}
		
		if (!flag2) {
			matStackIn.rotate(Vector3f.XP.rotationDegrees(pitch));
		}
		
		float interpolation = pitch > 0.0F ? pitch / 90.0F : 0.0F;
		matStackIn.translate(x, y - 0.1D - (0.2D * (flag2 ? 0.8D : interpolation)), z + 0.1D + (0.7D * (flag2 ? 0.0D : interpolation)) - posZ);
		
		ClientModels.LOGICAL_CLIENT.ENTITY_BIPED_FIRST_PERSON.draw(matStackIn, buffer.getBuffer(ModRenderTypes.getAnimatedModel(entitydata.getOriginalEntity().getLocationSkin())),
				packedLightIn, 1.0F, 1.0F, 1.0F, 1.0F, poses);
		
		if(!entityIn.isSpectator()) {
			renderLayer(entitydata, entityIn, poses, buffer, matStackIn, packedLightIn, partialTicks);
		}
		
		matStackIn.pop();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(ClientPlayerEntity entityIn) {
		return entityIn.getLocationSkin();
	}
}