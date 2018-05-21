package charcoalPit.core;

import charcoalPit.blocks.BlocksRegistry;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.BlockDoor.EnumHingePosition;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTrapDoor.DoorHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class MethodHelper {
	
	public static boolean CharcoalPitIsValidBlock(World world,BlockPos charPos,EnumFacing facing, boolean isCoke){
		IBlockState block=world.getBlockState(charPos.offset(facing));
		if(isCoke&&!CokeOvenIsValidBlock(block))
			return false;
		if(block.getBlock().isFlammable(world, charPos.offset(facing), facing.getOpposite()))
			return false;
		if(block.getBlock()==Blocks.IRON_DOOR||block.getBlock()==Blocks.IRON_TRAPDOOR){
			return block.getBlock()==Blocks.IRON_DOOR?isDoorFacingBlock(world, charPos, facing):isTrapDoorFacingBlock(world, charPos, facing);
		}else{
			return block.isSideSolid(world, charPos.offset(facing), facing.getOpposite());
		}
	}
	public static boolean CokeOvenIsValidBlock(IBlockState block){
		if(block.getBlock()==BlocksRegistry.activeCoalPile||block.getBlock()==BlocksRegistry.brickCollector||
				block.getBlock()==BlocksRegistry.netherCollector||block.getBlock()==BlocksRegistry.cokePile){
			return true;
		}else{
			for(String name:Config.CokeBlocks){
				if(block.getBlock().getRegistryName().toString().equals(name)){
					return true;
				}
			}
			for(int i=0;i<Config.CokeBlocksMeta.length;i=i+2){
				String name=Config.CokeBlocksMeta[i];
				String meta=Config.CokeBlocksMeta[i+1];
				int m=0;
				if(meta.equals("*"))
					m=OreDictionary.WILDCARD_VALUE;
				else
					m=Integer.parseInt(meta);
				if(block.getBlock().getRegistryName().toString().equals(name)&&
						(m==OreDictionary.WILDCARD_VALUE||
								block.getBlock().getMetaFromState(block)==m)){
					return true;
				}
			}
			return false;
		}
	}
	public static boolean isDoorFacingBlock(World world, BlockPos charPos,EnumFacing facing){
		IBlockState block=world.getBlockState(charPos.offset(facing));
		if(block.getValue(BlockDoor.HALF)==EnumDoorHalf.UPPER)
			return isDoorFacingBlock(world, charPos.offset(EnumFacing.DOWN), facing);
		EnumFacing doorFacing=block.getValue(BlockDoor.FACING);
		EnumHingePosition doorHinge=block.getValue(BlockDoor.HINGE);
		boolean open=block.getValue(BlockDoor.OPEN);
		if(facing==EnumFacing.UP||facing==EnumFacing.DOWN)
			return false;
		if(open){
			EnumFacing openFacing;
			if(doorHinge==EnumHingePosition.RIGHT){
				openFacing=doorFacing.rotateY();
			}else{
				openFacing=doorFacing.rotateYCCW();
			}
			return openFacing==facing.getOpposite();
		}else{
			return doorFacing==facing;
		}
	}
	public static boolean isTrapDoorFacingBlock(World world, BlockPos charPos,EnumFacing facing){
		IBlockState block=world.getBlockState(charPos.offset(facing));
		EnumFacing doorFacing=block.getValue(BlockTrapDoor.FACING);
		DoorHalf doorHinge=block.getValue(BlockTrapDoor.HALF);
		boolean open=block.getValue(BlockTrapDoor.OPEN);
		if(open){
			if(facing==EnumFacing.UP||facing==EnumFacing.DOWN)
				return false;
			return doorFacing==facing;
		}else{
			if(facing==EnumFacing.UP)
				return doorHinge==DoorHalf.BOTTOM;
			if(facing==EnumFacing.DOWN)
				return doorHinge==DoorHalf.TOP;
			return false;
		}
	}
	public static boolean PotteryKilnIsTatch(ItemStack stack){
		for(int i=0;i<Config.ThatchIDs.length;i=i+2){
			if(stack.isItemEqual(new ItemStack(Item.getByNameOrId(Config.ThatchIDs[i]),1,Integer.parseInt(Config.ThatchIDs[i+1])))){
				return true;
			}
		}
		return false;
	}
	public static boolean BloomeryIsValidBlock(World world,BlockPos bloomPos,EnumFacing facing){
		IBlockState block=world.getBlockState(bloomPos.offset(facing));
		if(block.getBlock().isFlammable(world, bloomPos.offset(facing), facing.getOpposite()))
			return false;
		if(!block.isSideSolid(world, bloomPos.offset(facing), facing.getOpposite())){
			return false;
		}
		for(int i=0;i<Config.BloomeryBlocks.length;i=i+2){
			String name=Config.BloomeryBlocks[i];
			String meta=Config.BloomeryBlocks[i+1];
			int m=0;
			if(meta.equals("*"))
				m=OreDictionary.WILDCARD_VALUE;
			else
				m=Integer.parseInt(meta);
			if(block.getBlock().getRegistryName().toString().equals(name)&&
					(m==OreDictionary.WILDCARD_VALUE||
							block.getBlock().getMetaFromState(block)==m)){
				return true;
			}
		}
		return false;
	}
	public static boolean isItemBlackListed(ItemStack stack){
		for(int i=0;i<Config.CeramicBlackList.length;i++){
			if(stack.getItem()==Item.getByNameOrId(Config.CeramicBlackList[i])){
				return true;
			}
		}
		return false;
	}
	public static boolean isHoe(ItemStack stack){
		for(int i=0;i<Config.HoeList.length;i++){
			Item item=Item.getByNameOrId(Config.HoeList[i]);
			if(stack.getItem()==item)
				return true;
		}
		return false;
	}
	
}
