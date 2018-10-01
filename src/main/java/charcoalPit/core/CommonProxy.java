package charcoalPit.core;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import charcoalPit.CharcoalPit;
import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.crafting.Crafting;
import charcoalPit.crafting.OreSmeltingRecipes;
import charcoalPit.crafting.PotteryKilnRecipe;
import charcoalPit.fluids.FluidsRegistry;
import charcoalPit.gui.GUIHandler;
import charcoalPit.items.ItemsRegistry;
import charcoalPit.tile.TileActivePile;
import charcoalPit.tile.TileBloom;
import charcoalPit.tile.TileBloomery;
import charcoalPit.tile.TileCeramicPot;
import charcoalPit.tile.TileClayPot;
import charcoalPit.tile.TileCreosoteCollector;
import charcoalPit.tile.TileCustomFurnace;
import charcoalPit.tile.TilePotteryKiln;
import charcoalPit.tile.TileSmeltedPot;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
		GameRegistry.registerTileEntity(TileActivePile.class, Constants.MODID+":active_pile");
		GameRegistry.registerTileEntity(TileCreosoteCollector.class, Constants.MODID+":creosote_collector");
		GameRegistry.registerTileEntity(TilePotteryKiln.class, Constants.MODID+":pottery_kiln");
		GameRegistry.registerTileEntity(TileCeramicPot.class, Constants.MODID+":ceramic_pot");
		GameRegistry.registerTileEntity(TileClayPot.class, Constants.MODID+":clay_pot");
		GameRegistry.registerTileEntity(TileSmeltedPot.class, Constants.MODID+":broken_pot");
		GameRegistry.registerTileEntity(TileBloomery.class, Constants.MODID+":bloomery");
		GameRegistry.registerTileEntity(TileBloom.class, Constants.MODID+":bloom");
		GameRegistry.registerTileEntity(TileCustomFurnace.class, Constants.MODID+":furnace");
		
		GameRegistry.registerFuelHandler(new FuelRegistry());
		MinecraftForge.EVENT_BUS.register(new PileIgnitr());
		MinecraftForge.EVENT_BUS.register(new PotionRegistry());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(CharcoalPit.instance, new GUIHandler());
		
		PotionRegistry.initPotions();
		ItemsRegistry.initOreDict();
		
		if(!Config.DisableDefaultPottery)
			PotteryKilnRecipe.initRecipes();
		PotteryKilnRecipe.initCustomRecipes(Config.PotteryRecipes);
		if(Config.RegisterRecipes)
			Crafting.registerRecipes();
		OreSmeltingRecipes.initSmeltingRecipes();
		
		if(Loader.isModLoaded("thaumcraft"))
			Thaumcraft.init();
	}
	public void postInit(FMLPostInitializationEvent e){
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
		Item thatch=Item.getByNameOrId(Config.ThatchDefault);
		if(thatch!=null){
			ItemStack thatchStack=new ItemStack(thatch, 1, Config.ThatchMeta);
			ItemsRegistry.thatch_stack=thatchStack.copy();
		}
		if(Config.Slag.length>=2){
			Item slag=Item.getByNameOrId(Config.Slag[0]);
			if(slag!=null){
				ItemStack slagStack=new ItemStack(slag,1,Integer.parseInt(Config.Slag[1]));
				ItemsRegistry.slag_stack=slagStack.copy();
			}
			if(Config.Slag.length>=4){
				Item richSlag=Item.getByNameOrId(Config.Slag[2]);
				if(richSlag!=null){
					ItemStack richSlagStack=new ItemStack(richSlag,1,Integer.parseInt(Config.Slag[3]));
					ItemsRegistry.rich_slag_stack=richSlagStack.copy();
				}
			}else{
				ItemsRegistry.rich_slag_stack=ItemsRegistry.slag_stack;
			}
		}
		for(PotteryKilnRecipe recipe:PotteryKilnRecipe.recipes){
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(recipe.input.getItem(), new DispenserPlaceKiln());
		}
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemsRegistry.thatch_stack.getItem(), new DispenserPlaceKiln());
		NonNullList<ItemStack> woods=OreDictionary.getOres("logWood");
		for(ItemStack stack:woods){
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(stack.getItem(), new DispenserPlaceKiln());
		}
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ItemsRegistry.ceramicPot, new DispenserPlacePot());
		if(config.hasChanged()){
			config.save();
		}
	}
}
