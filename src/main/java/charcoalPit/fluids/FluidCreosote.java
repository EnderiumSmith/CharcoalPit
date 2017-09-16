package charcoalPit.fluids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidCreosote extends Fluid{

	public FluidCreosote(String fluidName, ResourceLocation still, ResourceLocation flowing) {
		super(fluidName, still, flowing);
	}
	@Override
	public void vaporize(EntityPlayer player, World worldIn, BlockPos pos, FluidStack fluidStack) {
		
		worldIn.newExplosion(player, pos.getX(), pos.getY(), pos.getZ(), 1, true, true);
	}

}
