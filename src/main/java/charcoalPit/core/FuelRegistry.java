package charcoalPit.core;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.items.ItemsRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelRegistry implements IFuelHandler{

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.getItem()==ItemsRegistry.Coke)
			return Config.CokeFuel;
		if(fuel.getItem()==Item.getItemFromBlock(BlocksRegistry.CokeBlock))
			return Config.CokeFuel*10;
		if(FluidRegistry.getFluidStack("creosote", 1000).isFluidStackIdentical(FluidStack.loadFluidStackFromNBT(fuel.getTagCompound()))){
			return Config.CreosoteFuel;
		}
		return 0;
	}

}
