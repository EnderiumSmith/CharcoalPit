package charcoalPit.core;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.fluids.FluidsRegistry;
import charcoalPit.items.ItemsRegistry;
import charcoalPit.tile.TileActivePile;
import charcoalPit.tile.TileCreosoteCollector;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {
	
	public static Configuration config;
	public static ItemStack charcoal=new ItemStack(Items.COAL, 1, 1);
	public void preInit(FMLPreInitializationEvent e){
		File file=e.getModConfigurationDirectory();
		config=new Configuration(new File(file.getPath(),"charcoal_pit.cfg"));
		Config.readcfg();
		BlocksRegistry.registerBlocks();
		ItemsRegistry.registerItems();
		ItemsRegistry.initOreDict();
		if(Config.RegisterCreosote)
			FluidsRegistry.registerFluids();
	}
	public void init(FMLInitializationEvent e){
		GameRegistry.registerTileEntity(TileActivePile.class, Constants.MODID+"active_pile");
		GameRegistry.registerTileEntity(TileCreosoteCollector.class, Constants.MODID+"creosote_collector");
		GameRegistry.registerFuelHandler(new FuelRegistry());
		MinecraftForge.EVENT_BUS.register(new PileIgnitr());
		if(Config.RegisterRecipes)
			Crafting.registerRecipes();
	}
	public void postInit(FMLPostInitializationEvent e){
		if(Config.DisableFurnaceCharcoal){
			Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
			for (Iterator<Map.Entry<ItemStack,ItemStack>> entries = recipes.entrySet().iterator(); entries.hasNext(); ){
				Map.Entry<ItemStack,ItemStack> entry = entries.next();
				ItemStack result = entry.getValue();
				ItemStack input = entry.getKey();
				int[] ids=OreDictionary.getOreIDs(input);
				for(int id:ids){
					if(OreDictionary.getOreName(id).equals("logWood")&&ItemStack.areItemsEqual(result, charcoal)){
						entries.remove();
						break;
					}
				}
			}
		}
		Item ash=Item.getByNameOrId(Config.AshPreference);
		if(ash!=null){
			ItemStack ashStack=new ItemStack(ash, 1, Config.AshMeta);
			int[] ids=OreDictionary.getOreIDs(ashStack);
			for(int id:ids){
				if(OreDictionary.getOreName(id).equals("dustAsh")){
					ItemsRegistry.ash=ashStack.copy();
					break;
				}
			}
		}
		Item coke=Item.getByNameOrId(Config.CokePreference);
		if(coke!=null){
			ItemStack cokeStack=new ItemStack(coke, 1, Config.CokeMeta);
			int[] ids=OreDictionary.getOreIDs(cokeStack);
			for(int id:ids){
				if(OreDictionary.getOreName(id).equals("fuelCoke")){
					ItemsRegistry.coke=cokeStack.copy();
					break;
				}
			}
		}
		if(config.hasChanged()){
			config.save();
		}
	}
}
