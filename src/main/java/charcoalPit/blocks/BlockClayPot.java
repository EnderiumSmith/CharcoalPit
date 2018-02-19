package charcoalPit.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockClayPot extends BlockBase{
	
	public static final AxisAlignedBB AABB_POT=new AxisAlignedBB(2D/16D, 0D, 2D/16D, 14D/16D, 1D, 14D/16D);

	public BlockClayPot() {
		super(Material.CLAY, "clay_pot");
		setSoundType(SoundType.GROUND);
		setHardness(0.6F);
		setResistance(1F);
		setHarvestLevel("none", 0);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB_POT;
	}
	
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
}
