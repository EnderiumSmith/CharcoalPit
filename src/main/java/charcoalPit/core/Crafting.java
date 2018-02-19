package charcoalPit.core;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.items.ItemsRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Crafting {
	
	public static void registerRecipes(){
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.logPile, new Object[]{
				"LLL",
				"LLL",
				"LLL",
				'L',"logWood"
		}).setRegistryName("logPile"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.stoneCollector, new Object[]{
				"M M",
				"MBM",
				"MMM",
				'M',Blocks.STONEBRICK,
				'B',Items.BUCKET
		}).setRegistryName("stoneFunnel"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.brickCollector, new Object[]{
				"M M",
				"MBM",
				"MMM",
				'M',"ingotBrick",
				'B',Items.BUCKET
		}).setRegistryName("brickFunnel"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.netherCollector, new Object[]{
				"M M",
				"MBM",
				"MMM",
				'M',"ingotBrickNether",
				'B',Items.BUCKET
		}).setRegistryName("netherFunnel"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.cokeBlock, new Object[]{
				"CCC",
				"CCC",
				"CCC",
				'C',"fuelCoke"
		}).setRegistryName("blockCoke"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.coke_stack.getItem(), 9, ItemsRegistry.coke_stack.getItemDamage()), new Object[]{
				"B",
				'B',BlocksRegistry.cokeBlock
		}).setRegistryName("itemCoke"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.clay_Pot), new Object[]{
				"C C",
				" C ",
				'C', Items.CLAY_BALL
		}).setRegistryName("clayFlowerPot"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.clayPot, new Object[]{
				"CCC",
				"C C",
				"CCC",
				'C', Items.CLAY_BALL
		}).setRegistryName("clayPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.fertilizer, 2), new Object[]{
				Items.ROTTEN_FLESH,"dustAsh","dustAsh","dustAsh","dustAsh"
		}).setRegistryName("fertilizer1"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.fertilizer, 2), new Object[]{
				"dirt","dustAsh","dustAsh","dustAsh","dustAsh"
		}).setRegistryName("fertilizer2"));
		if(Config.DismantleLogPiles){
			ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.wood_stack.getItem(), 9, ItemsRegistry.wood_stack.getItemDamage()), new Object[]{
				BlocksRegistry.logPile
			}).setRegistryName("logPileDismantle"));
		}
	}
}
