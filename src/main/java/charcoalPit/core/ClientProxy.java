package charcoalPit.core;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.fluids.FluidsRegistry;
import charcoalPit.items.ItemsRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		BlocksRegistry.initModel();
		ItemsRegistry.initModel();
		StateMapperBase mapper=new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(new ResourceLocation(Constants.MODID, "creosote"), "creosote");
			}
		};
		ModelLoader.setCustomStateMapper(FluidsRegistry.BlockCreosote, mapper);
	}
	public void init(FMLInitializationEvent e){
		super.init(e);
	}
	public void postInit(FMLPostInitializationEvent e){
		super.postInit(e);
	}
}
