package charcoalPit.core;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	private static final String CATEGORY_GENERAL="General";
	private static final String CATEGORY_CHARCOAL_PIT="Charcoal Pit";
	private static final String CATEGORY_COKE_OVEN="Coke Oven";
	private static final String CATEGORY_KILN="Kiln";
	private static final String CATEGORY_BLOOMERY="Bloomery";
	private static final String CATEGORY_ORE="Ore";
	
	public static int CharcoalTime=18000,CokeTime=36000;
	public static int CharcoalCreosote=50,CokeCreosote=100;
	public static String[] CokeBlocks;
	public static String[] CokeBlocksMeta;
	public static int CharcoalMin=3,CharcoalMax=6,CharcoalTotal=9;
	public static int CokeMin=6,CokeMax=9,CokeTotal=9;
	public static Boolean AllowFortune=true;
	public static int CokeFuel=3200,CreosoteFuel=4800;
	public static Boolean DisableFurnaceCharcoal=true;
	public static String AshPreference,CokePreference;
	public static int AshMeta,CokeMeta;
	public static boolean RegisterRecipes=true;
	public static int PotteryTime=8000;
	public static int ThatchAmount=1,WoodAmount=3;
	public static String WoodDefault,ThatchDefault;
	public static String []ThatchIDs;
	public static int WoodMeta=0,ThatchMeta;
	public static boolean DismantleLogPiles=true;
	public static int PotteryAsh=4;
	public static boolean RainyPottery=true;
	public static boolean DisableVanillaPottery=true;
	public static String[] PotteryRecipes;
	public static boolean DisableDefaultPottery;
	public static boolean DisableFurnaceOre;
	public static String[] Slag;
	public static int BloomeryTime=15000;
	public static String[] BloomeryBlocks;
	public static float KilnSlagChance, BloomSlagChance;
	public static String[] CeramicBlackList;
	public static String[] HoeList;
	public static boolean enableStraw=true;
	
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
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General Configuration");
		AllowFortune=cfg.getBoolean("AllowFortune", CATEGORY_GENERAL, true, "If Fortune enchant applies to charcoal/coke piles");
		CokeFuel=cfg.getInt("CokeFuel", CATEGORY_GENERAL, 3200, 0, 1000000, "The fuel value of coke. (char)coal is 1600");
		CreosoteFuel=cfg.getInt("CreosoteFuel", CATEGORY_GENERAL, 4800, 0, 1000000, "The fuel value of a creosote bucket. (char)coal is 1600");
		DisableFurnaceCharcoal=cfg.getBoolean("DisableFurnaceCharcoal", CATEGORY_GENERAL, true, "If the vanilla log->charcoal furnace recipe should be disabled");
		AshPreference=cfg.getString("AshPreference", CATEGORY_GENERAL, "forestry:ash", "The prefered ash item to drop from charcoal/coke piles. Defaults to the mod's own if invalid");
		AshMeta=cfg.getInt("AshMeta", CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "The metadata of the prefered ash item to drop from charcoal/coke piles");
		CokePreference=cfg.getString("CokePreference", CATEGORY_GENERAL, "railcraft:fuel_coke", "The prefered coke item to drop from coke piles. Defaults to mod's own if invalid");
		CokeMeta=cfg.getInt("CokeMeta", CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "The metadata of the prefered coke item to drop from coke piles");
		RegisterRecipes=cfg.getBoolean("RegisterRecipes", CATEGORY_GENERAL, true, "Set to false to disable hard coded recipes");
		DismantleLogPiles=cfg.getBoolean("DismantleLogPiles", CATEGORY_GENERAL, true, "If log piles can be dismantled");
		DisableVanillaPottery=cfg.getBoolean("DisableVanillaPottery", CATEGORY_GENERAL, true, "If the vanilla methods of making pottery should be disabled");
		CeramicBlackList=cfg.getStringList("CeramicBlacklist", CATEGORY_GENERAL, new String[]{}, "A list of items that should not be allowed in the Ceramic Vessel due to nesting. Format is 'modID:ItemID'");
		HoeList=cfg.getStringList("HoeList", CATEGORY_GENERAL, new String[]{}, "A list of non vanilla hoes that should be able to get straw. Format is modID:itemID");
		enableStraw=cfg.getBoolean("EnableStraw", CATEGORY_GENERAL, true, "If getting straw from braking grass with a hoe should be enabled");
		cfg.addCustomCategoryComment(CATEGORY_CHARCOAL_PIT, "Charcoal Pit Configuration");
		CharcoalTime=cfg.getInt("CharcoalTime", CATEGORY_CHARCOAL_PIT, 18000, 1000, 1000000, "Time the charcoal pit takes to finish. 1000 Ticks = 1 MC hour");
		CharcoalCreosote=cfg.getInt("CharcoalCreosote", CATEGORY_CHARCOAL_PIT, 50, 0, 1000, "Amount of Creosote Oil in mB produced per log");
		CharcoalMin=cfg.getInt("CharcoalMin", CATEGORY_CHARCOAL_PIT, 3, 0, 1000, "The minimum amount of charcoal a charcoal pile can drop");
		CharcoalMax=cfg.getInt("CharcoalMax", CATEGORY_CHARCOAL_PIT, 6, 0, 1000, "The maximum amount of charcoal a charcoal pile can drop without fortune");
		CharcoalTotal=cfg.getInt("CharcoalTotal", CATEGORY_CHARCOAL_PIT, 9, 0, 1000, "The maximum amount of charcoal a charcoal pile can ever drop");
		cfg.addCustomCategoryComment(CATEGORY_COKE_OVEN, "Coke Oven Configuration");
		CokeTime=cfg.getInt("CokeTime", CATEGORY_COKE_OVEN, 36000, 1000, 1000000, "Time the coke oven takes to finish. 1000 Tick = 1 MC hour");
		CokeCreosote=cfg.getInt("CokeCreosote", CATEGORY_COKE_OVEN, 100, 0, 1000, "Amount of Creosote Oil in mB produced per coal");
		CokeBlocks=cfg.getStringList("CokeBlocks", CATEGORY_COKE_OVEN, new String[]{"minecraft:brick_block","minecraft:nether_brick","minecraft:brick_stairs","minecraft:nether_brick_stairs",
				"minecraft:red_nether_brick","minecraft:obsidian","minecraft:iron_door","minecraft:iron_trapdoor","charcoal_pit:bronze_reinforced_brick"}, "List of block registry names that are valid for the coke oven outer shell");
		CokeBlocksMeta=cfg.getStringList("CokeBlocksMeta", CATEGORY_COKE_OVEN, new String[]{
				"minecraft:stone_slab", "4", "minecraft:stone_slab", "6", "minecraft:stone_slab", "12", "minecraft:stone_slab", "14",
				"minecraft:double_stone_slab", "4", "minecraft:double_stone_slab", "6", "minecraft:double_stone_slab", "12", "minecraft:double_stone_slab", "14"
		}, "Metadata senzitive version of CokeBlocks. Format is 'modId:blockId' blockmeta. Use '*' for 'any'");
		CokeMin=cfg.getInt("CokeMin", CATEGORY_COKE_OVEN, 6, 0, 1000, "The minimum amount of coke a coke pile can drop");
		CokeMax=cfg.getInt("CokeMax", CATEGORY_COKE_OVEN, 9, 0, 1000, "The maximum amount of coke a coke pile can drop without fortune");
		CokeTotal=cfg.getInt("CokeTotal", CATEGORY_COKE_OVEN, 9, 0, 1000, "The maximum amount of coke a coke pile can ever drop");
		cfg.addCustomCategoryComment(CATEGORY_KILN, "Kiln Configuration");
		PotteryTime=cfg.getInt("PotteryTime", CATEGORY_KILN, 8000, 1000, 1000000, "Time the pottery kiln takes to finish. 1000 Ticks = 1 MC hour");
		ThatchAmount=cfg.getInt("ThatchAmount", CATEGORY_KILN, 6, 1, 64, "The amount of thatch needed by the pottery kiln");
		WoodAmount=cfg.getInt("WoodAmount", CATEGORY_KILN, 3, 1, 64, "The amount of wood needed by the pottery kiln");
		WoodDefault=cfg.getString("WoodDefault", CATEGORY_KILN, "minecraft:log", "The wood dropped if a pottery kiln or log pile is dismantled");
		ThatchIDs=cfg.getStringList("ThatchIDs", CATEGORY_KILN, new String[]{"charcoal_pit:straw","0"}, "The blocks used as thatch for the pottery kiln. Format is 'modID:itemID' itemMeta");
		WoodMeta=cfg.getInt("WoodMeta", CATEGORY_KILN, 0, 0, Integer.MAX_VALUE, "The metadata of the wood dropped if a pottery kiln or log pile is dismantled");
		ThatchDefault=cfg.getString("ThatchDefault", CATEGORY_KILN, "charcoal_pit:straw", "The ID of the default thatch item the kiln will drop if broken");
		ThatchMeta=cfg.getInt("ThatchMeta", CATEGORY_KILN, 0, 0, Integer.MAX_VALUE, "The metadata of the defailt thatch item the kiln will drop if broken");
		PotteryAsh=cfg.getInt("PotteryAsh", CATEGORY_KILN, 4, 0, 64, "The amount of ash dropped by a completed pottery kiln");
		RainyPottery=cfg.getBoolean("RainyPottery", CATEGORY_KILN, true, "If pottery kilns get extingushed by rain");
		DisableDefaultPottery=cfg.getBoolean("DisableDefaultPottery", CATEGORY_KILN, false, "If the default pottery kiln recipes should be disabled");
		PotteryRecipes=cfg.getStringList("PotteryRecipes", CATEGORY_KILN, new String[]{""}, "Register custom pottery kiln recipes in the format 'modId:ingredientId' ingredientMeta 'modId:resultId' resultMeta. ex: minecraft:clay_ball 0 minecraft:brick 0");
		cfg.addCustomCategoryComment(CATEGORY_BLOOMERY, "Bloomery Configuration");
		BloomeryTime=cfg.getInt("BloomeryTime", CATEGORY_BLOOMERY, 15000, 1000, 1000000, "Time the bloomery takes to finish. 1000 Ticks = 1 MC hour");
		BloomeryBlocks=cfg.getStringList("BloomeryBlocks", CATEGORY_BLOOMERY, new String[]{"minecraft:hardened_clay", "*", "minecraft:stained_hardened_clay", "*", "minecraft:obsidian", "*", "minecraft:sandstone", "*", "minecraft:red_sandstone", "*", 
				"minecraft:stone", "*", "minecraft:end_stone", "*", "minecraft:brick_block", "*", "minecraft:prismarine", "*", "minecraft:stonebrick", "*", "minecraft:nether_brick", "*", "minecrat:red_nether_brick", "*", "minecraft:end_bricks", "*",
				"minecraft:purpur_block", "*", "minecraft:purpur_pillar", "*", "minecraft:concrete", "*", "minecraft:quartz_block", "*", "minecraft:stone_slab", "0", "minecraft:stone_slab", "1", "minecraft:stone_slab", "4", "minecraft:stone_slab", "5",
				"minecraft:stone_slab", "6", "minecraft:stone_slab", "7", "minecraft:stone_slab", "8", "minecraft:stone_slab", "9", "minecraft:stone_slab", "12", "minecraft:stone_slab", "13", "minecraft:stone_slab", "14", "minecraft:stone_slab", "15",
				"minecraft:stone_slab2", "*", "minecraft:purpur_slab", "*", "minecraft:double_stone_slab", "0", "minecraft:double_stone_slab", "1", "minecraft:double_stone_slab", "4", "minecraft:double_stone_slab", "5", "minecraft:double_stone_slab", "6",
				"minecraft:double_stone_slab", "7", "minecraft:double_stone_slab", "8", "minecraft:double_stone_slab", "9", "minecraft:double_stone_slab", "12", "minecraft:double_stone_slab", "13", "minecraft:double_stone_slab", "14", "minecraft:double_stone_slab", "15",
				"minecraft:double_stone_slab2", "*", "minecraft:purpur_double_slab", "*", "minecraft:brick_stairs", "*", "minecraft:stone_brick_stairs", "*", "minecraft:nether_brick_stairs", "*", "minecraft:sandstone_stairs", "*", "minecraft:quartz_stairs", "*",
				"minecraft:red_sandstone_stairs", "*", "minecraft:purpur_stairs", "*", "charcoal_pit:bronze_reinforced_brick", "*"
		}, "List of blocks that are valid for the bloomery structure. Format is 'modID:blockID' blocksmeta. Use '*' for 'any'");
		cfg.addCustomCategoryComment(CATEGORY_ORE, "Ore Configuration");
		Slag=cfg.getStringList("SlagList", CATEGORY_ORE, new String[]{}, "The IDs of the (rich)slag that will drop from ore smelting. Format is 'modID:slagId' slagMeta 'modID:richSlagID' richSlagMeta");
		DisableFurnaceOre=cfg.getBoolean("DisableFurnaceOre", CATEGORY_ORE, true, "If furnace ore smelting recipes should be disabled");
		KilnSlagChance=cfg.getFloat("KilnSlagChance", CATEGORY_ORE, 0.5F, 0F, 1F, "The chance of rich slag being dropped insted of plain slag from the ore kiln");
		BloomSlagChance=cfg.getFloat("BloomSlagChance", CATEGORY_ORE, 0.5F, 0F, 1F, "The chance of rich slag being dropped insted of plain slag from the bloomery bloom");
		
		
		
		
	}
}
