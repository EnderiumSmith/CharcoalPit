package charcoalPit.items;

import charcoalPit.core.MethodHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockBloomery extends ItemBlockBase{

	public ItemBlockBloomery(Block block) {
		super(block);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, (Entity)null))
        {
            EnumFacing[] neighbors=new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, player.getHorizontalFacing().rotateY(), player.getHorizontalFacing().rotateYCCW()};
            boolean ok=true;
            for(EnumFacing neighbor:neighbors){
            	if(!MethodHelper.BloomeryIsValidBlock(worldIn, pos, neighbor))
            		ok=false;
            }
            
            if(ok){
	        	int i = this.getMetadata(itemstack.getMetadata());
	            IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);
	
	            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
	            {
	                iblockstate1 = worldIn.getBlockState(pos);
	                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
	                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
	                itemstack.shrink(1);
	            }
	
	            return EnumActionResult.SUCCESS;
            }
            else{
            	return EnumActionResult.FAIL;
            }
        }
        else
        {
            return EnumActionResult.FAIL;
        }
	}

}
