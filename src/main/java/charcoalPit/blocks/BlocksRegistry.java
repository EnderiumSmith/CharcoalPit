package charcoalPit.blocks;

import net.minecraft.block.material.Material;

public class BlocksRegistry {
	
	public static BlockLogPile LogPile=new BlockLogPile();
	public static BlockCoke CokeBlock=new BlockCoke();
	public static BlockActivePile ActiveLogPile=new BlockActivePile(Material.WOOD, "active_log_pile", false);
	public static BlockActivePile ActiveCoalPile=new BlockActivePile(Material.ROCK, "active_coal_pile", true);
	public static BlockAshPile CharcoalPile=new BlockAshPile("charcoal_pile", false);
	public static BlockAshPile CokePile=new BlockAshPile("coke_pile", true);
	public static BlockCreosoteCollector StoneCollector=new BlockCreosoteCollector("stone_creosote_collector", false);
	public static BlockCreosoteCollector BrickCollector=new BlockCreosoteCollector("brick_creosote_collector", true);
	public static BlockCreosoteCollector NetherCollector=new BlockCreosoteCollector("nether_creosote_collector", true);
	
	public static void registerBlocks(){
		LogPile.register();
		CokeBlock.register();
		ActiveLogPile.register();
		ActiveCoalPile.register();
		CharcoalPile.register();
		CokePile.register();
		StoneCollector.register();
		BrickCollector.register();
		NetherCollector.register();
	}
	public static void initModel(){
		LogPile.initModel();
		CokeBlock.initModel();
		StoneCollector.initModel();
		BrickCollector.initModel();
		NetherCollector.initModel();
	}
}
