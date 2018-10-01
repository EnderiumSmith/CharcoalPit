package charcoalPit.jei;

import java.util.ArrayList;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.crafting.PotteryKilnRecipe;
import charcoalPit.gui.GuiCustomFurnace;
import charcoalPit.items.ItemsRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class CharcoalPitJEIPlugin implements IModPlugin{
	
	public static ArrayList<ItemStack> charcoal=new ArrayList<>(), coke=new ArrayList<>();
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		
	}
	
	@Override
	public void register(IModRegistry registry) {
		charcoal.add(new ItemStack(BlocksRegistry.logPile));
		charcoal.add(new ItemStack(Items.COAL,1,1));
		charcoal.add(ItemsRegistry.ash_stack);
		coke.add(new ItemStack(Blocks.COAL_BLOCK));
		coke.add(ItemsRegistry.coke_stack);
		coke.add(ItemsRegistry.ash_stack);
		registry.addIngredientInfo(charcoal, ItemStack.class, "jei.charcoal_pit.name");
		registry.addIngredientInfo(coke, ItemStack.class, "jei.coke_oven.name");
		registry.addIngredientInfo(PotteryKilnRecipe.getAllItems(), ItemStack.class, "jei.pottery_kiln.name");
		registry.addRecipeClickArea(GuiCustomFurnace.class, 79, 34, 25, 16, VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeClickArea(GuiCustomFurnace.class, 56, 34, 14, 14, VanillaRecipeCategoryUid.FUEL);
	}
	
}
