package charcoalPit.core;

import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.tile.TileCeramicPot;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class DispenserPlacePot extends BehaviorDefaultDispenseItem{
	
	@Override
	protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		EnumFacing facing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
        BlockPos pos=source.getBlockPos().offset(facing);
        if(source.getWorld().getBlockState(pos).getBlock().isReplaceable(source.getWorld(), pos)){
        	source.getWorld().setBlockState(pos, BlocksRegistry.ceramicPot.getDefaultState());
        	if(stack.hasTagCompound()){
    			NBTTagCompound stacktag=stack.getTagCompound();
    			if(stacktag.hasKey("items")){
    				((TileCeramicPot)source.getWorld().getTileEntity(pos)).items.deserializeNBT(stacktag.getCompoundTag("items"));
    				//worldIn.updateComparatorOutputLevel(pos, state.getBlock());
    			}
    		}
        	source.getWorld().playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1F, 1F);
        	stack.splitStack(1);
        	return stack;
        }else{
	        return stack;
        }
	}
	
}
