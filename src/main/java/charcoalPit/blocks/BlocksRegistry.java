package charcoalPit.blocks;

import charcoalPit.core.Constants;
import charcoalPit.fluids.FluidsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlocksRegistry {
	
	public static BlockLogPile logPile=new BlockLogPile();
	public static BlockCoke cokeBlock=new BlockCoke();
	public static BlockActivePile activeLogPile=new BlockActivePile(Material.WOOD, "active_log_pile", false);
	public static BlockActivePile activeCoalPile=new BlockActivePile(Material.ROCK, "active_coal_pile", true);
	public static BlockAshPile charcoalPile=new BlockAshPile("charcoal_pile", false);
	public static BlockAshPile cokePile=new BlockAshPile("coke_pile", true);
	public static BlockCreosoteCollector stoneCollector=new BlockCreosoteCollector("stone_creosote_collector", false);
	public static BlockCreosoteCollector brickCollector=new BlockCreosoteCollector("brick_creosote_collector", true);
	public static BlockCreosoteCollector netherCollector=new BlockCreosoteCollector("nether_creosote_collector", true);
	public static BlockPotteryKiln potteryKiln=new BlockPotteryKiln("pottery_kiln");
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(logPile, cokeBlock, activeLogPile, activeCoalPile,
				charcoalPile, cokePile, stoneCollector, brickCollector, netherCollector, potteryKiln);
		if(FluidsRegistry.BlockCreosote!=null){
			event.getRegistry().register(FluidsRegistry.BlockCreosote);
		}
	}
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event){
		StateMapperBase mapper=new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(new ResourceLocation(Constants.MODID, "creosote"), "creosote");
			}
		};
		if(FluidsRegistry.BlockCreosote!=null)
			ModelLoader.setCustomStateMapper(FluidsRegistry.BlockCreosote, mapper);
	}
}
