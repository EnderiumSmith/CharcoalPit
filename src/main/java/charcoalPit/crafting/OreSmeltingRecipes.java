package charcoalPit.crafting;

import java.util.ArrayList;

import charcoalPit.tile.TileClayPot.ClayPotItemHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class OreSmeltingRecipes {
	
	public static ArrayList<String> orePrefixes=new ArrayList<>();
	//base ores: Copper, Zinc, Silver, Tin, Gold, Lead, Bismuth
	//advanced ores: Aluminium, Aluminum, Titanium, Iron, Nickel, Platinum
	public static ArrayList<AlloyRecipe> alloyRecipes=new ArrayList<>();
	public static ArrayList<SmeltingFuel> smeltingFuels=new ArrayList<>();
	
	public static boolean isValidOre(ItemStack ore, boolean advanced){
		if(ore.isEmpty())
			return false;
		for(int i=0;i<alloyRecipes.size();i++){
			if(advanced&&!alloyRecipes.get(i).isAdvanced)
				continue;
			if(alloyRecipes.get(i).isInputEqual(ore))
				return true;
		}
		return false;
	}
	
	public static boolean isValidFuel(ItemStack fuel){
		if(fuel.isEmpty())
			return false;
		for(int i=0;i<smeltingFuels.size();i++){
			if(smeltingFuels.get(i).isInputEqual(fuel))
				return true;
		}
		return false;
	}
	
	public static boolean doesOreExist(String ore){
		return OreDictionary.getOres(ore).size()>0;
	}
	
	public static void addAlloyRecipe(AlloyRecipe recipe){
		if((recipe.output instanceof String&&doesOreExist((String)recipe.output))||recipe.output instanceof ItemStack){
			alloyRecipes.add(recipe);
		}
	}
	//ore kiln
	public static int oreKilnGetFuelRequired(NBTTagCompound nbt){
		ClayPotItemHandler kiln=new ClayPotItemHandler();
		kiln.deserializeNBT(nbt.getCompoundTag("items"));
		int f=0;
		for(int i=0;i<9;i++){
			if(!kiln.getStackInSlot(i).isEmpty()){
				int[] ids=OreDictionary.getOreIDs(kiln.getStackInSlot(i));
				for(int id:ids){
					String ore=OreDictionary.getOreName(id);
					if(ore.startsWith("ore")){
						f++;
						break;
					}
				}
			}
		}
		return f;
	}
	
	public static int oreKilnGetFuelAvailable(NBTTagCompound nbt){
		ClayPotItemHandler kiln=new ClayPotItemHandler();
		kiln.deserializeNBT(nbt.getCompoundTag("items"));
		int f=0;
		for(int i=9;i<12;i++){
			if(!kiln.getStackInSlot(i).isEmpty()){
				for(SmeltingFuel fuel:smeltingFuels){
					if(fuel.isInputEqual(kiln.getStackInSlot(i))){
						f+=fuel.value*kiln.getStackInSlot(i).getCount();
						break;
					}
				}
			}
		}
		return f;
	}
	
	public static boolean oreKilnIsEmpty(NBTTagCompound nbt){
		ClayPotItemHandler kiln=new ClayPotItemHandler();
		kiln.deserializeNBT(nbt.getCompoundTag("items"));
		for(int i=0;i<9;i++){
			if(!kiln.getStackInSlot(i).isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public static boolean oreKilnIsEmpty(ClayPotItemHandler kiln){
		for(int i=0;i<9;i++){
			if(!kiln.getStackInSlot(i).isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public static int oreKilnGetOreAmount(NBTTagCompound nbt){
		ClayPotItemHandler kiln=new ClayPotItemHandler();
		kiln.deserializeNBT(nbt.getCompoundTag("items"));
		int a=0;
		for(int i=0;i<9;i++){
			if(!kiln.getStackInSlot(i).isEmpty())
				a++;
		}
		return a;
	}
	
	public static int oreKilnGetRecipeAmount(ItemStack result){
		for(AlloyRecipe recipe:alloyRecipes){
			if(recipe.isAdvanced)
				continue;
			if(recipe.isOutputEqual(result)){
				return recipe.outAmount;
			}
		}
		return 0;
	}
	
	public static ItemStack oreKilnGetOutput(NBTTagCompound nbt){
		ClayPotItemHandler kiln=new ClayPotItemHandler();
		for(AlloyRecipe recipe:alloyRecipes){
			if(recipe.isAdvanced)
				continue;
			kiln.deserializeNBT(nbt.getCompoundTag("items"));
			int r=0;
			while(!oreKilnIsEmpty(kiln)){
				boolean b=false;
				for(int i=0;i<recipe.recipe.length;i++){
					boolean e=false;
					for(int j=0;j<9;j++){
						if(recipe.isInputEqual(kiln.getStackInSlot(j), i)){
							e=true;
							kiln.setStackInSlot(j, ItemStack.EMPTY);
							break;
						}
					}
					if(!e){
						b=true;
						break;
					}
				}
				if(b){
					break;
				}else{
					r++;
				}
			}
			if(r>0&&oreKilnIsEmpty(kiln)){
				if(recipe.output instanceof ItemStack){
					ItemStack out=((ItemStack)recipe.output).copy();
					out.setCount(recipe.outAmount*r);
					return out;
				}else{
					if(recipe.output instanceof String){
						ItemStack out=OreDictionary.getOres((String)recipe.output).get(0);
						out.setCount(recipe.outAmount*r);
						return out;
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	public static int oreKilnGetMaxSpace(ItemStack result){
		for(AlloyRecipe recipe:alloyRecipes){
			if(recipe.isAdvanced)
				continue;
			if(recipe.isOutputEqual(result)){
				return 9/recipe.recipe.length;
			}
		}
		return 0;
	}
	
	public static void initSmeltingRecipes(){
		orePrefixes.add("ore");
		orePrefixes.add("ingot");
		orePrefixes.add("dust");
		
		smeltingFuels.add(new SmeltingFuel("fuelCoal", new ItemStack(Items.COAL, 1, 0), 1));
		smeltingFuels.add(new SmeltingFuel("fuelCharcoal", new ItemStack(Items.COAL, 1, 1), 1));
		smeltingFuels.add(new SmeltingFuel("fuelCoke", ItemStack.EMPTY, 2));
		
		addAlloyRecipe(new AlloyRecipe("ingotCopper", 1, false, true, new Object[]{"Copper"}));
		addAlloyRecipe(new AlloyRecipe("ingotZinc", 1, false, true, new Object[]{"Zinc"}));
		addAlloyRecipe(new AlloyRecipe("ingotSilver", 1, false, true, new Object[]{"Silver"}));
		addAlloyRecipe(new AlloyRecipe("ingotTin", 1, false, true, new Object[]{"Tin"}));
		addAlloyRecipe(new AlloyRecipe("ingotGold", 1, false, true, new Object[]{"Gold"}));
		addAlloyRecipe(new AlloyRecipe("ingotLead", 1, false, true, new Object[]{"Lead"}));
		addAlloyRecipe(new AlloyRecipe("ingotBismuth", 1, false, true, new Object[]{"Bismuth"}));
		
		addAlloyRecipe(new AlloyRecipe("ingotAluminium", 1, true, true, new Object[]{"Aluminium"}));
		addAlloyRecipe(new AlloyRecipe("ingotAluminum", 1, true, true, new Object[]{"Aluminum"}));
		addAlloyRecipe(new AlloyRecipe("ingotTitanium", 1, true, true, new Object[]{"Titanium"}));
		addAlloyRecipe(new AlloyRecipe("ingotIron", 1, false, true, new Object[]{"Iron"}));
		addAlloyRecipe(new AlloyRecipe("ingotNickel", 1, true, true, new Object[]{"Nickel"}));
		addAlloyRecipe(new AlloyRecipe("ingotPlatinum", 1, true, true, new Object[]{"Platinum"}));
		
		addAlloyRecipe(new AlloyRecipe("ingotBronze", 4, false, true, new Object[]{
				"Copper","Copper","Copper","Tin"
		}));
		addAlloyRecipe(new AlloyRecipe("ingotBrass", 4, false, true, new Object[]{
				"Copper","Copper","Copper","Zinc"
		}));
		addAlloyRecipe(new AlloyRecipe("ingotBismuthBronze", 4, false, true, new Object[]{
				"Copper","Copper","Zinc","Bismuth"
		}));
		addAlloyRecipe(new AlloyRecipe("ingotBlackBronze", 4, false, true, new Object[]{
				"Copper","Copper","Silver","Gold"
		}));
		addAlloyRecipe(new AlloyRecipe("ingotElectrum", 2, false, true, new Object[]{
				"Silver","Gold"
		}));
		addAlloyRecipe(new AlloyRecipe("ingotBlackBronze", 2, false, true, new Object[]{
				"Copper","Electrum"
		}));
		addAlloyRecipe(new AlloyRecipe("ingotInvar", 3, true, true, new Object[]{
				"Iron","Nickel","Nickel"
		}));
		addAlloyRecipe(new AlloyRecipe("ingotConstantan", 2, true, true, new Object[]{
				"Nickel","Copper"
		}));
	}
	
	public static class AlloyRecipe{
		public Object[] recipe;
		public Object output;
		public int outAmount;
		public boolean usePrefix;
		public boolean isAdvanced;
		
		public AlloyRecipe(Object output, int amount, boolean advanced, boolean usePrefix, Object...recipe) {
			this.output=output;
			this.outAmount=amount;
			this.usePrefix=usePrefix;
			this.isAdvanced=advanced;
			this.recipe=recipe;
		}
		
		public boolean isInputEqual(ItemStack in){
			if(in.isEmpty())
				return false;
			for(int i=0;i<recipe.length;i++){
				if(recipe[i] instanceof ItemStack){
					if(((ItemStack)recipe[i]).isItemEqual(in))
						return true;
				}
				if(recipe[i] instanceof String){
					int[] ids=OreDictionary.getOreIDs(in);
					for(int id:ids){
						if(usePrefix){
							String ore=OreDictionary.getOreName(id);
							for(int j=0;j<orePrefixes.size();j++){
								if(ore.equals(orePrefixes.get(j)+recipe[i]))
									return true;
							}
						}else{
							if(OreDictionary.getOreName(id).equals(recipe[i]))
								return true;
						}
					}
				}
			}
			return false;
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
						for(int j=0;j<orePrefixes.size();j++){
							if(ore.equals(orePrefixes.get(j)+recipe[slot]))
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
	
	public static class SmeltingFuel{
		
		public String ore;
		public ItemStack item;
		public int value;
		public SmeltingFuel(String ore,ItemStack item, int value) {
			this.ore=ore;
			this.item=item;
			this.value=value;
		}
		
		public boolean isInputEqual(ItemStack in){
			if(in.isEmpty())
				return false;
			if(!item.isEmpty()){
				if(item.isItemEqual(in))
					return true;
			}
			if(ore!=null){
				int[] ids=OreDictionary.getOreIDs(in);
				for(int id:ids){
					if(OreDictionary.getOreName(id).equals(ore))
						return true;
				}
			}
			return false;
		}
		
	}
	
}
