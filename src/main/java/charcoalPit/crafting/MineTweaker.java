package charcoalPit.crafting;

import java.util.ArrayList;

import charcoalPit.crafting.OreSmeltingRecipes.AlloyRecipe;
import charcoalPit.crafting.OreSmeltingRecipes.SmeltingFuel;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.charcoalpit")
@ZenRegister
public class MineTweaker {
	
	@ZenMethod
	public static void addKilnRecipe(IItemStack in, IItemStack out){
		PotteryKilnRecipe.recipes.add(new PotteryKilnRecipe(CraftTweakerMC.getItemStack(in), CraftTweakerMC.getItemStack(out)));
	}
	
	@ZenMethod
	public static void flushKilnRecipes(){
		PotteryKilnRecipe.recipes=new ArrayList<>();
	}
	
	@ZenMethod
	public static void addSmeltingFuel(IOreDictEntry in, int value){
		OreSmeltingRecipes.smeltingFuels.add(new SmeltingFuel(in.getName(), ItemStack.EMPTY, value));
	}
	
	@ZenMethod
	public static void addSmeltingFuel(IItemStack in, int value){
		OreSmeltingRecipes.smeltingFuels.add(new SmeltingFuel(null, CraftTweakerMC.getItemStack(in), value));
	}
	
	@ZenMethod
	public static void flushSmeltingFuels(){
		OreSmeltingRecipes.smeltingFuels=new ArrayList<>();
	}
	
	@ZenMethod
	public static void addAlloyRecipe(IIngredient result, int amount, boolean advanced, boolean usePrefix, IIngredient[] recipe){
		Object out;
		Object[] in=new Object[recipe.length];
		if(result instanceof IItemStack)
			out=CraftTweakerMC.getItemStack(result);
		else if(result instanceof IOreDictEntry)
			out=((IOreDictEntry) result).getName();
		else{
			CraftTweakerAPI.logError("Unknown input");
			return;
		}
		for(int i=0;i<recipe.length;i++){
			if(recipe[i] instanceof IItemStack)
				in[i]=CraftTweakerMC.getItemStack(recipe[i]);
			else if(recipe[i] instanceof IOreDictEntry)
				in[i]=((IOreDictEntry) recipe[i]).getName();
			else{
				CraftTweakerAPI.logError("Unknown input");
				return;
			}
		}
		OreSmeltingRecipes.addAlloyRecipe(new AlloyRecipe(out, amount, advanced, usePrefix, in));
	}
	
	@ZenMethod
	public static void flushAlloyRecipes(){
		OreSmeltingRecipes.alloyRecipes=new ArrayList<>();
	}
	
}
