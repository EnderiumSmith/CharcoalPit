package charcoalPit.items;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemsRegistry {
	
	public static ItemBase Coke=new ItemBase("item_coke");
	public static ItemBase Ash=new ItemBase("item_ash");
	public static ItemFertilizer Fertilizer=new ItemFertilizer("item_fertilizer");
	public static ItemBase Clay_Pot=new ItemBase("clay_pot");
	public static ItemStack coke;
	public static ItemStack ash;
	public static ItemStack wood;
	public static ItemStack thatch;
	
	public static void registerItems(){
		Coke.register();
		Ash.register();
		Fertilizer.register();
		Clay_Pot.register();
		coke=new ItemStack(Coke);
		ash=new ItemStack(Ash);
		wood=new ItemStack(Blocks.LOG);
		thatch=new ItemStack(Blocks.HAY_BLOCK);
	}
	public static void initModel(){
		Coke.initModel();
		Ash.initModel();
		Fertilizer.initModel();
		Clay_Pot.initModel();
	}
	public static void initOreDict(){
		OreDictionary.registerOre("fuelCoke", Coke);
		OreDictionary.registerOre("dustAsh", Ash);
	}
}
