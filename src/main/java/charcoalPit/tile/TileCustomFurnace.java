package charcoalPit.tile;

import charcoalPit.blocks.BlockCustomFurnace;
import charcoalPit.core.Config;
import charcoalPit.crafting.PotteryKilnRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileCustomFurnace extends TileEntity implements ITickable{

	public int burnTime, cookTime, totalBurnTime;
	public static final int totalCookTime=200;
	public float xp;
	public ItemStackHandler top, fuel, result;
	@CapabilityInject(IItemHandler.class)
	public static Capability<IItemHandler> ITEM=null;
	public TileCustomFurnace() {
		burnTime=0;
		cookTime=0;
		totalBurnTime=0;
		xp=0;
		top=new ItemStackHandler();
		fuel=new ItemStackHandler();
		result=new ItemStackHandler();
	}
	
	public void dropInventory(){
		InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), top.getStackInSlot(0));
		InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), fuel.getStackInSlot(0));
		InventoryHelper.spawnItemStack(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), result.getStackInSlot(0));
		int exp=(int)xp;
		while(exp>0){
			int x=EntityXPOrb.getXPSplit(exp);
			exp-=x;
			world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), x));
		}
	}
	
	public static boolean canSmelt(ItemStack stack){
		if(Config.DisableFurnaceCharcoal){
			int[] ids=OreDictionary.getOreIDs(stack);
			for(int id:ids){
				if(OreDictionary.getOreName(id).equals("logWood"))
					return false;
			}
		}
		if(Config.DisableFurnaceOre){
			int[] ids=OreDictionary.getOreIDs(stack);
			for(int id:ids){
				if(OreDictionary.getOreName(id).startsWith("ore"))
					return false;
			}
		}
		if(Config.DisableVanillaPottery){
			if(PotteryKilnRecipe.isValidInput(stack))
				return false;
		}
		return !FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty();
	}
	
	public boolean canSmelt(){
		if(top.getStackInSlot(0).isEmpty())
			return false;
		ItemStack stack=FurnaceRecipes.instance().getSmeltingResult(top.getStackInSlot(0));
		if(!canSmelt(top.getStackInSlot(0)))
			return false;
		if(stack.isEmpty())
			return false;
		else{
			ItemStack stack2=result.getStackInSlot(0);
			if(stack2.isEmpty())
				return true;
			else if(!stack2.isItemEqual(stack)){
				return false;
			}else if(stack2.getCount()+stack.getCount()<=result.getSlotLimit(0)&&stack2.getCount()+stack.getCount()<=stack2.getMaxStackSize()){
				return true;
			}else{
				return stack2.getCount()+stack.getCount()<=stack2.getMaxStackSize();
			}
		}
	}
	
	@Override
	public void update() {
		boolean flag0=burnTime>0;
		boolean flag1=false;
		if(burnTime>0)
			burnTime--;
		if(!world.isRemote){
			ItemStack stack=fuel.getStackInSlot(0);
			if(burnTime>0||!stack.isEmpty()&&!top.getStackInSlot(0).isEmpty()){
				if(!(burnTime>0)&&canSmelt()){
					burnTime=TileEntityFurnace.getItemBurnTime(stack);
					totalBurnTime=burnTime;
					if(burnTime>0){
						flag1=true;
						if(!stack.isEmpty()){
							Item item=stack.getItem();
							stack.shrink(1);
							if(stack.isEmpty()){
								ItemStack cont=item.getContainerItem(stack);
								fuel.insertItem(0, cont, false);
							}
						}
					}
				}
				if(burnTime>0&&canSmelt()){
					cookTime++;
					if(cookTime==totalCookTime){
						cookTime=0;
						smelt();
						flag1=true;
					}
				}else
					cookTime=0;
			}else if(!(burnTime>0)&&cookTime>0){
				cookTime=MathHelper.clamp(cookTime-2, 0, totalCookTime);
			}
			if(flag0!=(burnTime>0)){
				flag1=true;
				world.setBlockState(this.pos, world.getBlockState(this.pos).withProperty(BlockCustomFurnace.ACTIVE, burnTime>0));
			}
		}
		if(flag1)
			this.markDirty();
	}
	
	public void smelt(){
		if(canSmelt()){
			ItemStack stack=top.getStackInSlot(0);
			ItemStack stack1=FurnaceRecipes.instance().getSmeltingResult(stack).copy();
			ItemStack stack2=result.getStackInSlot(0);
			if(stack2.isEmpty()){
				result.insertItem(0, stack1, false);
			}else if(stack2.isItemEqual(stack2)){
				stack2.grow(stack1.getCount());
			}
			if(stack.getItem()==Item.getItemFromBlock(Blocks.SPONGE)&&stack.getMetadata()==1&&!fuel.getStackInSlot(0).isEmpty()&&fuel.getStackInSlot(0).getItem()==Items.BUCKET){
				fuel.setStackInSlot(0, new ItemStack(Items.WATER_BUCKET));
			}
			stack.shrink(1);
			xp+=FurnaceRecipes.instance().getSmeltingExperience(stack1);
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		if(oldState.getBlock()==newSate.getBlock())
			return false;
		return super.shouldRefresh(world, pos, oldState, newSate);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		burnTime=nbt.getInteger("burnTime");
		cookTime=nbt.getInteger("cookTime");
		totalBurnTime=nbt.getInteger("totalBurnTime");
		xp=nbt.getFloat("xp");
		top.deserializeNBT(nbt.getCompoundTag("top"));
		fuel.deserializeNBT(nbt.getCompoundTag("fuel"));
		result.deserializeNBT(nbt.getCompoundTag("result"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("cookTime", cookTime);
		nbt.setInteger("totalBurnTime", totalBurnTime);
		nbt.setFloat("xp", xp);
		nbt.setTag("top", top.serializeNBT());
		nbt.setTag("fuel", fuel.serializeNBT());
		nbt.setTag("result", result.serializeNBT());
		return nbt;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability==ITEM)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability==ITEM){
			if(facing==null){
				return (T) new IItemHandler() {
					
					@Override
					public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
						if(slot==0){
							return top.insertItem(slot, stack, simulate);
						}else if(slot==1){
							return fuel.insertItem(slot-1, stack, simulate);
						}else{
							return stack;
						}
					}
					
					@Override
					public ItemStack getStackInSlot(int slot) {
						if(slot==0){
							return top.getStackInSlot(slot);
						}else if(slot==1){
							return fuel.getStackInSlot(slot-1);
						}else{
							return result.getStackInSlot(slot-2);
						}
					}
					
					@Override
					public int getSlots() {
						return 3;
					}
					
					@Override
					public int getSlotLimit(int slot) {
						if(slot==0){
							return top.getSlotLimit(slot);
						}else if(slot==1){
							return fuel.getSlotLimit(slot-1);
						}else{
							return result.getSlotLimit(slot-2);
						}
					}
					
					@Override
					public ItemStack extractItem(int slot, int amount, boolean simulate) {
						if(slot<2)
							return ItemStack.EMPTY;
						else
							return result.extractItem(slot-2, amount, simulate);
					}
				};
			}
			switch(facing){
			case UP:return (T) new IItemHandler() {
				
				@Override
				public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
					if(slot==0){
						if(canSmelt(stack))
							return top.insertItem(slot, stack, simulate);
						else
							return stack;
					}
					return stack;
				}
				
				@Override
				public ItemStack getStackInSlot(int slot) {
					if(slot>0)
						return result.getStackInSlot(slot-1);
					return top.getStackInSlot(slot);
				}
				
				@Override
				public int getSlots() {
					return 2;
				}
				
				@Override
				public int getSlotLimit(int slot) {
					if(slot>0)
						return result.getSlotLimit(slot-1);
					return top.getSlotLimit(slot);
				}
				
				@Override
				public ItemStack extractItem(int slot, int amount, boolean simulate) {
					if(slot>0)
						return result.extractItem(slot-1, amount, simulate);
					return ItemStack.EMPTY;
				}
			};
			default:return (T) new IItemHandler() {
				
				@Override
				public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
					if(slot==0){
						if(TileEntityFurnace.getItemBurnTime(stack)>0)
							return fuel.insertItem(slot, stack, simulate);
						else
							return stack;
					}
					return stack;
				}
				
				@Override
				public ItemStack getStackInSlot(int slot) {
					if(slot>0)
						return result.getStackInSlot(slot-1);
					return fuel.getStackInSlot(slot);
				}
				
				@Override
				public int getSlots() {
					return 2;
				}
				
				@Override
				public int getSlotLimit(int slot) {
					if(slot>0)
						return result.getSlotLimit(slot-1);
					return fuel.getSlotLimit(slot);
				}
				
				@Override
				public ItemStack extractItem(int slot, int amount, boolean simulate) {
					if(slot>0)
						return result.extractItem(slot-1, amount, simulate);
					return ItemStack.EMPTY;
				}
			};
			}
		}
		return super.getCapability(capability, facing);
	}

}
