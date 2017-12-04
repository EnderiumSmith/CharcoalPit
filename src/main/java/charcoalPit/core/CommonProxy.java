package charcoalPit.core;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.fluids.FluidsRegistry;
import charcoalPit.items.ItemsRegistry;
import charcoalPit.tile.TileActivePile;
import charcoalPit.tile.TileCreosoteCollector;
import charcoalPit.tile.TilePotteryKiln;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
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
		//read config
		File file=e.getModConfigurationDirectory();
		config=new Configuration(new File(file.getPath(),"charcoal_pit.cfg"));
		Config.readcfg();
		//register item/block registry
		MinecraftForge.EVENT_BUS.register(BlocksRegistry.class);
		MinecraftForge.EVENT_BUS.register(ItemsRegistry.class);
		
		ItemsRegistry.initStacks();
		
		FluidsRegistry.registerFluids();
	}
	public void init(FMLInitializationEvent e){
		//tile entity
		GameRegistry.registerTileEntity(TileActivePile.class, Constants.MODID+"active_pile");
		GameRegistry.registerTileEntity(TileCreosoteCollector.class, Constants.MODID+"creosote_collector");
		GameRegistry.registerTileEntity(TilePotteryKiln.class, Constants.MODID+"pottery_kiln");
		
		GameRegistry.registerFuelHandler(new FuelRegistry());
		MinecraftForge.EVENT_BUS.register(new PileIgnitr());
		MinecraftForge.EVENT_BUS.register(new PotionRegistry());
		
		PotionRegistry.initPotions();
		ItemsRegistry.initOreDict();
		
		if(!Config.DisableDefaultPottery)
			PotteryKilnRecipe.initRecipes();
		PotteryKilnRecipe.initCustomRecipes(Config.PotteryRecipes);
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
				if(input.isEmpty())
					continue;
				int[] ids=OreDictionary.getOreIDs(input);
				for(int id:ids){
					if(OreDictionary.getOreName(id).equals("logWood")&&ItemStack.areItemsEqual(result, charcoal)){
						entries.remove();
						break;
					}
				}
			}
		}
		if(Config.DisableVanillaPottery){
			Map<ItemStack, ItemStack> recipes=FurnaceRecipes.instance().getSmeltingList();
			for(Iterator<Map.Entry<ItemStack, ItemStack>> entries=recipes.entrySet().iterator(); entries.hasNext();){
				Map.Entry<ItemStack, ItemStack> entry=entries.next();
				ItemStack result=entry.getValue();
				ItemStack input=entry.getKey();
				if(input.isEmpty())
					continue;
				if(PotteryKilnRecipe.isValidInput(input)&&PotteryKilnRecipe.getResult(input).isItemEqual(result)){
					entries.remove();
				}
			}
		}
		Item ash=Item.getByNameOrId(Config.AshPreference);
		if(ash!=null){
			ItemStack ashStack=new ItemStack(ash, 1, Config.AshMeta);
			int[] ids=OreDictionary.getOreIDs(ashStack);
			for(int id:ids){
				if(OreDictionary.getOreName(id).equals("dustAsh")){
					ItemsRegistry.ash_stack=ashStack.copy();
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
					ItemsRegistry.coke_stack=cokeStack.copy();
					break;
				}
			}
		}
		Item wood=Item.getByNameOrId(Config.WoodDefault);
		if(wood!=null){
			ItemStack woodStack=new ItemStack(wood, 1, Config.WoodMeta);
			int[] ids=OreDictionary.getOreIDs(woodStack);
			for(int id:ids){
				if(OreDictionary.getOreName(id).equals("logWood")){
					ItemsRegistry.wood_stack=woodStack.copy();
					break;
				}
			}
		}
		Item thatch=Item.getByNameOrId(Config.ThatchID);
		if(thatch!=null){
			ItemsRegistry.thatch_stack=new ItemStack(thatch, 1, Config.ThatchMeta);
		}
		for(PotteryKilnRecipe recipe:PotteryKilnRecipe.recipes){
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(recipe.input.getItem(), new DispenserPlaceKiln());
		}
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemsRegistry.thatch_stack.getItem(), new DispenserPlaceKiln());
		NonNullList<ItemStack> woods=OreDictionary.getOres("logWood");
		for(ItemStack stack:woods){
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(stack.getItem(), new DispenserPlaceKiln());
		}
		if(config.hasChanged()){
			config.save();
		}
	}
}
