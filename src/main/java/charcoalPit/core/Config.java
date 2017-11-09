package charcoalPit.core;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	private static final String CATEGORY_GENERAL="general";
	
	public static int CharcoalTime=18000,CokeTime=36000;
	public static int CharcoalCreosote=50,CokeCreosote=100;
	public static String[] CokeBlocks;
	public static int CharcoalMin=3,CharcoalMax=6,CharcoalTotal=9;
	public static int CokeMin=6,CokeMax=9,CokeTotal=9;
	public static Boolean AllowFortune=true;
	public static int CokeFuel=3200,CreosoteFuel=4800;
	public static Boolean DisableFurnaceCharcoal=true;
	public static String AshPreference,CokePreference;
	public static int AshMeta,CokeMeta;
	public static boolean RegisterCreosote=true;
	public static boolean RegisterRecipes=true;
	public static int PotteryTime=8000;
	public static int ThatchAmount=1,WoodAmount=3;
	public static String WoodDefault,ThatchID;
	public static int WoodMeta=0,ThatchMeta=0;
	public static boolean DismantleLogPiles=true;
	public static int PotteryAsh=4;
	public static boolean RainyPottery=true;
	public static boolean DisableVanillaPottery=true;
	
	public static void readcfg(){
		Configuration cfg=CommonProxy.config;
		try{
			cfg.load();
			initConfig(cfg);
		} catch(Exception e){
			System.out.println("Charcoal Pit mod could not load configs");
		} finally{
			if(cfg.hasChanged()){
				cfg.save();
			}
		}
	}
	private static void initConfig(Configuration cfg){
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "Configuration");
		CharcoalTime=cfg.getInt("CharcoalTime", CATEGORY_GENERAL, 18000, 1000, 1000000, "Time the charcoal pit takes to finish. 1000 Ticks = 1 MC hour");
		CokeTime=cfg.getInt("CokeTime", CATEGORY_GENERAL, 36000, 1000, 1000000, "Time the coke oven takes to finish. 1000 Tick = 1 MC hour");
		CharcoalCreosote=cfg.getInt("CharcoalCreosote", CATEGORY_GENERAL, 50, 0, 1000, "Amount of Creosote Oil in mB produced per log");
		CokeCreosote=cfg.getInt("CokeCreosote", CATEGORY_GENERAL, 100, 0, 1000, "Amount of Creosote Oil in mB produced per coal");
		CokeBlocks=cfg.getStringList("CokeBlocks", CATEGORY_GENERAL, new String[]{"minecraft:brick_block","minecraft:nether_brick"}, "List of block registry names that are valid for the coke oven outer shell");
		CharcoalMin=cfg.getInt("CharcoalMin", CATEGORY_GENERAL, 3, 0, 1000, "The minimum amount of charcoal a charcoal pile can drop");
		CharcoalMax=cfg.getInt("CharcoalMax", CATEGORY_GENERAL, 6, 0, 1000, "The maximum amount of charcoal a charcoal pile can drop without fortune");
		CharcoalTotal=cfg.getInt("CharcoalTotal", CATEGORY_GENERAL, 9, 0, 1000, "The maximum amount of charcoal a charcoal pile can ever drop");
		CokeMin=cfg.getInt("CokeMin", CATEGORY_GENERAL, 6, 0, 1000, "The minimum amount of coke a coke pile can drop");
		CokeMax=cfg.getInt("CokeMax", CATEGORY_GENERAL, 9, 0, 1000, "The maximum amount of coke a coke pile can drop without fortune");
		CokeTotal=cfg.getInt("CokeTotal", CATEGORY_GENERAL, 9, 0, 1000, "The maximum amount of coke a coke pile can ever drop");
		AllowFortune=cfg.getBoolean("AllowFortune", CATEGORY_GENERAL, true, "If Fortune enchant applies to charcoal/coke piles");
		CokeFuel=cfg.getInt("CokeFuel", CATEGORY_GENERAL, 3200, 0, 1000000, "The fuel value of coke. (char)coal is 1600");
		CreosoteFuel=cfg.getInt("CreosoteFuel", CATEGORY_GENERAL, 4800, 0, 1000000, "The fuel value of a creosote bucket. (char)coal is 1600");
		DisableFurnaceCharcoal=cfg.getBoolean("DisableFurnaceCharcoal", CATEGORY_GENERAL, true, "If the vanilla log->charcoal furnace recipe should be disabled");
		AshPreference=cfg.getString("AshPreference", CATEGORY_GENERAL, "forestry:ash", "The prefered ash item to drop from charcoal/coke piles. Defaults to the mod's own if invalid");
		AshMeta=cfg.getInt("AshMeta", CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "The metadata of the prefered ash item to drop from charcoal/coke piles");
		CokePreference=cfg.getString("CokePreference", CATEGORY_GENERAL, "railcraft:fuel_coke", "The prefered coke item to drop from coke piles. Defaults to mod's own if invalid");
		CokeMeta=cfg.getInt("CokeMeta", CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "The metadata of the prefered coke item to drop from coke piles");
		RegisterCreosote=cfg.getBoolean("RegisterCreosote", CATEGORY_GENERAL, true, "If the mod should register a Creosote Oil fluid. If disabled another mod must provide Creosote Oil or it might crash. Also disables the fluid block");
		RegisterRecipes=cfg.getBoolean("RegisterRecipes", CATEGORY_GENERAL, true, "Set to false to disable hard coded recipes");
		PotteryTime=cfg.getInt("PotteryTime", CATEGORY_GENERAL, 8000, 1000, 1000000, "Time the pottery kiln takes to finish. 1000 Ticks = 1 MC hour");
		ThatchAmount=cfg.getInt("ThatchAmount", CATEGORY_GENERAL, 1, 1, 64, "The amount of thatch needed by the pottery kiln");
		WoodAmount=cfg.getInt("WoodAmount", CATEGORY_GENERAL, 3, 1, 64, "The amount of wood needed by the pottery kiln");
		WoodDefault=cfg.getString("WoodDefault", CATEGORY_GENERAL, "minecraft:log", "The wood dropped if a pottery kiln or log pile is dismantled");
		ThatchID=cfg.getString("ThatchID", CATEGORY_GENERAL, "minecraft:hay_block", "The block used as thatch for the pottery kiln");
		WoodMeta=cfg.getInt("WoodMeta", CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "The metadata of the wood dropped if a pottery kiln or log pile is dismantled");
		ThatchMeta=cfg.getInt("ThatchMeta", CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "The metadata of the block used as thatch for the pottery kiln");
		DismantleLogPiles=cfg.getBoolean("DismantleLogPiles", CATEGORY_GENERAL, true, "If log piles can be dismantled");
		PotteryAsh=cfg.getInt("PotteryAsh", CATEGORY_GENERAL, 4, 0, 64, "The amount of ash dropped by a completed pottery kiln");
		RainyPottery=cfg.getBoolean("RainyPottery", CATEGORY_GENERAL, true, "If pottery kilns get extingushed by rain");
		DisableVanillaPottery=cfg.getBoolean("DisableVanillaPottery", CATEGORY_GENERAL, true, "If the vanilla methods of making pottery should be disabled");
		
		
	}
}
