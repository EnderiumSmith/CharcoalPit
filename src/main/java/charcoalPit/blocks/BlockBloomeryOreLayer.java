package charcoalPit.blocks;

import java.util.Random;

import charcoalPit.tile.TileBloomery;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBloomeryOreLayer extends BlockBase{

	public static final PropertyInteger LAYER=PropertyInteger.create("layer", 1, 8);
	public static final PropertyBool ACTIVE=PropertyBool.create("active");
	
	public static final AxisAlignedBB ORE_LAYER_1=new AxisAlignedBB(0D, 0D, 0D, 1D, 2/16D, 1D);
	public static final AxisAlignedBB ORE_LAYER_2=new AxisAlignedBB(0D, 0D, 0D, 1D, 4/16D, 1D);
	public static final AxisAlignedBB ORE_LAYER_3=new AxisAlignedBB(0D, 0D, 0D, 1D, 6/16D, 1D);
	public static final AxisAlignedBB ORE_LAYER_4=new AxisAlignedBB(0D, 0D, 0D, 1D, 0.5D, 1D);
	public static final AxisAlignedBB ORE_LAYER_5=new AxisAlignedBB(0D, 0D, 0D, 1D, 10/16D, 1D);
	public static final AxisAlignedBB ORE_LAYER_6=new AxisAlignedBB(0D, 0D, 0D, 1D, 12/16D, 1D);
	public static final AxisAlignedBB ORE_LAYER_7=new AxisAlignedBB(0D, 0D, 0D, 1D, 14/16D, 1D);
	public static final AxisAlignedBB ORE_LAYER_8=new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 1D);
	
	public BlockBloomeryOreLayer() {
		super(Material.ROCK, "bloomery_ore_layer");
		setHardness(-1);
		setResistance(6000000);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{LAYER, ACTIVE});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(LAYER, meta%8+1).withProperty(ACTIVE, meta>7);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LAYER)-1+(state.getValue(ACTIVE)?8:0);
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(stateIn.getValue(ACTIVE)){		
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
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		for(int i=0;i<4;i++){
			for(EnumFacing facing:EnumFacing.HORIZONTALS){
				TileEntity tile=worldIn.getTileEntity(pos.offset(facing).offset(EnumFacing.DOWN, i));
				if(tile instanceof TileBloomery){
					((TileBloomery)tile).isValid=false;
				}
			}
		}
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch(state.getValue(LAYER)){
		case 1:return ORE_LAYER_1;
		case 2:return ORE_LAYER_2;
		case 3:return ORE_LAYER_3;
		case 4:return ORE_LAYER_4;
		case 5:return ORE_LAYER_5;
		case 6:return ORE_LAYER_6;
		case 7:return ORE_LAYER_7;
		case 8:return ORE_LAYER_8;
		default: return ORE_LAYER_8;
		}
	}
	
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
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
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if(state.getValue(ACTIVE))
			return 15;
		else
			return 0;
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (worldIn.getBlockState(pos).getValue(ACTIVE) && !entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase)entityIn))
        {
            entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }

}
