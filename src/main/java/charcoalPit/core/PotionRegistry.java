package charcoalPit.core;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionRegistry {
	
	public static final PotionType COKE_COLA=new PotionType("coke_cola", new PotionEffect[]{new PotionEffect(MobEffects.SPEED, 3600, 1)}).setRegistryName(Constants.MODID, "coke_cola");
	public static final PotionType COKE_COLA_LONG=new PotionType("coke_cola", new PotionEffect[]{new PotionEffect(MobEffects.SPEED, 9600, 1)}).setRegistryName(Constants.MODID, "coke_cola_long");
	public static final PotionType COKE_COLA_STRONG=new PotionType("coke_cola", new PotionEffect[]{new PotionEffect(MobEffects.SPEED, 1800, 2)}).setRegistryName(Constants.MODID, "coke_cola_strong");
	
	public static ItemStack Coke_Cola_Bottle;
	public static ItemStack Coke_Cola_Bottle_Long;
	public static ItemStack Coke_Cola_Bottle_Strong;
	
	public static ItemStack Coke_Cola_Splash_Bottle;
	public static ItemStack Coke_Cola_Splash_Bottle_Long;
	public static ItemStack Coke_Cola_Splash_Bottle_Strong;
	
	public static ItemStack Coke_Cola_Lingering_Bottle;
	public static ItemStack Coke_Cola_Lingeting_Bottle_Long;
	public static ItemStack Coke_Cola_Lingering_Bottle_Strong;
	
	public static void initPotions(){
		ForgeRegistries.POTION_TYPES.register(COKE_COLA);
		ForgeRegistries.POTION_TYPES.register(COKE_COLA_LONG);
		ForgeRegistries.POTION_TYPES.register(COKE_COLA_STRONG);
		
		Coke_Cola_Bottle=PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), COKE_COLA);
		Coke_Cola_Bottle_Long=PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), COKE_COLA_LONG);
		Coke_Cola_Bottle_Strong=PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), COKE_COLA_STRONG);
		
		Coke_Cola_Splash_Bottle=PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), COKE_COLA);
		Coke_Cola_Splash_Bottle_Long=PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), COKE_COLA_LONG);
		Coke_Cola_Splash_Bottle_Strong=PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), COKE_COLA_STRONG);
		
		Coke_Cola_Lingering_Bottle=PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), COKE_COLA);
		Coke_Cola_Lingeting_Bottle_Long=PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), COKE_COLA_LONG);
		Coke_Cola_Lingering_Bottle_Strong=PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), COKE_COLA_STRONG);
		
		Coke_Cola_Bottle.getTagCompound().setInteger("CustomPotionColor", 4738376);
		Coke_Cola_Bottle_Long.getTagCompound().setInteger("CustomPotionColor", 4738376);
		Coke_Cola_Bottle_Strong.getTagCompound().setInteger("CustomPotionColor", 4738376);
		
		Coke_Cola_Splash_Bottle.getTagCompound().setInteger("CustomPotionColor", 4738376);
		Coke_Cola_Splash_Bottle_Long.getTagCompound().setInteger("CustomPotionColor", 4738376);
		Coke_Cola_Splash_Bottle_Strong.getTagCompound().setInteger("CustomPotionColor", 4738376);
		
		Coke_Cola_Lingering_Bottle.getTagCompound().setInteger("CustomPotionColor", 4738376);
		Coke_Cola_Lingeting_Bottle_Long.getTagCompound().setInteger("CustomPotionColor", 4738376);
		Coke_Cola_Lingering_Bottle_Strong.getTagCompound().setInteger("CustomPotionColor", 4738376);
		
		//speed to coke
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.SWIFTNESS), "fuelCoke", Coke_Cola_Bottle));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.LONG_SWIFTNESS), "fuelCoke", Coke_Cola_Bottle_Long));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_SWIFTNESS), "fuelCoke", Coke_Cola_Bottle_Strong));
		//splash
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.SWIFTNESS), "fuelCoke", Coke_Cola_Splash_Bottle));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.LONG_SWIFTNESS), "fuelCoke", Coke_Cola_Splash_Bottle_Long));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.STRONG_SWIFTNESS), "fuelCoke", Coke_Cola_Splash_Bottle_Strong));
		//lingering
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.SWIFTNESS), "fuelCoke", Coke_Cola_Lingering_Bottle));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.LONG_SWIFTNESS), "fuelCoke", Coke_Cola_Lingeting_Bottle_Long));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.STRONG_SWIFTNESS), "fuelCoke", Coke_Cola_Lingering_Bottle_Strong));
		//boosts
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(Coke_Cola_Bottle, "dustRedstone", Coke_Cola_Bottle_Long));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(Coke_Cola_Bottle, "dustGlowstone", Coke_Cola_Bottle_Strong));
		//splash
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(Coke_Cola_Splash_Bottle, "dustRedstone", Coke_Cola_Splash_Bottle_Long));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(Coke_Cola_Splash_Bottle, "dustGlowstone", Coke_Cola_Splash_Bottle_Strong));
		//lingering
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(Coke_Cola_Lingering_Bottle, "dustRedstone", Coke_Cola_Lingeting_Bottle_Long));
		BrewingRecipeRegistry.addRecipe(new BrewingNBTRecipe(Coke_Cola_Lingering_Bottle, "dustGlowstone", Coke_Cola_Lingering_Bottle_Strong));
		
	}
	
	@SubscribeEvent
	public void colorCoke(PotionBrewEvent.Post event){
		for(int i=0;i<event.getLength();i++){
			if(PotionUtils.getPotionFromItem(event.getItem(i))==COKE_COLA||
					PotionUtils.getPotionFromItem(event.getItem(i))==COKE_COLA_LONG||
					PotionUtils.getPotionFromItem(event.getItem(i))==COKE_COLA_STRONG){
				ItemStack brew=event.getItem(i).copy();
				brew.getTagCompound().setInteger("CustomPotionColor", 4738376);
				event.setItem(i, brew);
			}
		}
	}
	
}
