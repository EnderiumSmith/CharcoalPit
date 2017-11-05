package charcoalPit.core;

import charcoalPit.blocks.BlockPotteryKiln;
import charcoalPit.blocks.BlockPotteryKiln.EnumKilnTypes;
import charcoalPit.tile.TilePotteryKiln;
import charcoalPit.blocks.BlocksRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PileIgnitr {
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void checkIgnition(NeighborNotifyEvent event){
		if(!event.isCanceled()&&
				event.getWorld().getBlockState(event.getPos()).getBlock()==Blocks.FIRE){
			for(EnumFacing facing:event.getNotifiedSides()){
				BlockPos pos=event.getPos().offset(facing);
				if(event.getWorld().getBlockState(pos).getBlock()==BlocksRegistry.LogPile){
					//found log pile to ignite
					igniteLogs(event.getWorld(),pos);
					
				}else if(event.getWorld().getBlockState(pos).getBlock()==Blocks.COAL_BLOCK){
					boolean shouldIgnite=false;
					for(int x=-1;x<=1;x++){
						for(int y=-1;y<=1;y++){
							for(int z=-1;z<=1&&!shouldIgnite;z++){
								BlockPos newpos=pos.add(x, y, z);
								for(String name:Config.CokeBlocks){
									if(event.getWorld().getBlockState(newpos).getBlock().getRegistryName().toString().equals(name)){
										shouldIgnite=true;
										break;
									}
								}
							}
						}
					}
					if(shouldIgnite){
						//found coal pile to ignite
						igniteCoal(event.getWorld(),pos);
					}
				}else if(facing==EnumFacing.DOWN&&event.getWorld().getBlockState(pos).getBlock()==BlocksRegistry.PotteryKiln){
					//found pottery kiln to ignite
					ignitePottery(event.getWorld(), pos);
				}
			}
		}
	}
	public void igniteLogs(World world, BlockPos pos){
		if(world.getBlockState(pos).getBlock()==BlocksRegistry.LogPile){
			world.setBlockState(pos, BlocksRegistry.ActiveLogPile.getDefaultState());
			EnumFacing[] neighbors=EnumFacing.VALUES;
			for(int i=0;i<neighbors.length;i++){
				igniteLogs(world, pos.offset(neighbors[i]));
			}
		}
	}
	public void igniteCoal(World world, BlockPos pos){
		if(world.getBlockState(pos).getBlock()==Blocks.COAL_BLOCK){
			world.setBlockState(pos, BlocksRegistry.ActiveCoalPile.getDefaultState());
			EnumFacing[] neighbors=EnumFacing.VALUES;
			for(int i=0;i<neighbors.length;i++){
				igniteCoal(world, pos.offset(neighbors[i]));
			}
		}
	}
	public void ignitePottery(World world, BlockPos pos){
		if(world.getBlockState(pos).getBlock()==BlocksRegistry.PotteryKiln&&
				((EnumKilnTypes)world.getBlockState(pos).getValue(BlockPotteryKiln.TYPE))==EnumKilnTypes.WOOD){
			world.setBlockState(pos, BlocksRegistry.PotteryKiln.getDefaultState().withProperty(BlockPotteryKiln.TYPE, EnumKilnTypes.ACTIVE));
			((TilePotteryKiln)world.getTileEntity(pos)).setActive(true);
			for(EnumFacing facing:EnumFacing.HORIZONTALS){
				ignitePottery(world, pos.offset(facing));
			}
		}
	}
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void placeKiln(PlayerInteractEvent.RightClickBlock event){
		if(event.getWorld().getBlockState(event.getPos()).getBlock()==BlocksRegistry.PotteryKiln){
			event.setUseBlock(Result.ALLOW);
		}else if(!event.isCanceled()&&event.getEntityPlayer().isSneaking()&&PotteryKilnRecipe.isValidInput(event.getItemStack())){
			if(event.getFace()==EnumFacing.UP&&event.getWorld().getBlockState(event.getPos()).isSideSolid(event.getWorld(), event.getPos(), EnumFacing.UP)&&
					event.getWorld().getBlockState(event.getPos().offset(EnumFacing.UP)).getBlock().isReplaceable(event.getWorld(), event.getPos().offset(EnumFacing.UP))){
				if(!event.getWorld().isRemote){
					event.getWorld().setBlockState(event.getPos().offset(EnumFacing.UP), BlocksRegistry.PotteryKiln.getDefaultState());
					TilePotteryKiln tile=((TilePotteryKiln)event.getWorld().getTileEntity(event.getPos().offset(EnumFacing.UP)));
					event.getEntityPlayer().setHeldItem(event.getHand(), tile.pottery.insertItem(0, event.getItemStack(), false));
					event.getWorld().playSound(null, event.getPos(), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
					event.getWorld().notifyBlockUpdate(event.getPos().offset(EnumFacing.UP), BlocksRegistry.PotteryKiln.getDefaultState(), BlocksRegistry.PotteryKiln.getDefaultState(), 2);
				}
				event.setUseBlock(Result.DENY);
				event.setUseItem(Result.DENY);
			}
		}
	}
}
