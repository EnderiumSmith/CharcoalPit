package charcoalPit.tile;

import java.util.Random;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.core.Config;
import charcoalPit.items.ItemsRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class TileBloom extends TileEntity{
	
	public ItemStackHandler items;
	public int slag;
	public TileBloom() {
		items=new ItemStackHandler();
		slag=0;
	}
	
	public void dropInventory(){
		BlockPos hatch=gethatchPos();
		spawnItemStack(items.getStackInSlot(0), hatch);
		if(slag>0){
			for(int i=0;i<slag;i++){
				Random r=new Random();
				if(r.nextFloat()<Config.BloomSlagChance){
					InventoryHelper.spawnItemStack(world, hatch.getX(), hatch.getY(), hatch.getZ(), ItemsRegistry.rich_slag_stack.copy());
				}else{
					InventoryHelper.spawnItemStack(world, hatch.getX(), hatch.getY(), hatch.getZ(), ItemsRegistry.slag_stack.copy());
				}
			}
			while(slag>0){
				int i=EntityXPOrb.getXPSplit(slag);
				slag-=i;
				world.spawnEntity(new EntityXPOrb(world, (double)hatch.getX() + 0.5D, (double)hatch.getY() + 0.5D, (double)hatch.getZ() + 0.5D, i));
			}
		}
	}
	
	public void spawnItemStack(ItemStack stack, BlockPos pos){
		Random r=new Random();
		float f = r.nextFloat() * 0.8F + 0.1F;
        float f1 = r.nextFloat() * 0.8F + 0.1F;
        float f2 = r.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty())
        {
            EntityItem entityitem = new EntityItem(world, pos.getX() + (double)f, pos.getY() + (double)f1, pos.getZ() + (double)f2, stack.splitStack(r.nextInt(21) + 10));
            entityitem.motionX = r.nextGaussian() * 0.05000000074505806D;
            entityitem.motionY = r.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
            entityitem.motionZ = r.nextGaussian() * 0.05000000074505806D;
            entityitem.setThrower("Bloom");
            world.spawnEntity(entityitem);
        }
	}
	
	public BlockPos gethatchPos(){
		for(EnumFacing facing:EnumFacing.HORIZONTALS){
			if(world.getBlockState(pos.offset(facing)).getBlock()==BlocksRegistry.hatch)
				return pos.offset(facing);
		}
		return pos;
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
