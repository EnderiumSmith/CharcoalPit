package charcoalPit.blocks;

import java.util.Random;

import charcoalPit.tile.TileActivePile;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockActivePile extends BlockBase implements ITileEntityProvider{

	public final Boolean isCoal;
	public BlockActivePile(Material materialIn, String name,Boolean coal) {
		super(materialIn, name);
		isCoal=coal;
		setHardness(isCoal?5:2);
		setHarvestLevel(isCoal?"pickaxe":"axe", 0);
		setSoundType(isCoal?SoundType.STONE:SoundType.WOOD);
	}
	@Override
	public void register() {
		ForgeRegistries.BLOCKS.register(this);
	}
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		((TileActivePile)worldIn.getTileEntity(pos)).isValid=false;
	}
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double centerX = pos.getX() + 0.5F;
		double centerY = pos.getY() + 2F;
		double centerZ = pos.getZ() + 0.5F;
		//double d3 = 0.2199999988079071D;
		//double d4 = 0.27000001072883606D;
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, centerX+(rand.nextDouble()-0.5), centerY, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.1D, 0.0D);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, centerX+(rand.nextDouble()-0.5), centerY, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.15D, 0.0D);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, centerX+(rand.nextDouble()-0.5), centerY-1, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.1D, 0.0D);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, centerX+(rand.nextDouble()-0.5), centerY-1, centerZ+(rand.nextDouble()-0.5), 0.0D, 0.15D, 0.0D);
	}
	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return true;
	}
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return isCoal?Item.getItemFromBlock(Blocks.COAL_BLOCK):Item.getItemFromBlock(BlocksRegistry.logPile);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileActivePile(isCoal);
	}
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

}
