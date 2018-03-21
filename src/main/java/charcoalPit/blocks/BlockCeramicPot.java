package charcoalPit.blocks;

import java.util.List;

import charcoalPit.CharcoalPit;
import charcoalPit.tile.TileCeramicPot;
import net.minecraft.block.ITileEntityProvider;
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
import net.minecraftforge.items.ItemStackHandler;

public class BlockCeramicPot extends BlockBase implements ITileEntityProvider{
	
	public static final AxisAlignedBB AABB_POT=new AxisAlignedBB(2D/16D, 0D, 2D/16D, 14D/16D, 1D, 14D/16D);

	public BlockCeramicPot(String name) {
		super(Material.ROCK, name);
		setHardness(1.25F);
		setCreativeTab(CreativeTabs.DECORATIONS);
		setHarvestLevel("pickaxe", 0);
		setResistance(7F);
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
        if (tileentity instanceof TileCeramicPot)
        {
            ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("items", ((TileCeramicPot)tileentity).items.serializeNBT());
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
				((TileCeramicPot)worldIn.getTileEntity(pos)).items.deserializeNBT(stacktag.getCompoundTag("items"));
				//worldIn.updateComparatorOutputLevel(pos, state.getBlock());
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		NBTTagCompound nbttagcompound = stack.getTagCompound();
        if (nbttagcompound != null && nbttagcompound.hasKey("items"))
        {
            ItemStackHandler items=new ItemStackHandler(9);
            items.deserializeNBT(nbttagcompound.getCompoundTag("items"));
            int i = 0;
            for (int k=0;k<items.getSlots();k++)
            {
                ItemStack itemstack=items.getStackInSlot(i);
            	if (!itemstack.isEmpty())
                {
                    if (i <= 9)
                    {
                        ++i;
                        tooltip.add(String.format("%s x%d", new Object[] {itemstack.getDisplayName(), Integer.valueOf(itemstack.getCount())}));
                    }
                }
            }
        }
	}
	
	/*@Override
	public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
    	TileCeramicPot tile=(TileCeramicPot)worldIn.getTileEntity(pos);
        int i = 0;
        float f = 0.0F;
        for (int j = 0; j < tile.items.getSlots(); ++j)
        {
            ItemStack itemstack = tile.items.getStackInSlot(j);
            if (!itemstack.isEmpty())
            {
                f += (float)itemstack.getCount() / (float)Math.min(tile.items.getSlotLimit(j), itemstack.getMaxStackSize());
                ++i;
            }
        }
        f = f / (float)tile.items.getSlots();
        return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
    }*/
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote){
			return true;
		}else{
			playerIn.openGui(CharcoalPit.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCeramicPot();
	}
	
	
}
