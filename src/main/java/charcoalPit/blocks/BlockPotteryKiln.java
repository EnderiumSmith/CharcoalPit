package charcoalPit.blocks;

import charcoalPit.core.Config;
import charcoalPit.core.PotteryKilnRecipe;
import charcoalPit.items.ItemsRegistry;
import charcoalPit.tile.TilePotteryKiln;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class BlockPotteryKiln extends BlockBase implements ITileEntityProvider{

	public static final PropertyEnum<EnumKilnTypes> TYPE=PropertyEnum.create("kiln_type", EnumKilnTypes.class);
	public static final AxisAlignedBB AABB_EMPTY=new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	public static final AxisAlignedBB AABB_THATCH=new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 10D/16D, 1.0D);
	
	public BlockPotteryKiln(String name) {
		super(Material.WOOD, name);
		setHarvestLevel("none", 0, this.getDefaultState().withProperty(TYPE, EnumKilnTypes.EMPTY));
		setHarvestLevel("none", 0, this.getDefaultState().withProperty(TYPE, EnumKilnTypes.THATCH));
		setHarvestLevel("axe", 0, this.getDefaultState().withProperty(TYPE, EnumKilnTypes.WOOD));
		setHarvestLevel("axe", 0, this.getDefaultState().withProperty(TYPE, EnumKilnTypes.ACTIVE));
		setHarvestLevel("shovel", 0, this.getDefaultState().withProperty(TYPE, EnumKilnTypes.COMPLETE));
	}
	@Override
	public void register() {
		ForgeRegistries.BLOCKS.register(this);
	}
	@Override
	public void initModel() {
		
	}
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
		switch((EnumKilnTypes)state.getValue(TYPE)){
		case EMPTY:return SoundType.GROUND;
		case THATCH:return SoundType.PLANT;
		case WOOD:return SoundType.WOOD;
		case ACTIVE:return SoundType.WOOD;
		case COMPLETE:return SoundType.SAND;
		default:return SoundType.WOOD;
		}
	}
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		switch((EnumKilnTypes)blockState.getValue(TYPE)){
		case EMPTY:return 0.6F;
		case THATCH:return 0.6F;
		case WOOD:return 2F;
		case ACTIVE:return 2F;
		case COMPLETE:return 0.6F;
		default:return 1F;
		}
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(state.getValue(TYPE)==EnumKilnTypes.EMPTY)
			return AABB_EMPTY;
		if(state.getValue(TYPE)==EnumKilnTypes.THATCH)
			return AABB_THATCH;
		return FULL_BLOCK_AABB;
	}
	@Override
	public boolean isFullBlock(IBlockState state) {
		return state.getValue(TYPE)==EnumKilnTypes.WOOD||state.getValue(TYPE)==EnumKilnTypes.ACTIVE||state.getValue(TYPE)==EnumKilnTypes.COMPLETE;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return state.getValue(TYPE)==EnumKilnTypes.WOOD||state.getValue(TYPE)==EnumKilnTypes.ACTIVE||state.getValue(TYPE)==EnumKilnTypes.COMPLETE;
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		return state.getValue(TYPE)==EnumKilnTypes.WOOD||state.getValue(TYPE)==EnumKilnTypes.ACTIVE||state.getValue(TYPE)==EnumKilnTypes.COMPLETE;
	}
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(TYPE)==EnumKilnTypes.WOOD||state.getValue(TYPE)==EnumKilnTypes.ACTIVE||state.getValue(TYPE)==EnumKilnTypes.COMPLETE;
	}
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if(face==EnumFacing.DOWN){
			return !(state.getValue(TYPE)==EnumKilnTypes.EMPTY);
		}else{
			return state.getValue(TYPE)==EnumKilnTypes.WOOD||state.getValue(TYPE)==EnumKilnTypes.ACTIVE||state.getValue(TYPE)==EnumKilnTypes.COMPLETE;
		}
	}
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{TYPE});
	}
	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch(meta){
		case(0):return getDefaultState().withProperty(TYPE, EnumKilnTypes.EMPTY);
		case(1):return getDefaultState().withProperty(TYPE, EnumKilnTypes.THATCH);
		case(2):return getDefaultState().withProperty(TYPE, EnumKilnTypes.WOOD);
		case(3):return getDefaultState().withProperty(TYPE, EnumKilnTypes.ACTIVE);
		case(4):return getDefaultState().withProperty(TYPE, EnumKilnTypes.COMPLETE);
		default:return getDefaultState().withProperty(TYPE, EnumKilnTypes.EMPTY);
		}
	}
	@Override
	public int getMetaFromState(IBlockState state) {
		switch((EnumKilnTypes)state.getValue(TYPE)){
		case EMPTY:return 0;
		case THATCH:return 1;
		case WOOD:return 2;
		case ACTIVE:return 3;
		case COMPLETE:return 4;
		default:return 0;
		}
	}
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 0;
	}
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(pos.offset(EnumFacing.DOWN).equals(fromPos)&&
				!worldIn.getBlockState(fromPos).isSideSolid(worldIn, fromPos, EnumFacing.UP)){
			worldIn.destroyBlock(pos, true);
			return;
		}
		if(state.getValue(TYPE)==EnumKilnTypes.ACTIVE){
			if(pos.offset(EnumFacing.UP).equals(fromPos)){
				IBlockState fromState=worldIn.getBlockState(fromPos);
				if(fromState.getBlock()==Blocks.FIRE)
					return;
				((TilePotteryKiln)worldIn.getTileEntity(pos)).isValid=false;
			}
			((TilePotteryKiln)worldIn.getTileEntity(pos)).isValid=false;
		}
	}
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if(side==EnumFacing.DOWN){
			return !(base_state.getValue(TYPE)==EnumKilnTypes.EMPTY);
		}else{
			return base_state.getValue(TYPE)==EnumKilnTypes.WOOD||base_state.getValue(TYPE)==EnumKilnTypes.ACTIVE||base_state.getValue(TYPE)==EnumKilnTypes.COMPLETE;
		}
	}
	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return world.getBlockState(pos).getValue(TYPE)==EnumKilnTypes.ACTIVE;
	}
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TilePotteryKiln)worldIn.getTileEntity(pos)).pottery.getStackInSlot(0));
		super.breakBlock(worldIn, pos, state);
	}
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		if(state.getValue(TYPE)==EnumKilnTypes.COMPLETE){
			drops.add(new ItemStack(ItemsRegistry.ash.getItem(), Config.PotteryAsh, ItemsRegistry.ash.getItemDamage()));
		}else if(state.getValue(TYPE)==EnumKilnTypes.WOOD||state.getValue(TYPE)==EnumKilnTypes.ACTIVE){
			drops.add(new ItemStack(ItemsRegistry.wood.getItem(), Config.WoodAmount, ItemsRegistry.wood.getItemDamage()));
			drops.add(new ItemStack(ItemsRegistry.thatch.getItem(), Config.ThatchAmount, ItemsRegistry.thatch.getItemDamage()));
		}else if(state.getValue(TYPE)==EnumKilnTypes.THATCH){
			drops.add(new ItemStack(ItemsRegistry.thatch.getItem(), Config.ThatchAmount, ItemsRegistry.thatch.getItemDamage()));
		}
	}
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		switch(state.getValue(TYPE)){
		case EMPTY:{
			if(playerIn.getHeldItem(hand).isEmpty()){
				if(worldIn.isRemote){
					return true;
				}else{
					TilePotteryKiln tile=((TilePotteryKiln)worldIn.getTileEntity(pos));
					playerIn.setHeldItem(hand, tile.pottery.extractItem(0, 8, false));
					worldIn.destroyBlock(pos, true);
					return true;
				}
			}else{
				if(PotteryKilnRecipe.isValidInput(playerIn.getHeldItem(hand))){
					if(worldIn.isRemote){
						return true;
					}else{
						TilePotteryKiln tile=((TilePotteryKiln)worldIn.getTileEntity(pos));
						playerIn.setHeldItem(hand, tile.pottery.insertItem(0, playerIn.getHeldItem(hand), false));
						worldIn.notifyBlockUpdate(pos, state, state, 2);
						return true;
					}
				}else{
					if(!playerIn.getHeldItem(hand).isEmpty()&&ItemStack.areItemsEqual(playerIn.getHeldItem(hand),ItemsRegistry.thatch)&&playerIn.getHeldItem(hand).getCount()>=Config.ThatchAmount){
						if(worldIn.isRemote){
							return true;
						}else{
							playerIn.getHeldItem(hand).setCount(playerIn.getHeldItem(hand).getCount()-Config.ThatchAmount);
							worldIn.setBlockState(pos, this.getDefaultState().withProperty(TYPE, EnumKilnTypes.THATCH));
							worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1F, 1F);
							return true;
						}
					}else
						return false;
				}
			}
		}
		case THATCH:{
			if(!playerIn.getHeldItem(hand).isEmpty()){
				int[] ids=OreDictionary.getOreIDs(playerIn.getHeldItem(hand));
				for(int id:ids){
					if(OreDictionary.getOreName(id).equals("logWood")&&playerIn.getHeldItem(hand).getCount()>=Config.WoodAmount){
						if(worldIn.isRemote){
							return true;
						}else{
							playerIn.getHeldItem(hand).setCount(playerIn.getHeldItem(hand).getCount()-Config.WoodAmount);
							worldIn.setBlockState(pos, this.getDefaultState().withProperty(TYPE, EnumKilnTypes.WOOD));
							worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1F, 1F);
							return true;
						}
					}
				}
				return false;
			}else
				return false;
		}
		default:{
			return false;
		}
		}
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePotteryKiln();
	}
	
	public enum EnumKilnTypes implements IStringSerializable{
		//stage 1
		EMPTY("empty"),
		//stage 2
		THATCH("thatch"),
		//stage 3
		WOOD("wood"),
		//stage 4
		ACTIVE("active"),
		//complete
		COMPLETE("complete");

		private String name;
		private EnumKilnTypes(String id) {
			name=id;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
}
