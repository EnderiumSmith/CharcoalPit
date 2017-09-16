package charcoalPit;

import charcoalPit.core.CommonProxy;
import charcoalPit.core.Constants;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Constants.MODID, name=Constants.MODNAME, version=Constants.MODVERSION)
public class CharcoalPit {
	
	static{
		FluidRegistry.enableUniversalBucket();
	}
	
	@Mod.Instance
	public static CharcoalPit instance;
	
	@SidedProxy(clientSide="charcoalPit.core.ClientProxy",
			serverSide="charcoalPit.core.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		proxy.preInit(e);
	}
	@EventHandler
	public void init(FMLInitializationEvent e){
		proxy.init(e);
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		proxy.postInit(e);
	}
}
