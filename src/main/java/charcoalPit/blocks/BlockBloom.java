package charcoalPit.blocks;

import charcoalPit.tile.TileBloom;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBloom extends BlockBase implements ITileEntityProvider{

	public BlockBloom() {
		super(Material.ROCK, "bloom");
		setHardness(5);
		setHarvestLevel("pickaxe", 1);
		setSoundType(BlockSmeltedPot.BROKEN_POT);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		((TileBloom)worldIn.getTileEntity(pos)).dropInventory();
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBloom();
	}
	
}
