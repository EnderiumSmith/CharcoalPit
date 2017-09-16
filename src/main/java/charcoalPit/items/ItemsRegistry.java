package charcoalPit.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemsRegistry {
	
	public static ItemBase Coke=new ItemBase("item_coke");
	public static ItemBase Ash=new ItemBase("item_ash");
	public static ItemFertilizer Fertilizer=new ItemFertilizer("item_fertilizer");
	public static ItemStack coke;
	public static ItemStack ash;
	
	public static void registerItems(){
		Coke.register();
		Ash.register();
		Fertilizer.register();
		coke=new ItemStack(Coke);
		ash=new ItemStack(Ash);
	}
	public static void initModel(){
		Coke.initModel();
		Ash.initModel();
		Fertilizer.initModel();
	}
	public static void initOreDict(){
		OreDictionary.registerOre("fuelCoke", Coke);
		OreDictionary.registerOre("dustAsh", Ash);
	}
}
