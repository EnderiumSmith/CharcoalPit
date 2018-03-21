package charcoalPit.core;

import java.util.ArrayList;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.crafting.OreSmeltingRecipes;
import charcoalPit.items.ItemsRegistry;
import charcoalPit.tile.TileClayPot.ClayPotItemHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
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
		recipes.add(new PotteryKilnRecipe(new ItemStack(ItemsRegistry.clay_Pot), new ItemStack(Items.FLOWER_POT)));
		
		recipes.add(new PotteryKilnRecipe(new ItemStack(BlocksRegistry.clayPot), new ItemStack(BlocksRegistry.ceramicPot)));
		
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
	public static void initCustomRecipes(String[] customRecipes){
		if(customRecipes!=null){
			for(int i=0;i<customRecipes.length;i++){
				String[] customRecipe=customRecipes[i].split(" ");
				if(customRecipe.length!=4)
					continue;
				Item input=Item.getByNameOrId(customRecipe[0]);
				Item output=Item.getByNameOrId(customRecipe[2]);
				if(input!=null&&output!=null){
					recipes.add(new PotteryKilnRecipe(new ItemStack(input, 1, Integer.parseInt(customRecipe[1])), new ItemStack(output, 1, Integer.parseInt(customRecipe[3]))));
				}
			}
		}
	}
	public static boolean isValidInput(ItemStack stack){
		if(stack.isEmpty())
			return false;
		if(stack.isItemEqual(new ItemStack(BlocksRegistry.clayPot))){
			if(stack.hasTagCompound()){
				NBTTagCompound tag=stack.getTagCompound();
				if(tag.hasKey("items")){
					ClayPotItemHandler items=new ClayPotItemHandler();
					items.deserializeNBT(tag.getCompoundTag("items"));
					boolean isEmpty=true;
					for(int i =0;i<12;i++){
						if(!items.getStackInSlot(i).isEmpty()){
							isEmpty=false;
							break;
						}
					}
					if(!isEmpty){
						if(OreSmeltingRecipes.oreKilnGetOutput(tag)!=ItemStack.EMPTY&&
								OreSmeltingRecipes.oreKilnGetFuelAvailable(tag)>=OreSmeltingRecipes.oreKilnGetFuelRequired(tag)){
							return true;
						}
						return false;
					}
				}
			}
		}
		for(int i=0;i<recipes.size();i++){
			if(OreDictionary.itemMatches(stack, recipes.get(i).input, false))
				return true;
		}
		return false;
	}
	public static ItemStack getResult(ItemStack in){
		if(in.isEmpty())
			return ItemStack.EMPTY;
		if(in.isItemEqual(new ItemStack(BlocksRegistry.clayPot))){
			if(in.hasTagCompound()){
				NBTTagCompound tag=in.getTagCompound();
				if(tag.hasKey("items")){
					ClayPotItemHandler items=new ClayPotItemHandler();
					items.deserializeNBT(tag.getCompoundTag("items"));
					boolean isEmpty=true;
					for(int i =0;i<12;i++){
						if(!items.getStackInSlot(i).isEmpty()){
							isEmpty=false;
							break;
						}
					}
					if(!isEmpty){
						ItemStackHandler ingots=new ItemStackHandler(1);
						ingots.setStackInSlot(0, OreSmeltingRecipes.oreKilnGetOutput(tag));
						int slag=OreSmeltingRecipes.oreKilnGetFuelRequired(tag);
						ItemStack result=new ItemStack(BlocksRegistry.brokenPot);
						NBTTagCompound tag2=new NBTTagCompound();
						tag2.setTag("items", ingots.serializeNBT());
						if(slag>0){
							tag2.setInteger("slag", slag);
						}
						result.setTagCompound(tag2);
						return result;
					}
				}
			}
		}
		for(int i=0;i<recipes.size();i++){
			if(OreDictionary.itemMatches(in, recipes.get(i).input, false)){
				return recipes.get(i).output.copy();
			}
		}
		return ItemStack.EMPTY;
	}
	public static ArrayList<ItemStack> getAllItems(){
		ArrayList<ItemStack> list=new ArrayList<>();
		for(PotteryKilnRecipe recipe:recipes){
			list.add(recipe.input.copy());
			list.add(recipe.output.copy());
		}
		return list;
	}
}
