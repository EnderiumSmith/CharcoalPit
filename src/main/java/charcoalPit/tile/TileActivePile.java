package charcoalPit.tile;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.core.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;

public class TileActivePile extends TileEntity implements ITickable{

	//18H=18000ticks
	public int invalidTicks;
	public int burnTime;
	public int itemsLeft;
	public boolean isValid;
	public boolean isCoke;
	public FluidTank creosote;
	public TileActivePile() {
		this(false);
	}
	public TileActivePile(boolean coal) {
		invalidTicks=0;
		burnTime=isCoke?Config.CokeTime/10:Config.CharcoalTime/10;
		itemsLeft=9;
		isValid=false;
		isCoke=coal;
		creosote=new FluidTank(1000);
	}
	@Override
	public void update() {
		if(!this.world.isRemote){
			checkValid();
			if(burnTime>0){
				burnTime--;
			}else{
				if(itemsLeft>0){
					itemsLeft--;
					creosote.fill(FluidRegistry.getFluidStack("creosote", isCoke?Config.CokeCreosote:Config.CharcoalCreosote), true);
					burnTime=isCoke?Config.CokeTime/10:Config.CharcoalTime/10;
				}else{
					this.world.setBlockState(this.pos, isCoke?BlocksRegistry.cokePile.getDefaultState():BlocksRegistry.charcoalPile.getDefaultState());
				}
			}
			if(creosote.getFluidAmount()>0){
				if(this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN))instanceof TileActivePile){
					TileActivePile down=(TileActivePile)this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN));
					creosote.drain(down.creosote.fill(creosote.getFluid(), true), true);
				}
			}
		}
	}
	public void checkValid(){
		if(!isValid){
			boolean valid=true;
			EnumFacing[] neighbors=EnumFacing.VALUES;
			//check structure
			for(EnumFacing facing:neighbors){
				IBlockState block=this.world.getBlockState(this.pos.offset(facing));
				if((!isValidBlock(block))||
						(!block.isSideSolid(this.world, this.pos.offset(facing), facing.getOpposite()))||
						block.getBlock().isFlammable(this.world, this.pos.offset(facing), facing.getOpposite())){
					valid=false;
					break;
				}
			}
			if(valid){
				isValid=true;
				invalidTicks=0;
			}else{
				if(invalidTicks<100){
					invalidTicks++;
					for(EnumFacing facing:neighbors){
						//set fire
						IBlockState block=this.world.getBlockState(this.pos.offset(facing));
						if(block.getBlock().isAir(block, this.world, this.pos.offset(facing))||
								block.getBlock().isReplaceable(this.world, this.pos.offset(facing))){
							this.world.setBlockState(this.pos.offset(facing), Blocks.FIRE.getDefaultState());
						}
					}
				}else{
					this.world.setBlockToAir(this.pos);
				}
			}
		}
	}
	public boolean isValidBlock(IBlockState block){
		if(isCoke){
			if(block.getBlock()==BlocksRegistry.activeCoalPile||block.getBlock()==BlocksRegistry.brickCollector||
					block.getBlock()==BlocksRegistry.netherCollector||block.getBlock()==BlocksRegistry.cokePile){
				return true;
			}else{
				for(String name:Config.CokeBlocks){
					if(block.getBlock().getRegistryName().toString().equals(name)){
						return true;
					}
				}
				return false;
			}
		}else
			return true;
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("invalid", invalidTicks);
		compound.setInteger("time", burnTime);
		compound.setInteger("items", itemsLeft);
		compound.setBoolean("valid", isValid);
		compound.setBoolean("coke", isCoke);
		compound.setTag("creosote", creosote.writeToNBT(new NBTTagCompound()));
		return compound;
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		invalidTicks=compound.getInteger("invalid");
		burnTime=compound.getInteger("time");
		itemsLeft=compound.getInteger("items");
		isValid=compound.getBoolean("valid");
		isCoke=compound.getBoolean("coke");
		creosote.readFromNBT(compound.getCompoundTag("creosote"));
	}

}
