package charcoalPit.tile;

import charcoalPit.core.FilteredItemHandler;
import charcoalPit.crafting.OreSmeltingRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

public class TileClayPot extends TileEntity{
	
	@CapabilityInject(IItemHandler.class)
	public static Capability<IItemHandler> ITEM=null;
	public ClayPotItemHandler items;
	public TileClayPot() {
		items=new ClayPotItemHandler();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", items.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		items.deserializeNBT(compound.getCompoundTag("items"));
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==ITEM){
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==ITEM){
			return (T) items;
		}
		return super.getCapability(capability, facing);
	}
	
	public static class ClayPotItemHandler extends FilteredItemHandler{
		
		public ClayPotItemHandler() {
			super(5);
		}
		
		@Override
		public int getSlotLimit(int slot) {
			if(slot<4)
				return 2;
			else
				return 8;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			if(slot<4)
				return OreSmeltingRecipes.isValidOre(stack, false);
			else
				return OreSmeltingRecipes.isValidFuel(stack);
		}
		
	}
	
}
