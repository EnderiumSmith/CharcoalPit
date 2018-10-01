package charcoalPit.core;

import charcoalPit.items.ItemsRegistry;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class Thaumcraft {
	
	public static void init(){
		try{
			ThaumcraftApi.registerObjectTag(new ItemStack(ItemsRegistry.straw), 
					new AspectList().add(Aspect.PLANT, 5).add(Aspect.AIR, 1).add(Aspect.ENTROPY, 1));
			ThaumcraftApi.registerObjectTag(new ItemStack(ItemsRegistry.coke), 
					new AspectList().add(Aspect.ENERGY, 15).add(Aspect.FIRE, 15));
			ThaumcraftApi.registerObjectTag(new ItemStack(ItemsRegistry.ash), 
					new AspectList().add(Aspect.EARTH, 15).add(Aspect.FIRE, 3));
			ThaumcraftApi.registerObjectTag(new ItemStack(ItemsRegistry.ceramicPot), 
					new AspectList().add(Aspect.WATER, 30).add(Aspect.EARTH, 30).add(Aspect.FIRE, 1));
			ThaumcraftApi.registerObjectTag(new ItemStack(ItemsRegistry.brokenPot), 
					new AspectList().add(Aspect.WATER, 30).add(Aspect.EARTH, 30).add(Aspect.FIRE, 1).add(Aspect.ENTROPY, 1));
			ThaumcraftApi.registerObjectTag(new ItemStack(ItemsRegistry.magic_Coal), 
					new AspectList().add(Aspect.ENERGY, 15).add(Aspect.FIRE, 15).add(Aspect.MAGIC, 5));
			ThaumcraftApi.registerObjectTag(new ItemStack(ItemsRegistry.aeternalis_fuel), 
					new AspectList().add(Aspect.ELDRITCH, 100).add(Aspect.ALCHEMY, 20).add(Aspect.DESIRE, 20).add(Aspect.FIRE, 50).add(Aspect.LIGHT, 10).add(Aspect.EXCHANGE, 10).add(Aspect.ENERGY, 50).add(Aspect.MAGIC, 30));
		}catch(Exception e){
			System.out.println("Could not apply Thaumcraft aspects. Probably because Thaumcraft is not present");
		}
	}
	
}
