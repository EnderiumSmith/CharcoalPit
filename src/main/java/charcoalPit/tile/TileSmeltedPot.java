package charcoalPit.tile;

import java.util.Random;

import charcoalPit.core.Config;
import charcoalPit.items.ItemsRegistry;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TileSmeltedPot extends TileEntity{
	
	public ItemStackHandler items;
	public int slag;
	public TileSmeltedPot() {
		items=new ItemStackHandler();
		slag=0;
	}
	
	public void dropInventory(){
		InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), items.getStackInSlot(0));
		if(slag>0){
			for(int i=0;i<slag;i++){
				Random r=new Random();
				if(r.nextFloat()<Config.KilnSlagChance){
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), ItemsRegistry.rich_slag_stack.copy());
				}else{
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), ItemsRegistry.slag_stack.copy());
				}
			}
			while(slag>0){
				int i=EntityXPOrb.getXPSplit(slag);
				slag-=i;
				world.spawnEntity(new EntityXPOrb(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, i));
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", items.serializeNBT());
		compound.setInteger("slag", slag);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		items.deserializeNBT(compound.getCompoundTag("items"));
		slag=compound.getInteger("slag");
	}
	
}
