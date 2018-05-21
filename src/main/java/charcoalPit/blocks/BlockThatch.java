package charcoalPit.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockThatch extends BlockBase{

	public BlockThatch() {
		super(Material.GRASS, "thatch");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setSoundType(SoundType.PLANT);
		Blocks.FIRE.setFireInfo(this, 60, 100);
		setHardness(0.5F);
		setHarvestLevel("none", 0);
	}
	
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        entityIn.fall(fallDistance, 0.2F);
    }

}
