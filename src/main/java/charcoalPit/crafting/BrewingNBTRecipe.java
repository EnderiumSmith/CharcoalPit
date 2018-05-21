package charcoalPit.crafting;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingOreRecipe;

public class BrewingNBTRecipe extends BrewingOreRecipe{
	
	public BrewingNBTRecipe(@Nonnull ItemStack input, @Nonnull String ingredient, @Nonnull ItemStack output) {
		super(input, ingredient, output);
	}
	
	@Override
	public boolean isInput(ItemStack stack) {
		return super.isInput(stack) && ItemStack.areItemStackTagsEqual(getInput(), stack);
	}
}
