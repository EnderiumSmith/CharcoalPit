package charcoalPit.core;

import charcoalPit.blocks.BlocksRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PileIgnitr {
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void checkIgnition(NeighborNotifyEvent event){
		if(!event.isCanceled()&&
				event.getState().getBlock()==Blocks.FIRE){
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
}
