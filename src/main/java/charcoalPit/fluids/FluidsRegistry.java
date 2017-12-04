package charcoalPit.fluids;

import charcoalPit.core.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidsRegistry {
	
	public static Fluid Creosote;
	public static BlockFluidCreosote BlockCreosote;
	
	public static void registerFluids(){
		if(!FluidRegistry.isFluidRegistered("creosote")){
			Creosote=new FluidCreosote("creosote", new ResourceLocation(Constants.MODID, "blocks/creosote_still"), new ResourceLocation(Constants.MODID, "blocks/creosote_flow"));
			Creosote.setViscosity(2000);
			FluidRegistry.registerFluid(Creosote);
			FluidRegistry.addBucketForFluid(Creosote);
			BlockCreosote=new BlockFluidCreosote();
			Creosote.setBlock(BlockCreosote);
		}else{
			Creosote=FluidRegistry.getFluid("creosote");
			if(Creosote.getBlock()==null){
				BlockCreosote=new BlockFluidCreosote();
				Creosote.setBlock(BlockCreosote);
			}
		}
	}
}
