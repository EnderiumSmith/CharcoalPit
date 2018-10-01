package charcoalPit.tile;

import charcoalPit.blocks.BlockCeramicPot;
import charcoalPit.blocks.BlockClayPot;
import charcoalPit.blocks.BlockSmeltedPot;
import charcoalPit.core.FilteredItemHandler;
import charcoalPit.core.MethodHelper;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

public class TileCeramicPot extends TileEntity{
	
	@CapabilityInject(IItemHandler.class)
	public static Capability<IItemHandler> ITEM=null;
	public CeramicItemHandler items;
	public TileCeramicPot() {
		items=new CeramicItemHandler(9);
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
	
	public static class CeramicItemHandler extends FilteredItemHandler{
		
		public CeramicItemHandler(int size) {
			super(size);
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			if(stack.getItem() instanceof ItemBlock){
				if(((ItemBlock)stack.getItem()).getBlock() instanceof BlockCeramicPot||
						((ItemBlock)stack.getItem()).getBlock() instanceof BlockShulkerBox||
						((ItemBlock)stack.getItem()).getBlock() instanceof BlockClayPot||
						((ItemBlock)stack.getItem()).getBlock() instanceof BlockSmeltedPot){
					return false;
				}
			}
			return !MethodHelper.isItemBlackListed(stack);
		}
		
	}
}
