package charcoalPit.crafting;

import java.util.ArrayList;

import charcoalPit.crafting.OreSmeltingRecipes.SmeltingFuel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BlastFurnaceRecipes {
	
	public static ArrayList<BlastRecipe> recipes;
	public static ArrayList<SmeltingFuel> fuels;
	
	
	public static class BlastRecipe{
		public Object[] recipe;
		public float[] useChance;
		public Object output;
		public int outAmount;
		public boolean usePrefix;
		
		public BlastRecipe(Object output, int amount, boolean prefix, float[] useChance ,Object... recipe) {
			this.recipe=recipe;
			this.useChance=useChance;
			this.output=output;
			this.outAmount=amount;
			this.usePrefix=prefix;
		}
		
		public boolean isInputEqual(ItemStack in, int slot){
			if(in.isEmpty())
				return false;
			if(slot>=recipe.length)
				return false;
			if(recipe[slot] instanceof ItemStack){
				if(((ItemStack)recipe[slot]).isItemEqual(in))
					return true;
			}
			if(recipe[slot] instanceof String){
				int[] ids=OreDictionary.getOreIDs(in);
				for(int id:ids){
					if(usePrefix){
						String ore=OreDictionary.getOreName(id);
						for(int j=0;j<OreSmeltingRecipes.orePrefixes.size();j++){
							if(ore.equals(OreSmeltingRecipes.orePrefixes.get(j)+recipe[slot]))
								return true;
						}
					}else{
						if(OreDictionary.getOreName(id).equals(recipe[slot]))
							return true;
					}
				}
			}
			return false;
		}
		
		public boolean isOutputEqual(ItemStack in){
			if(in.isEmpty())
				return false;
			if(output instanceof ItemStack){
				if(((ItemStack)output).isItemEqual(in))
					return true;
			}
			if(output instanceof String){
				int[] ids=OreDictionary.getOreIDs(in);
				for(int id:ids){
					if(OreDictionary.getOreName(id).equals(output))
						return true;
				}
			}
			return false;
		}
	}
	
	
}
