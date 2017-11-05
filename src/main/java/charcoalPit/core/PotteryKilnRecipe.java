package charcoalPit.core;

import java.util.ArrayList;

import charcoalPit.items.ItemsRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PotteryKilnRecipe {
	
	public static ArrayList<PotteryKilnRecipe> recipes=new ArrayList<>();
	
	public ItemStack input,output;
	public PotteryKilnRecipe(ItemStack in,ItemStack out) {
		input=in;
		output=out;
	}
	
	public static void initRecipes(){
		recipes.add(new PotteryKilnRecipe(new ItemStack(Items.CLAY_BALL), new ItemStack(Items.BRICK)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.CLAY), new ItemStack(Blocks.HARDENED_CLAY)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(ItemsRegistry.Clay_Pot), new ItemStack(Items.FLOWER_POT)));
		
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 0), new ItemStack(Blocks.WHITE_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 1), new ItemStack(Blocks.ORANGE_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 2), new ItemStack(Blocks.MAGENTA_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 3), new ItemStack(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 4), new ItemStack(Blocks.YELLOW_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 5), new ItemStack(Blocks.LIME_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 6), new ItemStack(Blocks.PINK_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 7), new ItemStack(Blocks.GRAY_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 8), new ItemStack(Blocks.SILVER_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9), new ItemStack(Blocks.CYAN_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 10), new ItemStack(Blocks.PURPLE_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 11), new ItemStack(Blocks.BLUE_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 12), new ItemStack(Blocks.BROWN_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 13), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 14), new ItemStack(Blocks.RED_GLAZED_TERRACOTTA)));
		recipes.add(new PotteryKilnRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 15), new ItemStack(Blocks.BLACK_GLAZED_TERRACOTTA)));
	}
	public static boolean isValidInput(ItemStack stack){
		if(stack.isEmpty())
			return false;
		for(int i=0;i<recipes.size();i++){
			if(OreDictionary.itemMatches(stack, recipes.get(i).input, false))
				return true;
		}
		return false;
	}
	public static ItemStack getResult(ItemStack in){
		if(in.isEmpty())
			return ItemStack.EMPTY;
		for(int i=0;i<recipes.size();i++){
			if(OreDictionary.itemMatches(in, recipes.get(i).input, false)){
				return recipes.get(i).output.copy();
			}
		}
		return ItemStack.EMPTY;
	}
}
