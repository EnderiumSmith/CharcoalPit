package charcoalPit.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class BlockCoke extends BlockBase{

	public BlockCoke() {
		super(Material.ROCK, "block_coke");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		Blocks.FIRE.setFireInfo(this, 5, 5);
		setHarvestLevel("pickaxe", 0);
		setHardness(5);
	}

}
