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
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.LogPile, new Object[]{
				"LLL",
				"LLL",
				"LLL",
				'L',"logWood"
		}).setRegistryName("logPile"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.StoneCollector, new Object[]{
				"M M",
				"MBM",
				"MMM",
				'M',Blocks.STONEBRICK,
				'B',Items.BUCKET
		}).setRegistryName("stoneFunnel"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.BrickCollector, new Object[]{
				"M M",
				"MBM",
				"MMM",
				'M',"ingotBrick",
				'B',Items.BUCKET
		}).setRegistryName("brickFunnel"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.NetherCollector, new Object[]{
				"M M",
				"MBM",
				"MMM",
				'M',"ingotBrickNether",
				'B',Items.BUCKET
		}).setRegistryName("netherFunnel"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), BlocksRegistry.CokeBlock, new Object[]{
				"CCC",
				"CCC",
				"CCC",
				'C',"fuelCoke"
		}).setRegistryName("blockCoke"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.coke.getItem(), 9, ItemsRegistry.coke.getItemDamage()), new Object[]{
				"B",
				'B',BlocksRegistry.CokeBlock
		}).setRegistryName("itemCoke"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.Clay_Pot), new Object[]{
				"C C",
				" C ",
				'C', Items.CLAY_BALL
		}).setRegistryName("clayPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.Fertilizer, 2), new Object[]{
				Items.ROTTEN_FLESH,"dustAsh","dustAsh","dustAsh","dustAsh"
		}).setRegistryName("fertilizer1"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.Fertilizer, 2), new Object[]{
				"dirt","dustAsh","dustAsh","dustAsh","dustAsh"
		}).setRegistryName("fertilizer2"));
		if(Config.DismantleLogPiles){
			ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.wood.getItem(), 9, ItemsRegistry.wood.getItemDamage()), new Object[]{
				BlocksRegistry.LogPile
			}).setRegistryName("logPileDismantle"));
		}
	}
}
