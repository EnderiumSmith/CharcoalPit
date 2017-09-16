package charcoalPit.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileCreosoteCollector extends TileEntity implements ITickable{
	
	public FluidTank creosote;
	int tick;
	@CapabilityInject(IFluidHandler.class)
	public static Capability<IFluidHandler> FLUID=null;
	public TileCreosoteCollector() {
		creosote=new FluidTank(8000);
		creosote.setCanFill(false);
		tick=0;
	}

	@Override
	public void update() {
		if(tick<20){
			tick++;
		}else{
			tick=0;
			//collect creosote
			if(creosote.getFluidAmount()<creosote.getCapacity()&&this.world.getTileEntity(this.pos.offset(EnumFacing.UP))instanceof TileActivePile){
				TileActivePile up=(TileActivePile)this.world.getTileEntity(this.pos.offset(EnumFacing.UP));
				up.creosote.drain(creosote.fillInternal(up.creosote.getFluid(), true), true);
				for(EnumFacing facing:EnumFacing.HORIZONTALS){
					if(creosote.getFluidAmount()<creosote.getCapacity())
						collectCreosote(this.pos.offset(EnumFacing.UP).offset(facing), facing, 3);
				}
			}
			//chanel creosote
			if(this.world.isBlockPowered(this.pos)){
				for(EnumFacing facing:EnumFacing.HORIZONTALS){
					if(creosote.getFluidAmount()<creosote.getCapacity())
						chanelCreosote(this.pos.offset(facing), facing, 3);
				}
			}
			//output creosote
			if(creosote.getFluidAmount()>0&&this.world.isBlockPowered(this.pos)){
				TileEntity tile=this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN));
				if(tile!=null){
					if(tile.hasCapability(FLUID, EnumFacing.UP)){
						IFluidHandler down=tile.getCapability(FLUID, EnumFacing.UP);
						creosote.drain(down.fill(creosote.getFluid(), true), true);
					}
				}
			}
		}
		
	}
	public void collectCreosote(BlockPos pos, EnumFacing facing, int runs){
		if(this.world.getTileEntity(pos)instanceof TileActivePile){
			TileActivePile up=(TileActivePile)this.world.getTileEntity(pos);
			up.creosote.drain(creosote.fillInternal(up.creosote.getFluid(), true), true);
			if(runs>0&&creosote.getFluidAmount()<creosote.getCapacity())
				collectCreosote(pos.offset(facing), facing, --runs);
		}
	}
	public void chanelCreosote(BlockPos pos, EnumFacing facing, int runs){
		if(this.world.getTileEntity(pos)instanceof TileCreosoteCollector){
			TileCreosoteCollector up=(TileCreosoteCollector)this.world.getTileEntity(pos);
			up.creosote.drain(creosote.fillInternal(up.creosote.getFluid(), true), true);
			if(runs>0&&creosote.getFluidAmount()<creosote.getCapacity())
				chanelCreosote(pos.offset(facing), facing, --runs);
		}
	}
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(facing==EnumFacing.DOWN&&capability==FLUID){
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(facing==EnumFacing.DOWN&&capability==FLUID){
			return (T)creosote;
		}
		return super.getCapability(capability, facing);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("creosote", creosote.writeToNBT(new NBTTagCompound()));
		return compound;
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		creosote.readFromNBT(compound.getCompoundTag("creosote"));
	}
}
