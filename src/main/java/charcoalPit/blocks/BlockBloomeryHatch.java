package charcoalPit.blocks;

import java.util.List;
import java.util.Random;

import com.mojang.realmsclient.gui.ChatFormatting;

import charcoalPit.core.MethodHelper;
import charcoalPit.crafting.OreSmeltingRecipes;
import charcoalPit.tile.TileBloomery;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBloomeryHatch extends BlockBase implements ITileEntityProvider{

	public static final PropertyBool OPEN=PropertyBool.create("open");
	public static final PropertyDirection FACING=PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool ACTIVE=PropertyBool.create("active");
	//collision boxes
	//closed
	public static final AxisAlignedBB HATCH_NORTH_AABB=new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 2/16D);
	public static final AxisAlignedBB HATCH_SOUTH_AABB=new AxisAlignedBB(0D, 0D, 1-2/16D, 1D, 1D, 1D);
	public static final AxisAlignedBB HATCH_WEST_AABB=new AxisAlignedBB(0D, 0D, 0D, 2/16D, 1D, 1D);
	public static final AxisAlignedBB HATCH_EAST_AABB=new AxisAlignedBB(1-2/16D, 0D, 0D, 1D, 1D, 1D);
	//open
	public static final AxisAlignedBB HATCH_NORTH_RIGHT_AABB=new AxisAlignedBB(1-2/16D, 0D, 0D, 1D, 1D, 0.5D);
	public static final AxisAlignedBB HATCH_NORTH_LEFT_AABB=new AxisAlignedBB(0D, 0D, 0D, 2/16D, 1D, 0.5D);
	public static final AxisAlignedBB HATCH_SOUTH_RIGHT_AABB=new AxisAlignedBB(0D, 0D, 0.5D, 2/16D, 1D, 1D);
	public static final AxisAlignedBB HATCH_SOUTH_LEFT_AABB=new AxisAlignedBB(1-2/16D, 0D, 0.5D, 1D, 1D, 1D);
	public static final AxisAlignedBB HATCH_WEST_RIGHT_AABB=new AxisAlignedBB(0D, 0D, 1-2/16D, 0.5D, 1D, 1D);
	public static final AxisAlignedBB HATCH_WEST_LEFT_AABB=new AxisAlignedBB(0D, 0D, 0D, 0.5D, 1D, 2/16D);
	public static final AxisAlignedBB HATCH_EAST_RIGHT_AABB=new AxisAlignedBB(0.5D, 0D, 1-2/16D, 1D, 1D, 1D);
	public static final AxisAlignedBB HATCH_EAST_LEFT_AABB=new AxisAlignedBB(0.5D, 0D, 0D, 1D, 1D, 2/16D);
	
	public BlockBloomeryHatch() {
		super(Material.IRON, "bloomery_hatch");
		setHardness(5);
		setResistance(20);
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(CreativeTabs.DECORATIONS);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING, OPEN, ACTIVE});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex()+(state.getValue(OPEN)?4:0)+(state.getValue(ACTIVE)?8:0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta%4)).withProperty(OPEN, meta/4==1||meta/4==3).withProperty(ACTIVE, meta>7);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		// TODO Auto-generated method stub
		if(!pos.offset(state.getValue(FACING)).equals(fromPos)&&!pos.offset(state.getValue(FACING).getOpposite()).equals(fromPos)){
			for(EnumFacing facing:new EnumFacing[]{EnumFacing.UP,EnumFacing.DOWN,state.getValue(FACING).rotateY(),state.getValue(FACING).rotateYCCW()}){
				if(!MethodHelper.BloomeryIsValidBlock(worldIn, pos, facing))
					worldIn.destroyBlock(pos, true);
			}
		}
	}
	
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		EnumFacing facing=state.getValue(FACING);
		if(facing==EnumFacing.NORTH){
			if(state.getValue(OPEN)){
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_NORTH_RIGHT_AABB);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_NORTH_LEFT_AABB);
			}else
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_NORTH_AABB);
		}else if(facing==EnumFacing.SOUTH){
			if(state.getValue(OPEN)){
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_SOUTH_RIGHT_AABB);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_SOUTH_LEFT_AABB);
			}else
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_SOUTH_AABB);
		}else if(facing==EnumFacing.WEST){
			if(state.getValue(OPEN)){
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_WEST_RIGHT_AABB);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_WEST_LEFT_AABB);
			}else
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_WEST_AABB);
		}else if(facing==EnumFacing.EAST){
			if(state.getValue(OPEN)){
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_EAST_RIGHT_AABB);
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_EAST_LEFT_AABB);
			}else
				addCollisionBoxToList(pos, entityBox, collidingBoxes, HATCH_EAST_AABB);
		}
	}
	
	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start,
			Vec3d end) {
		EnumFacing facing=blockState.getValue(FACING);
		if(facing==EnumFacing.NORTH){
			if(blockState.getValue(OPEN)){
				RayTraceResult result;
				result=this.rayTrace(pos, start, end, HATCH_NORTH_RIGHT_AABB);
				if(result==null)
					result=this.rayTrace(pos, start, end, HATCH_NORTH_LEFT_AABB);
				return result;
			}else
				return this.rayTrace(pos, start, end, HATCH_NORTH_AABB);
		}else if(facing==EnumFacing.SOUTH){
			if(blockState.getValue(OPEN)){
				RayTraceResult result;
				result=this.rayTrace(pos, start, end, HATCH_SOUTH_RIGHT_AABB);
				if(result==null)
					result=this.rayTrace(pos, start, end, HATCH_SOUTH_LEFT_AABB);
				return result;
			}else
				return this.rayTrace(pos, start, end, HATCH_SOUTH_AABB);
		}else if(facing==EnumFacing.WEST){
			if(blockState.getValue(OPEN)){
				RayTraceResult result;
				result=this.rayTrace(pos, start, end, HATCH_WEST_RIGHT_AABB);
				if(result==null)
					result=this.rayTrace(pos, start, end, HATCH_WEST_LEFT_AABB);
				return result;
			}else
				return this.rayTrace(pos, start, end, HATCH_WEST_AABB);
		}else if(facing==EnumFacing.EAST){
			if(blockState.getValue(OPEN)){
				RayTraceResult result;
				result=this.rayTrace(pos, start, end, HATCH_EAST_RIGHT_AABB);
				if(result==null)
					result=this.rayTrace(pos, start, end, HATCH_EAST_LEFT_AABB);
				return result;
			}else
				return this.rayTrace(pos, start, end, HATCH_EAST_AABB);
		}
		return null;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(OPEN, false).withProperty(ACTIVE, false);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		((TileBloomery)worldIn.getTileEntity(pos)).dropInventory(state.getValue(FACING));
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.isSneaking()){
			if(playerIn.getHeldItem(hand).isEmpty()){
				if(!worldIn.isRemote){
					TileBloomery bloomery=(TileBloomery)worldIn.getTileEntity(pos);
					ItemStack output=OreSmeltingRecipes.BloomeryGetOutput(bloomery);
					if(output.isEmpty()){
						if(bloomery.getOreAmount()==0){
							playerIn.sendMessage(new TextComponentString("Empty"));
						}else{
							playerIn.sendMessage(new TextComponentString(ChatFormatting.DARK_RED+"Invalid"+" ("+bloomery.getOreAmount()+"/"+bloomery.getMaxSpace()*8));
						}
					}else{
						int t=OreSmeltingRecipes.BloomeryGetMaxSpace(bloomery, output);
						if(t>output.getCount()/OreSmeltingRecipes.BloomeryGetRecipeAmount(output)){
							playerIn.sendMessage(new TextComponentString(ChatFormatting.YELLOW+output.getDisplayName()+" x"+output.getCount()+" ("+output.getCount()/OreSmeltingRecipes.BloomeryGetRecipeAmount(output)+"/"+OreSmeltingRecipes.BloomeryGetMaxSpace(bloomery, output)));
						}else{
							playerIn.sendMessage(new TextComponentString(ChatFormatting.GREEN+output.getDisplayName()+" x"+output.getCount()+" ("+output.getCount()/OreSmeltingRecipes.BloomeryGetRecipeAmount(output)+"/"+OreSmeltingRecipes.BloomeryGetMaxSpace(bloomery, output)));
						}
					}
					int f,n;
					f=bloomery.getFuelAmount();
					n=OreSmeltingRecipes.BloomeryGetFuelRequired(bloomery);
					if(f==0){
						if(n==0){
							playerIn.sendMessage(new TextComponentString("No Fuel"));
						}else{
							playerIn.sendMessage(new TextComponentString(ChatFormatting.DARK_RED+"No Fuel (0/"+n+")"));
						}
					}else{
						if(f<n){
							playerIn.sendMessage(new TextComponentString(ChatFormatting.DARK_RED+"Fuel x"+f+" ("+f+"/"+n+")"));
						}else{
							if(f>n){
								playerIn.sendMessage(new TextComponentString(ChatFormatting.YELLOW+"Fuel x"+f+" ("+f+"/"+n+")"));
							}else{
								playerIn.sendMessage(new TextComponentString(ChatFormatting.GREEN+"Fuel x"+f+" ("+f+"/"+n+")"));
							}
						}
					}
				}
				return true;
			}else
				return false;
		}else{
			if(playerIn.getHeldItem(hand).getItem()==Items.FLINT_AND_STEEL){
				if(worldIn.getBlockState(pos).getValue(ACTIVE)==false&&worldIn.getBlockState(pos).getValue(OPEN)==false){
					if(!worldIn.isRemote){
						TileBloomery bloomery=(TileBloomery)worldIn.getTileEntity(pos);
						if((!OreSmeltingRecipes.BloomeryGetOutput(bloomery).isEmpty())&&
								bloomery.getFuelAmount()>=OreSmeltingRecipes.BloomeryGetFuelRequired(bloomery)){
							worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(ACTIVE, true));
							bloomery.ignite();
						}
						playerIn.getHeldItem(hand).damageItem(1, playerIn);
						Random r=new Random();
						worldIn.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, r.nextFloat() * 0.4F + 0.8F);
					}
					return true;
				}else
					return false;
			}else{
				if(state.getValue(ACTIVE)==false){
					if(!worldIn.isRemote){
						worldIn.playSound(playerIn, pos, (state.getValue(OPEN)?SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE:SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), SoundCategory.BLOCKS, 1F, 1F);
						worldIn.setBlockState(pos, state.withProperty(OPEN, !state.getValue(OPEN)));
						return true;
					}else{
						worldIn.playSound(playerIn, pos, (state.getValue(OPEN)?SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE:SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), SoundCategory.BLOCKS, 1F, 1F);
						return true;
					}
				}else
					return false;
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBloomery();
	}

}
