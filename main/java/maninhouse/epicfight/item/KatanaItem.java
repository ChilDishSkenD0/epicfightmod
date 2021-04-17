package maninhouse.epicfight.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import maninhouse.epicfight.capabilities.item.KatanaCapability;
import maninhouse.epicfight.main.EpicFightMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KatanaItem extends WeaponItem {
	@OnlyIn(Dist.CLIENT)
	private List<ITextComponent> tooltipExpand;

	public KatanaItem(Item.Properties build) {
		super(ModItemTier.KATANA, 0, -2.0F, build);
		if (EpicFightMod.isPhysicalClient()) {
			tooltipExpand = new ArrayList<ITextComponent> ();
			tooltipExpand.add(new StringTextComponent(""));
			tooltipExpand.add(new StringTextComponent("If you don't act for 5 second, it will go to the sheathing state. In sheathing state, you can reinforce the next attack"));
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return toRepair.getItem() == Items.IRON_BARS;
	}

	@Override
	public boolean canHarvestBlock(BlockState blockIn) {
        return false;
    }
    
    @Override
	public void setWeaponCapability(IItemTier tier) {
		capability = new KatanaCapability();
	}
	
	@OnlyIn(Dist.CLIENT)@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		for (ITextComponent txtComp : tooltipExpand) {
			tooltip.add(txtComp);
		}
	}
}