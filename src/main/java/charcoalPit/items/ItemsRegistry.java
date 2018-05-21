package charcoalPit.items;

import charcoalPit.blocks.BlocksRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemsRegistry {
	
	public static ItemBase coke=new ItemBase("item_coke");
	public static ItemBase ash=new ItemBase("item_ash");
	public static ItemFertilizer fertilizer=new ItemFertilizer("item_fertilizer");
	public static ItemBase clay_Pot=new ItemBase("clay_flowerpot");
	public static ItemBase magic_Coal=new ItemBase("alchemical_coal");
	public static ItemFireStarter fire_starter=new ItemFireStarter();
	public static ItemBase straw=new ItemBase("straw");
	public static ItemAeternalis aeternalis_fuel=new ItemAeternalis();
	
	public static ItemBlockBase logPile=new ItemBlockBase(BlocksRegistry.logPile);
	public static ItemBlockBase cokeBlock=new ItemBlockBase(BlocksRegistry.cokeBlock);
	public static ItemBlockBase stoneCollector=new ItemBlockBase(BlocksRegistry.stoneCollector);
	public static ItemBlockBase brickCollector=new ItemBlockBase(BlocksRegistry.brickCollector);
	public static ItemBlockBase netherCollector=new ItemBlockBase(BlocksRegistry.netherCollector);
	public static ItemBlockBase ceramicPot=new ItemBlockBase(BlocksRegistry.ceramicPot);
	public static ItemBlockBase clayPot=new ItemBlockBase(BlocksRegistry.clayPot);
	public static ItemBlockBase brokenPot=new ItemBlockBase(BlocksRegistry.brokenPot);
	public static ItemBlockBase[] dyedPot=new ItemBlockBase[16];
	public static ItemBlockBase thatch=new ItemBlockBase(BlocksRegistry.thatch);
	public static ItemBlockBloomery hatch=new ItemBlockBloomery(BlocksRegistry.hatch);
	public static ItemBlockBase reinforcedBrick=new ItemBlockBase(BlocksRegistry.reinforcedBrick);
	
	public static ItemStack coke_stack;
	public static ItemStack ash_stack;
	public static ItemStack wood_stack;
	public static ItemStack thatch_stack;
	public static ItemStack slag_stack;
	public static ItemStack rich_slag_stack;
	
	
	static{
		ceramicPot.setMaxStackSize(1);
		clayPot.setMaxStackSize(1);
		brokenPot.setMaxStackSize(1);
		for(int i=0;i<16;i++){
			dyedPot[i]=new ItemBlockBase(BlocksRegistry.dyedPot[i]);
			dyedPot[i].setMaxStackSize(1);
		}
	}
	
	public static void initStacks(){
		coke_stack=new ItemStack(coke);
		ash_stack=new ItemStack(ash);
		wood_stack=new ItemStack(Blocks.LOG);
		thatch_stack=new ItemStack(ItemsRegistry.straw);
		slag_stack=new ItemStack(Blocks.GRAVEL);
		rich_slag_stack=new ItemStack(Blocks.GRAVEL);
	}
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(coke, ash, fertilizer, clay_Pot, magic_Coal,
				fire_starter, straw, aeternalis_fuel, logPile, cokeBlock, stoneCollector, brickCollector, netherCollector,
				ceramicPot, clayPot, brokenPot, thatch, hatch, reinforcedBrick);
		event.getRegistry().registerAll(dyedPot);
	}
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event){
		coke.initModel();
		ash.initModel();
		fertilizer.initModel();
		clay_Pot.initModel();
		magic_Coal.initModel();
		fire_starter.initModel();
		straw.initModel();
		aeternalis_fuel.initModel();
		
		logPile.initModel();
		cokeBlock.initModel();
		stoneCollector.initModel();
		brickCollector.initModel();
		netherCollector.initModel();
		ceramicPot.initModel();
		clayPot.initModel();
		brokenPot.initModel();
		thatch.initModel();
		hatch.initModel();
		reinforcedBrick.initModel();
		for(int i=0;i<16;i++){
			dyedPot[i].initModel();
		}
	}
	public static void initOreDict(){
		OreDictionary.registerOre("fuelCoke", coke);
		OreDictionary.registerOre("dustAsh", ash);
		/*
		OreDictionary.registerOre("ingotBronze", Items.NETHERBRICK);
		OreDictionary.registerOre("ingotCopper", Items.BRICK);
		OreDictionary.registerOre("ingotTin", Items.QUARTZ);
		OreDictionary.registerOre("oreTin", Blocks.QUARTZ_ORE);
		OreDictionary.registerOre("oreCopper", Blocks.REDSTONE_ORE);
		*/
	}
}
