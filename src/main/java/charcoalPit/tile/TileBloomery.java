package charcoalPit.tile;

import java.util.List;

import charcoalPit.blocks.BlockBloomeryHatch;
import charcoalPit.blocks.BlockBloomeryOreLayer;
import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.core.FilteredItemHandler;
import charcoalPit.core.MethodHelper;
import charcoalPit.crafting.OreSmeltingRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileBloomery extends TileEntity implements ITickable{
	
	public OreStackItemHandler oreStack;
	public FuelStackItemHandler fuelStack;
	public int burnTime;
	public boolean isValid;
	public TileBloomery() {
		oreStack=new OreStackItemHandler(32);
		fuelStack=new FuelStackItemHandler(32);
		burnTime=-1;
		isValid=false;
	}
	
	@Override
	public void update() {
		if(!world.isRemote){
			checkValid();
			if(burnTime==-1){
				//check for input ores
				if(world.getWorldTime()%10==0){
					int l=getOreAmount()+getFuelAmount();
					l/=16;
					if(l<4){
						BlockPos chimneyPos=pos.offset(world.getBlockState(pos).getValue(BlockBloomeryHatch.FACING).getOpposite());
						BlockPos topPos=chimneyPos.offset(EnumFacing.UP,l);
						List<EntityItem> items=world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(topPos.getX(), topPos.getY(), topPos.getZ(), topPos.getX()+1, topPos.getY()+1, topPos.getZ()+1));
						//dont bury etho
						List<EntityPlayer> players=world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(topPos.getX(), topPos.getY(), topPos.getZ(), topPos.getX()+1, topPos.getY()+1, topPos.getZ()+1));
						if(!items.isEmpty()&&players.isEmpty()&&isLayerValid(l)){
							int o=getOreAmount()%8;
							for(EntityItem item:items){
								//dont pick up the output
								if(item.getThrower()!=null&&item.getThrower().equals("Bloom"))
									continue;
								if(OreSmeltingRecipes.isValidOre(item.getItem(), true)){
									while(o<8&&!item.getItem().isEmpty()){
										addOreToStack(item.getItem().splitStack(1));
										o++;
									}
								}else if(OreSmeltingRecipes.isValidFuel(item.getItem())){
									while(getFuelAmount()<getOreAmount()&&!item.getItem().isEmpty()){
										addFuelToStack(item.getItem().splitStack(1));
									}
								}
							}
						}
						updateStack(false);
					}
				}
			}else{
				//smelt ores
				if(burnTime>0)
					burnTime--;
				else{
					//done
					burnTime=-1;
					world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockBloomeryHatch.ACTIVE, false));
					deleteStack(world.getBlockState(pos).getValue(BlockBloomeryHatch.FACING));
					//spawn bloom
					BlockPos chimneyPos=pos.offset(world.getBlockState(pos).getValue(BlockBloomeryHatch.FACING).getOpposite());
					world.setBlockState(chimneyPos, BlocksRegistry.bloom.getDefaultState());
					//add result
					TileBloom bloom=((TileBloom)world.getTileEntity(chimneyPos));
					bloom.items.setStackInSlot(0, OreSmeltingRecipes.BloomeryGetOutput(this));
					bloom.slag=OreSmeltingRecipes.BloomeryGetSlagAmount(this);
					//empty stacks
					oreStack=new OreStackItemHandler(32);
					fuelStack=new FuelStackItemHandler(32);
				}
			}
		}
		
	}
	
	public void ignite(){
		burnTime=15000;
		updateStack(true);
	}
	
	public void checkValid(){
		if(!isValid){
			System.out.println("validating");
			int l=getOreAmount()+getFuelAmount();
			int s=getMaxSpace()*16;
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
			}
		}
	}
	
	public void splitOreStack(){
		for(int i=31;i>=0;i--){
			if(!oreStack.getStackInSlot(i).isEmpty()){
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), oreStack.getStackInSlot(i));
				oreStack.setStackInSlot(i, ItemStack.EMPTY);
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
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), oreStack.getStackInSlot(i));
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), fuelStack.getStackInSlot(i));
		}
	}
	
	public void deleteStack(EnumFacing facing){
		int l=getOreAmount()+getFuelAmount();
		BlockPos chimneyPos=pos.offset(facing.getOpposite());
		for(int i=0;i<l/16+(l%16>0?1:0);i++){
			world.setBlockToAir(chimneyPos.offset(EnumFacing.UP, i));
		}
	}
	
	public void updateStack(boolean active){
		int l=getOreAmount()+getFuelAmount();
		EnumFacing hatch=world.getBlockState(pos).getValue(BlockBloomeryHatch.FACING);
		BlockPos chimneyPos=pos.offset(hatch.getOpposite());
		for(int i=0;i<l/16;i++){
			world.setBlockState(chimneyPos.offset(EnumFacing.UP, i), BlocksRegistry.oreLayer.getDefaultState().withProperty(BlockBloomeryOreLayer.LAYER, 8).withProperty(BlockBloomeryOreLayer.ACTIVE, active));
		}
		if(l%16>0){
			world.setBlockState(chimneyPos.offset(EnumFacing.UP, l/16), BlocksRegistry.oreLayer.getDefaultState().withProperty(BlockBloomeryOreLayer.LAYER, Math.max(1, (l%16)/2)).withProperty(BlockBloomeryOreLayer.ACTIVE, active));
		}
		for(int i=l/16+(l%16>0?1:0);i<4;i++){
			if(world.getBlockState(chimneyPos.offset(EnumFacing.UP, i)).getBlock()==BlocksRegistry.oreLayer)
				world.setBlockToAir(chimneyPos.offset(EnumFacing.UP, i));
		}
	}
	
	public void addOreToStack(ItemStack stack){
		for(int i=0;i<32;i++){
			if(oreStack.getStackInSlot(i).isEmpty()){
				oreStack.setStackInSlot(i, stack);
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
	
	public int getOreAmount(){
		int a=0;
		for(int i=0;i<32;i++){
			if(!oreStack.getStackInSlot(i).isEmpty())
				a++;
		}
		return a;
	}
	
	public int getFuelAmount(){
		int f=0;
		for(int i=0;i<32;i++){
			f+=OreSmeltingRecipes.getFuelValue(fuelStack.getStackInSlot(i));
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
		EnumFacing hatch=world.getBlockState(pos).getValue(BlockBloomeryHatch.FACING);
		BlockPos chimneyPos=pos.offset(hatch.getOpposite());
		if(layer==0){
			if(!isValidCore(world.getBlockState(chimneyPos), chimneyPos))
				return false;
			for(EnumFacing facing:new EnumFacing[]{hatch.getOpposite(),hatch.rotateY(),hatch.rotateYCCW(),EnumFacing.DOWN}){
				if(!MethodHelper.BloomeryIsValidBlock(world, chimneyPos, facing))
					return false;
			}
			return isLayerValid(1);
		}
		if(layer>0&&layer<4){
			if(!isValidCore(world.getBlockState(chimneyPos.offset(EnumFacing.UP, layer)), chimneyPos.offset(EnumFacing.UP, layer)))
				return false;
			for(EnumFacing facing:EnumFacing.HORIZONTALS){
				if(!MethodHelper.BloomeryIsValidBlock(world, chimneyPos.offset(EnumFacing.UP, layer), facing))
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
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("ores", oreStack.serializeNBT());
		compound.setTag("fuel", fuelStack.serializeNBT());
		compound.setInteger("burnTime", burnTime);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		oreStack.deserializeNBT(compound.getCompoundTag("ores"));
		fuelStack.deserializeNBT(compound.getCompoundTag("fuel"));
		burnTime=compound.getInteger("burnTime");
	}
	
	public static class OreStackItemHandler extends FilteredItemHandler{
		
		public OreStackItemHandler(int size) {
			super(size);
		}
		
		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return OreSmeltingRecipes.isValidOre(stack, true);
		}
		
	}
	public static class FuelStackItemHandler extends FilteredItemHandler{
		
		public FuelStackItemHandler(int size) {
			super(size);
		}
		
		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return OreSmeltingRecipes.isValidFuel(stack);
		}
		
	}
	
}
