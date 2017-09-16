package charcoalPit.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class BlockLogPile extends BlockBase{

	public BlockLogPile() {
		super(Material.WOOD, "log_pile");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		Blocks.FIRE.setFireInfo(this, 5, 5);
		setHardness(2F);
		setSoundType(SoundType.WOOD);
	}

}
