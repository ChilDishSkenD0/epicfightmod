package maninhouse.epicfight.client.renderer.entity;

import maninhouse.epicfight.capabilities.entity.mob.ZombieData;
import maninhouse.epicfight.client.renderer.layer.VillagerProfessionLayer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.client.renderer.entity.model.ZombieVillagerModel;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieVillagerRenderer extends BipedRenderer<ZombieVillagerEntity, ZombieData<ZombieVillagerEntity>, ZombieVillagerModel<ZombieVillagerEntity>> {
	private static final ResourceLocation ZOMBIE_VILLAGER_TEXTURE = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");
	
	public ZombieVillagerRenderer() {
		this.layerRendererReplace.put(VillagerLevelPendantLayer.class, new VillagerProfessionLayer());
	}
	
	@Override
	protected ResourceLocation getEntityTexture(ZombieVillagerEntity entityIn) {
		return ZOMBIE_VILLAGER_TEXTURE;
	}
}