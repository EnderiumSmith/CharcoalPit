package charcoalPit.core;

import charcoalPit.tile.TESRPotteryKiln;
import charcoalPit.tile.TilePotteryKiln;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		ClientRegistry.bindTileEntitySpecialRenderer(TilePotteryKiln.class, new TESRPotteryKiln());
	}
	public void init(FMLInitializationEvent e){
		super.init(e);
	}
	public void postInit(FMLPostInitializationEvent e){
		super.postInit(e);
	}
}
