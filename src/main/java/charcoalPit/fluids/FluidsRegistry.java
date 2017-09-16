package charcoalPit.fluids;

import charcoalPit.core.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class FluidsRegistry {
	
	public static FluidCreosote Creosote=new FluidCreosote("creosote", new ResourceLocation(Constants.MODID, "blocks/creosote_still"), new ResourceLocation(Constants.MODID, "blocks/creosote_flow"));
	public static BlockFluidCreosote BlockCreosote;
	
	public static void registerFluids(){
		Creosote.setViscosity(2000);
		FluidRegistry.registerFluid(Creosote);
		FluidRegistry.addBucketForFluid(Creosote);
		BlockCreosote=new BlockFluidCreosote();
		Creosote.setBlock(BlockCreosote);
		ForgeRegistries.BLOCKS.register(BlockCreosote);
	}
}
