package charcoalPit.tile;


import charcoalPit.blocks.BlockPotteryKiln;
import charcoalPit.blocks.BlockPotteryKiln.EnumKilnTypes;
import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.core.Config;
import charcoalPit.crafting.PotteryKilnRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class TilePotteryKiln extends TileEntity implements ITickable{
	
	public int invalidTicks;
	public int burnTime;
	public int xp;
	public boolean isValid;
	public PotteryStackHandler pottery;
	public TilePotteryKiln() {
		invalidTicks=0;
		burnTime=-1;
		xp=0;
		isValid=false;
		pottery=new PotteryStackHandler();
	}

	@Override
	public void update() {
		if(!this.world.isRemote&&burnTime>-1){
			checkValid();
			if(burnTime>0)
				burnTime--;
			else{
				if(burnTime==0){
					ItemStack result=PotteryKilnRecipe.getResult(pottery.getStackInSlot(0));
					result.setCount(pottery.getStackInSlot(0).getCount());
					pottery.setStackInSlot(0, result);
					xp=result.getCount();
					this.world.setBlockState(pos, BlocksRegistry.potteryKiln.getDefaultState().withProperty(BlockPotteryKiln.TYPE, EnumKilnTypes.COMPLETE));
					this.world.setBlockToAir(this.pos.offset(EnumFacing.UP));
					burnTime--;
				}
			}
		}
	}
	public void setActive(boolean active){
		if(active){
			burnTime=Config.PotteryTime;
		}else{
			burnTime=-1;
		}
	}
	public void dropInventory(){
		InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), pottery.getStackInSlot(0));
		while(xp>0){
			int i=EntityXPOrb.getXPSplit(xp);
			xp-=i;
			world.spawnEntity(new EntityXPOrb(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, i));
		}
	}
	public void checkValid(){
		boolean valid=true;
		if(Config.RainyPottery&&this.world.isRainingAt(this.pos.offset(EnumFacing.UP))){
			valid=false;
		}else{
			if(isValid)
				return;
		}
		//check structure
		for(EnumFacing facing:EnumFacing.HORIZONTALS){
			IBlockState block=this.world.getBlockState(this.pos.offset(facing));
			if(!block.isSideSolid(this.world, pos.offset(facing), facing.getOpposite())){
				valid=false;
				break;
			}
		}
		IBlockState block=this.world.getBlockState(this.pos.offset(EnumFacing.UP));
		if(block.getBlock()!=Blocks.FIRE){
			if(block.getBlock().isAir(block, this.world, this.pos.offset(EnumFacing.UP))||
					block.getBlock().isReplaceable(this.world, this.pos.offset(EnumFacing.UP))){
				this.world.setBlockState(this.pos.offset(EnumFacing.UP), Blocks.FIRE.getDefaultState());
			}else{
				valid=false;
			}
		}
		if(valid){
			isValid=true;
			invalidTicks=0;
		}else{
			if(invalidTicks<100){
				invalidTicks++;
			}else{
				setActive(false);
				this.world.setBlockState(pos, BlocksRegistry.potteryKiln.getDefaultState().withProperty(BlockPotteryKiln.TYPE, EnumKilnTypes.WOOD), 2);
				this.world.setBlockState(pos.offset(EnumFacing.UP), Blocks.AIR.getDefaultState(), 2);
				invalidTicks=0;
			}
		}
	}
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		if(oldState.getBlock()==newSate.getBlock())
			return false;
		return super.shouldRefresh(world, pos, oldState, newSate);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("invalid", invalidTicks);
		compound.setInteger("time", burnTime);
		compound.setInteger("xp", xp);
		compound.setBoolean("valid", isValid);
		compound.setTag("pottery", pottery.serializeNBT());
		return compound;
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		invalidTicks=compound.getInteger("invalid");
		burnTime=compound.getInteger("time");
		xp=compound.getInteger("xp");
		isValid=compound.getBoolean("valid");
		pottery.deserializeNBT(compound.getCompoundTag("pottery"));
		System.out.println(this.world!=null);
	}
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt=super.getUpdateTag();
		nbt.setTag("pottery", pottery.serializeNBT());
		return nbt;
	}
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.readFromNBT(tag);
		pottery.deserializeNBT(tag.getCompoundTag("pottery"));
	}
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 1, pottery.serializeNBT());
	}
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		pottery.deserializeNBT(pkt.getNbtCompound());
	}
	
	public class PotteryStackHandler extends ItemStackHandler{
		@Override
		public int getSlotLimit(int slot) {
			return 8;
		}
	}
}
