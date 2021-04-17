package maninhouse.epicfight.client.renderer.layer;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import maninhouse.epicfight.capabilities.ModCapabilities;
import maninhouse.epicfight.capabilities.entity.LivingData;
import maninhouse.epicfight.capabilities.item.ArmorCapability;
import maninhouse.epicfight.client.ClientEngine;
import maninhouse.epicfight.client.model.ClientModel;
import maninhouse.epicfight.client.model.custom.CustomModelBakery;
import maninhouse.epicfight.client.renderer.ModRenderTypes;
import maninhouse.epicfight.main.EpicFightMod;
import maninhouse.epicfight.utils.math.VisibleMatrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

@OnlyIn(Dist.CLIENT)
public class WearableItemLayer<E extends LivingEntity, T extends LivingData<E>> extends Layer<E, T> {
	private static final Map<ResourceLocation, ClientModel> ARMOR_MODEL_MAP = new HashMap<ResourceLocation, ClientModel>();
	private static final Map<BipedModel<?>, ClientModel> ARMOR_MODEL_MAP_BY_MODEL = new HashMap<BipedModel<?>, ClientModel>();
	private final EquipmentSlotType slot;
	
	public WearableItemLayer(EquipmentSlotType slotType) {
		this.slot = slotType;
	}
	
	private void renderArmor(MatrixStack matStack, IRenderTypeBuffer buf, int packedLightIn, boolean hasEffect,
			ClientModel model, float r, float g, float b, ResourceLocation armorResource, VisibleMatrix4f[] poses) {
		IVertexBuilder ivertexbuilder = ModRenderTypes.getArmorVertexBuilder(buf, ModRenderTypes.getAnimatedArmorModel(armorResource), hasEffect);
		model.draw(matStack, ivertexbuilder, packedLightIn, r, g, b, 1.0F, poses);
	}
	
	@Override
	public void renderLayer(T entitydata, E entityliving, MatrixStack matrixStackIn, IRenderTypeBuffer buffer, int packedLightIn, VisibleMatrix4f[] poses, float partialTicks) {
		ItemStack stack = entityliving.getItemStackFromSlot(this.slot);
		Item item = stack.getItem();
		
		matrixStackIn.push();
		if(this.slot == EquipmentSlotType.HEAD && entityliving instanceof ZombieVillagerEntity) {
			matrixStackIn.translate(0.0D, 0.1D, 0.0D);
		}
		
		if (item instanceof ArmorItem) {
			ArmorItem armorItem = (ArmorItem) stack.getItem();
			ClientModel model = this.getArmorModel(entityliving, armorItem, stack);
			
			boolean hasEffect = stack.hasEffect();
			if (armorItem instanceof IDyeableArmorItem) {
				int i = ((IDyeableArmorItem) armorItem).getColor(stack);
				float r = (float) (i >> 16 & 255) / 255.0F;
				float g = (float) (i >> 8 & 255) / 255.0F;
				float b = (float) (i & 255) / 255.0F;
				this.renderArmor(matrixStackIn, buffer, packedLightIn, hasEffect, model, r, g, b,
						this.getArmorTexture(stack, entityliving, armorItem.getEquipmentSlot(), null), poses);
				this.renderArmor(matrixStackIn, buffer, packedLightIn, hasEffect, model, 1.0F, 1.0F, 1.0F,
						this.getArmorTexture(stack, entityliving, armorItem.getEquipmentSlot(), "overlay"), poses);
			} else {
				this.renderArmor(matrixStackIn, buffer, packedLightIn, hasEffect, model, 1.0F, 1.0F, 1.0F,
						this.getArmorTexture(stack, entityliving, armorItem.getEquipmentSlot(), null), poses);
			}
		} else {
			if (item != Items.AIR) {
				ClientEngine.INSTANCE.renderEngine.getItemRenderer(stack.getItem()).renderItemOnHead(stack, entitydata,
						buffer, matrixStackIn, packedLightIn, partialTicks);
			}
		}
		
		matrixStackIn.pop();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ClientModel getArmorModel(E entityliving, ArmorItem armorItem, ItemStack stack) {
		ResourceLocation registryName = armorItem.getRegistryName();
		if (ARMOR_MODEL_MAP.containsKey(registryName)) {
			return ARMOR_MODEL_MAP.get(registryName);
		} else {
			BipedModel<E> originalModel = new BipedModel<>(0.5F);
			ClientModel model;
			LivingRenderer<E, ?> entityRenderer = (LivingRenderer<E, ?>)Minecraft.getInstance().getRenderManager().getRenderer(entityliving);
			
			for (LayerRenderer<E, ?> layer : entityRenderer.layerRenderers) {
				if (layer instanceof BipedArmorLayer) {
					originalModel = ((BipedArmorLayer) layer).func_241736_a_(this.slot);
				}
			}
			
			BipedModel<E> customModel = armorItem.getArmorModel(entityliving, stack, slot, originalModel);
			
			if (customModel == null) {
				ArmorCapability cap = (ArmorCapability) stack.getCapability(ModCapabilities.CAPABILITY_ITEM, null).orElse(null);
				
				if (cap == null) {
					model = ArmorCapability.getBipedArmorModel(armorItem.getEquipmentSlot());
				} else {
					model = cap.getArmorModel(armorItem.getEquipmentSlot());
				}
				ARMOR_MODEL_MAP.put(registryName, model);
				return model;
			} else {
				if (ARMOR_MODEL_MAP_BY_MODEL.containsKey(customModel)) {
					model = ARMOR_MODEL_MAP_BY_MODEL.get(customModel);
				} else {
					EpicFightMod.LOGGER.info("baked new model for " + registryName);
					model = CustomModelBakery.bakeBipedCustomArmorModel(customModel, armorItem);
				}
				ARMOR_MODEL_MAP.put(registryName, model);
				return model;
			}
		}
	}
	
	private ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		ArmorItem item = (ArmorItem) stack.getItem();
		String texture = item.getArmorMaterial().getName();
		String domain = "minecraft";
		int idx = texture.indexOf(':');

		if (idx != -1) {
			domain = texture.substring(0, idx);
			texture = texture.substring(idx + 1);
		}

		String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture,
				(slot == EquipmentSlotType.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
		s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
		ResourceLocation resourcelocation = BipedArmorLayer.ARMOR_TEXTURE_RES_MAP.get(s1);
		if (resourcelocation == null) {
			resourcelocation = new ResourceLocation(s1);
			BipedArmorLayer.ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
		}

		return resourcelocation;
	}
}