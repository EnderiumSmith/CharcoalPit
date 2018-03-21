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
		if(fuel.getItem()==ItemsRegistry.coke)
			return Config.CokeFuel;
		if(fuel.getItem()==Item.getItemFromBlock(BlocksRegistry.cokeBlock))
			return Config.CokeFuel*10;
		if(fuel.getItem()==ItemsRegistry.magic_Coal)
			return 6400;
		if(FluidRegistry.getFluidStack("creosote", 1000).isFluidStackIdentical(FluidStack.loadFluidStackFromNBT(fuel.getTagCompound()))){
			return Config.CreosoteFuel;
		}
		if(fuel.getItem()==ItemsRegistry.straw)
			return 50;
		if(fuel.getItem()==Item.getItemFromBlock(BlocksRegistry.thatch))
			return 200;
		if(fuel.getItem()==ItemsRegistry.aeternalis_fuel){
			return 200;
		}
		return 0;
	}

}
