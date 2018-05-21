package charcoalPit.crafting;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.core.Config;
import charcoalPit.core.Constants;
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
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.fire_starter), new Object[]{
				" S",
				"S ",
				'S', Items.STICK
		}).setRegistryName("fireStarter"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.thatch), new Object[]{
				"SS",
				"SS",
				'S', ItemsRegistry.straw
		}).setRegistryName("thatch"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.straw, 4), new Object[]{
				"T",
				'T', BlocksRegistry.thatch
		}).setRegistryName("straw"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[0]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeWhite"
		}).setRegistryName("whitePot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[1]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeOrange"
		}).setRegistryName("orangePot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[2]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeMagenta"
		}).setRegistryName("magentaPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[3]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeLightBlue"
		}).setRegistryName("lightBluePot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[4]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeYellow"
		}).setRegistryName("yellowPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[5]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeLime"
		}).setRegistryName("limePot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[6]), new Object[]{
				BlocksRegistry.ceramicPot, "dyePink"
		}).setRegistryName("pinkPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[7]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeGray"
		}).setRegistryName("grayPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[8]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeLightGray"
		}).setRegistryName("silverPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[9]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeCyan"
		}).setRegistryName("cyanPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[10]), new Object[]{
				BlocksRegistry.ceramicPot, "dyePurple"
		}).setRegistryName("purplePot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[11]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeBlue"
		}).setRegistryName("bluePot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[12]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeBrown"
		}).setRegistryName("brownPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[13]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeGreen"
		}).setRegistryName("greenPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[14]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeRed"
		}).setRegistryName("redPot"));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.dyedPot[15]), new Object[]{
				BlocksRegistry.ceramicPot, "dyeBlack"
		}).setRegistryName("blackPot"));
		if(OreSmeltingRecipes.doesOreExist("ingotBronze")){
			ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.hatch), new Object[]{
					"I",
					"I",
					"I",
					'I', "blockBronze"
			}).setRegistryName("bronze_hatch"));
			ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.reinforcedBrick, 4), new Object[]{
					"IBI",
					"BIB",
					"IBI",
					'I', "ingotBronze", 'B', Blocks.BRICK_BLOCK
			}).setRegistryName("bronze_brick"));
		}else{
			ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.hatch), new Object[]{
					"I",
					"I",
					"I",
					'I', "blockIron"
			}).setRegistryName("iron_hatch"));
			ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(BlocksRegistry.reinforcedBrick, 4), new Object[]{
					"IBI",
					"BIB",
					"IBI",
					'I', "ingotIron", 'B', Blocks.BRICK_BLOCK
			}).setRegistryName("iron_brick"));
		}
		if(Config.DismantleLogPiles){
			ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Constants.MODID, Constants.MODNAME), new ItemStack(ItemsRegistry.wood_stack.getItem(), 9, ItemsRegistry.wood_stack.getItemDamage()), new Object[]{
				BlocksRegistry.logPile
			}).setRegistryName("logPileDismantle"));
		}
	}
}
