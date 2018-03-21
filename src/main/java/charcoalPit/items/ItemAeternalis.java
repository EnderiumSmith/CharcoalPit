package charcoalPit.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAeternalis extends ItemBase{

	public ItemAeternalis() {
		super("aeternalis_fuel");
		setContainerItem(this);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(ChatFormatting.LIGHT_PURPLE+"An ancient relic that poseses the power to burn eternal");
	}

}
