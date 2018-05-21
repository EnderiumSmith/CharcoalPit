package charcoalPit.core;

import charcoalPit.blocks.BlockPotteryKiln;
import charcoalPit.blocks.BlockPotteryKiln.EnumKilnTypes;
import charcoalPit.crafting.PotteryKilnRecipe;
import charcoalPit.blocks.BlocksRegistry;
import charcoalPit.tile.TilePotteryKiln;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class DispenserPlaceKiln extends BehaviorDefaultDispenseItem{
	
	@Override
	protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		EnumFacing facing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
        IPosition iposition = BlockDispenser.getDispensePosition(source);
        BlockPos pos=source.getBlockPos().offset(facing);
        if(source.getWorld().getBlockState(pos).getBlock().isReplaceable(source.getWorld(), pos)){
        	if(PotteryKilnRecipe.isValidInput(stack)){
        		if(source.getWorld().getBlockState(pos.offset(EnumFacing.DOWN)).isSideSolid(source.getWorld(), pos, EnumFacing.UP)){
        			source.getWorld().setBlockState(pos, BlocksRegistry.potteryKiln.getDefaultState());
        			TilePotteryKiln tile=((TilePotteryKiln)source.getWorld().getTileEntity(pos));
        			ItemStack newStack=tile.pottery.insertItem(0, stack, false);
        			source.getWorld().playSound(null, pos, SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
        			source.getWorld().notifyBlockUpdate(pos, BlocksRegistry.potteryKiln.getDefaultState(), BlocksRegistry.potteryKiln.getDefaultState(), 2);
        			return newStack;
        		}else{
        			ItemStack itemstack = stack.splitStack(1);
        	        doDispense(source.getWorld(), itemstack, 6, facing, iposition);
        	        return stack;
        		}
        	}else{
        		ItemStack itemstack = stack.splitStack(1);
    	        doDispense(source.getWorld(), itemstack, 6, facing, iposition);
    	        return stack;
        	}
        }else if(source.getWorld().getBlockState(pos).getBlock()==BlocksRegistry.potteryKiln){
        	if(source.getWorld().getBlockState(pos).getValue(BlockPotteryKiln.TYPE)==EnumKilnTypes.EMPTY){
        		if(!stack.isEmpty()&&MethodHelper.PotteryKilnIsTatch(stack)&&stack.getCount()>=Config.ThatchAmount){
        			stack.setCount(stack.getCount()-Config.ThatchAmount);
        			source.getWorld().setBlockState(pos, BlocksRegistry.potteryKiln.getDefaultState().withProperty(BlockPotteryKiln.TYPE, EnumKilnTypes.THATCH));
        			source.getWorld().playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1F, 1F);
        			return stack;
        		}else{
        			ItemStack itemstack = stack.splitStack(1);
        	        doDispense(source.getWorld(), itemstack, 6, facing, iposition);
        	        return stack;
        		}
        	}else if(source.getWorld().getBlockState(pos).getValue(BlockPotteryKiln.TYPE)==EnumKilnTypes.THATCH){
        		if(!stack.isEmpty()){
        			int[] ids=OreDictionary.getOreIDs(stack);
    				for(int id:ids){
    					if(OreDictionary.getOreName(id).equals("logWood")&&stack.getCount()>=Config.WoodAmount){
    						stack.setCount(stack.getCount()-Config.WoodAmount);
    						source.getWorld().setBlockState(pos, BlocksRegistry.potteryKiln.getDefaultState().withProperty(BlockPotteryKiln.TYPE, EnumKilnTypes.WOOD));
    						source.getWorld().playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1F, 1F);
    						return stack;
    					}
    				}
    				ItemStack itemstack = stack.splitStack(1);
    		        doDispense(source.getWorld(), itemstack, 6, facing, iposition);
    		        return stack;
    			}else{
    				ItemStack itemstack = stack.splitStack(1);
    		        doDispense(source.getWorld(), itemstack, 6, facing, iposition);
    		        return stack;
    			}
        	}else{
        		ItemStack itemstack = stack.splitStack(1);
    	        doDispense(source.getWorld(), itemstack, 6, facing, iposition);
    	        return stack;
        	}
        }else{
        	ItemStack itemstack = stack.splitStack(1);
	        doDispense(source.getWorld(), itemstack, 6, facing, iposition);
	        return stack;
        }
	}
}
