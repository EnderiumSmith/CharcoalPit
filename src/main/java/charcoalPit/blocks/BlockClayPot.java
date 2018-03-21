package charcoalPit.blocks;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import charcoalPit.CharcoalPit;
import charcoalPit.crafting.OreSmeltingRecipes;
import charcoalPit.tile.TileClayPot;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockClayPot extends BlockBase implements ITileEntityProvider{
	
	public static final AxisAlignedBB AABB_POT=new AxisAlignedBB(2D/16D, 0D, 2D/16D, 14D/16D, 1D, 14D/16D);

	public BlockClayPot() {
		super(Material.CLAY, "clay_pot");
		setSoundType(SoundType.GROUND);
		setHardness(0.6F);
		setResistance(1F);
		setHarvestLevel("shovel", 0);
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
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileClayPot)
        {
            ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("items", ((TileClayPot)tileentity).items.serializeNBT());
            itemstack.setTagCompound(nbttagcompound);
            spawnAsEntity(worldIn, pos, itemstack);
            //worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        }

        super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(stack.hasTagCompound()){
			NBTTagCompound stacktag=stack.getTagCompound();
			if(stacktag.hasKey("items")){
				((TileClayPot)worldIn.getTileEntity(pos)).items.deserializeNBT(stacktag.getCompoundTag("items"));
				//worldIn.updateComparatorOutputLevel(pos, state.getBlock());
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		if(stack.hasTagCompound()){
			NBTTagCompound stacktag=stack.getTagCompound();
			if(stacktag.hasKey("items")){
				ItemStack output=OreSmeltingRecipes.oreKilnGetOutput(stacktag);
				if(output.isEmpty()){
					if(OreSmeltingRecipes.oreKilnIsEmpty(stacktag)){
						tooltip.add("Empty");
					}else{
						tooltip.add(ChatFormatting.DARK_RED+"Invalid"+" ("+OreSmeltingRecipes.oreKilnGetOreAmount(stacktag)+"/9)");
					}
				}else{
					int t=OreSmeltingRecipes.oreKilnGetMaxSpace(output);
					if(t>output.getCount()/OreSmeltingRecipes.oreKilnGetRecipeAmount(output)){
						tooltip.add(ChatFormatting.YELLOW+output.getDisplayName()+" x"+output.getCount()+" ("+output.getCount()/OreSmeltingRecipes.oreKilnGetRecipeAmount(output)+"/"+OreSmeltingRecipes.oreKilnGetMaxSpace(output)+")");
					}else{
						tooltip.add(ChatFormatting.GREEN+output.getDisplayName()+" x"+output.getCount()+" ("+output.getCount()/OreSmeltingRecipes.oreKilnGetRecipeAmount(output)+"/"+OreSmeltingRecipes.oreKilnGetMaxSpace(output)+")");
					}
				}
				int f,n;
				f=OreSmeltingRecipes.oreKilnGetFuelAvailable(stacktag);
				n=OreSmeltingRecipes.oreKilnGetFuelRequired(stacktag);
				if(f==0){
					if(n==0){
						tooltip.add("No Fuel");
					}else{
						tooltip.add(ChatFormatting.DARK_RED+"No Fuel (0/"+n+")");
					}
				}else{
					if(f<n){
						tooltip.add(ChatFormatting.DARK_RED+"Fuel x"+f+" ("+f+"/"+n+")");
					}else{
						if(f>n){
							tooltip.add(ChatFormatting.YELLOW+"Fuel x"+f+" ("+f+"/"+n+")");
						}else{
							tooltip.add(ChatFormatting.GREEN+"Fuel x"+f+" ("+f+"/"+n+")");
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote){
			return true;
		}else{
			playerIn.openGui(CharcoalPit.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileClayPot();
	}
	
}
