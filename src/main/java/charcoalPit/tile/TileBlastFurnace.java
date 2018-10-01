package charcoalPit.tile;

import java.util.List;

import charcoalPit.blocks.BlockBloomeryHatch;
import charcoalPit.blocks.BlockBloomeryOreLayer;
import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.core.Config;
import charcoalPit.core.FilteredItemHandler;
import charcoalPit.core.MethodHelper;
import charcoalPit.crafting.OreSmeltingRecipes;
import charcoalPit.tile.TileBloomery.OreStackItemHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
/*
public class TileBlastFurnace extends TileEntity implements ITickable{
	
	/*public OreStackItemHandler oreStack;
	public OreStackItemHandler additiveStack;
	public int[] smeltProgress;
	public int burnTime;
	public int bellowTime;
	public int delay;
	public boolean isValid;
	public TileBlastFurnace() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		if(!world.isRemote){
			checkValid();
			if(burnTime==-1){
				delay++;
				if(delay>=10){
					delay=0;
					int l=getOreAmount()+getFuelAmount();
					if(l/8<4){
						BlockPos topPos=this.pos.offset(EnumFacing.UP, l/8);
						List<EntityItem> items=world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(topPos.getX(), topPos.getY(), topPos.getZ(), topPos.getX()+1, topPos.getY()+1, topPos.getZ()+1));
						List<EntityPlayer> players=world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(topPos.getX(), topPos.getY(), topPos.getZ(), topPos.getX()+1, topPos.getY()+1, topPos.getZ()+1));
						if(!items.isEmpty()&&players.isEmpty()&&isLayerValid(l/8)){
							int o=l%8;
							for(EntityItem item:items){
								if(item.getThrower()!=null&&item.getThrower().equals("Bloom"))
									continue;
								//TODO:
								if(OreSmeltingRecipes.isValidOre(item.getItem(), true)){
									while(o<8&&!item.getItem().isEmpty()&&getOreAmount()<32){
										addOreToStack(item.getItem().splitStack(1));
										o++;
									}
								}else if(OreSmeltingRecipes.isValidFuel(item.getItem())){
									while(o<8&&!item.getItem().isEmpty()&&getFuelAmount()<32){
										addFuelToStack(item.getItem().splitStack(1));
										o++;
									}
								}
							}
							//1 2 3 4
							
						}
					}
				}
			}
		}
		
	}
	
	public void ignite(){
		burnTime=Config.BloomeryTime;
		updateStack(true);
	}
	
	public void checkValid(){
		if(!isValid){
			int l=getOreAmount()+getFuelAmount();
			int s=getMaxSpace()*8;
			if(l>s){
				splitStacks(s);
				updateStack(false);
				burnTime=-1;
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockBloomeryHatch.ACTIVE, false));
				isValid=true;
			}else{
				isValid=true;
			}
		}
	}
	
	public void splitStacks(int max){
		while(getOreAmount()+getFuelAmount()>max){
			if(getFuelAmount()>getOreAmount()){
				splitFuelStack();
			}else{
				splitOreStack();
				for(int j=0;j<additiveStack.length;j++){
					while(getAdditiveAmount(j)>getOreAmount()){
						splitAdditiveStack(j);
					}
				}
			}
		}
	}
	
	public void splitAdditiveStack(int s){
		for(int i=31;i>=0;i--){
			if(!additiveStack[s].getStackInSlot(i).isEmpty()){
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), additiveStack[s].getStackInSlot(i));
				additiveStack[s].setStackInSlot(i, ItemStack.EMPTY);
				return;
			}
		}
	}
	
	public void splitOreStack(){
		for(int i=31;i>=0;i--){
			if(!ironStack.getStackInSlot(i).isEmpty()){
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), ironStack.getStackInSlot(i));
				ironStack.setStackInSlot(i, ItemStack.EMPTY);
				return;
			}
		}
	}
	
	public void splitFuelStack(){
		for(int i=31;i>=0;i--){
			if(!fuelStack.getStackInSlot(i).isEmpty()){
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), fuelStack.getStackInSlot(i));
				fuelStack.setStackInSlot(i, ItemStack.EMPTY);
				return;
			}
		}
	}
	
	public void dropInventory(EnumFacing facing){
		deleteStack(facing);
		for(int i=0;i<32;i++){
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY()-1, pos.getZ(), ironStack.getStackInSlot(i));
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY()-1, pos.getZ(), fuelStack.getStackInSlot(i));
			for(int j=0;j<additiveStack.length;j++){
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY()-1, pos.getZ(), additiveStack[j].getStackInSlot(i));
			}
		}
	}
	
	public void deleteStack(EnumFacing facing){
		int l=getOreAmount()+getFuelAmount();
		BlockPos chimneyPos=pos.offset(facing.getOpposite());
		for(int i=0;i<l/8+(l%8>0?1:0);i++){
			world.setBlockToAir(chimneyPos.offset(EnumFacing.UP, i));
		}
	}
	
	public void updateStack(boolean active){
		int l=getOreAmount()+getFuelAmount();
		BlockPos chimneyPos=this.pos;
		for(int i=0;i<l/8;i++){
			world.setBlockState(chimneyPos.offset(EnumFacing.UP, i), BlocksRegistry.oreLayer.getDefaultState().withProperty(BlockBloomeryOreLayer.LAYER, 8).withProperty(BlockBloomeryOreLayer.ACTIVE, active));
		}
		if(l%16>0){
			world.setBlockState(chimneyPos.offset(EnumFacing.UP, l/16), BlocksRegistry.oreLayer.getDefaultState().withProperty(BlockBloomeryOreLayer.LAYER, Math.max(1, (l%8)/2)).withProperty(BlockBloomeryOreLayer.ACTIVE, active));
		}
		for(int i=l/8+(l%8>0?1:0);i<4;i++){
			if(world.getBlockState(chimneyPos.offset(EnumFacing.UP, i)).getBlock()==BlocksRegistry.oreLayer)
				world.setBlockToAir(chimneyPos.offset(EnumFacing.UP, i));
		}
	}
	
	public void addAdditiveToStack(ItemStack stack, int s){
		for(int i=0;i<=32;i++){
			if(additiveStack[s].getStackInSlot(i).isEmpty()){
				additiveStack[s].setStackInSlot(i, stack);
				return;
			}
		}
	}
	
	public void addOreToStack(ItemStack stack){
		for(int i=0;i<32;i++){
			if(ironStack.getStackInSlot(i).isEmpty()){
				ironStack.setStackInSlot(i, stack);
				return;
			}
		}
	}
	
	public void addFuelToStack(ItemStack stack){
		for(int i=0;i<32;i++){
			if(fuelStack.getStackInSlot(i).isEmpty()){
				fuelStack.setStackInSlot(i, stack);
				return;
			}
		}
	}
	
	public int getAdditiveAmount(int s){
		int a=0;
		for(int i=0;i<32;i++){
			if(!additiveStack[s].getStackInSlot(i).isEmpty())
				a++;
		}
		return a;
	}
	
	public int getOreAmount(){
		int a=0;
		for(int i=0;i<32;i++){
			if(!ironStack.getStackInSlot(i).isEmpty())
				a++;
		}
		return a;
	}
	
	public int getFuelAmount(){
		int f=0;
		for(int i=0;i<32;i++){
			if(!fuelStack.getStackInSlot(i).isEmpty())
				f++;
		}
		return f;
	}
	
	public int getFluxNeeded(){
		int f=0;
		for(int i=0;i<32;i++){
			int[] ids=OreDictionary.getOreIDs(ironStack.getStackInSlot(i));
			for(int id:ids){
				if(OreDictionary.getOreName(id).startsWith("ore"))
					f++;
			}
		}
		return f;
	}
	
	public int getMaxSpace(){
		int l=0;
		if(isLayerValid(0))
			l++;
		else
			return l;
		if(isLayerValid(1))
			l++;
		else
			return l;
		if(isLayerValid(2))
			l++;
		else
			return l;
		if(isLayerValid(3))
			l++;
		return l;
	}
	
	public boolean isLayerValid(int layer){
		if(layer>=0&&layer<4){
			BlockPos chimneyPos=this.pos.offset(EnumFacing.UP, layer);
			if(!isValidCore(world.getBlockState(chimneyPos), chimneyPos))
				return false;
			for(EnumFacing facing:EnumFacing.HORIZONTALS){
				if(!MethodHelper.BloomeryIsValidBlock(world, chimneyPos, facing))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean isValidCore(IBlockState state, BlockPos pos){
		return state.getBlock().isAir(state, world, pos)||state.getBlock()==BlocksRegistry.oreLayer;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return !(oldState.getBlock()==newSate.getBlock());
	}
	
	public static class IronStackItemHandler extends FilteredItemHandler{
		
		public IronStackItemHandler(int size) {
			super(size);
		}
		
		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return true;
		}
		
	}
	
	public static class BlastFuelItemHandler extends FilteredItemHandler{
		
		public BlastFuelItemHandler(int size) {
			super(size);
		}
		
		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return true;
		}
		
	}
	
}
*/