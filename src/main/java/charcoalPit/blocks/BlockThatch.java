package charcoalPit.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class BlockThatch extends BlockBase{

	public BlockThatch() {
		super(Material.GRASS, "thatch");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setSoundType(SoundType.PLANT);
		Blocks.FIRE.setFireInfo(this, 60, 100);
		setHardness(0.5F);
		setHarvestLevel("none", 0);
	}

}
