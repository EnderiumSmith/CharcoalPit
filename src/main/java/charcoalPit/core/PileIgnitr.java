package charcoalPit.core;

import java.util.Iterator;
import java.util.Map;

import charcoalPit.blocks.BlockBloomeryHatch;
import charcoalPit.blocks.BlockDyedPot;
import charcoalPit.blocks.BlockPotteryKiln;
import charcoalPit.blocks.BlockPotteryKiln.EnumKilnTypes;
import charcoalPit.crafting.OreSmeltingRecipes;
import charcoalPit.crafting.PotteryKilnRecipe;
import charcoalPit.items.ItemBlockBase;
import charcoalPit.items.ItemsRegistry;
import charcoalPit.tile.TileBloomery;
import charcoalPit.tile.TilePotteryKiln;
import charcoalPit.blocks.BlocksRegistry;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class PileIgnitr {
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void checkIgnition(NeighborNotifyEvent event){
		if(!event.isCanceled()&&
				event.getWorld().getBlockState(event.getPos()).getBlock()==Blocks.FIRE){
			for(EnumFacing facing:event.getNotifiedSides()){
				BlockPos pos=event.getPos().offset(facing);
				if(event.getWorld().getBlockState(pos).getBlock()==BlocksRegistry.logPile){
					//found log pile to ignite
					igniteLogs(event.getWorld(),pos);
					
				}else if(event.getWorld().getBlockState(pos).getBlock()==Blocks.COAL_BLOCK){
					boolean shouldIgnite=false;
					for(int x=-1;x<=1;x++){
						for(int y=-1;y<=1;y++){
							for(int z=-1;z<=1&&!shouldIgnite;z++){
								BlockPos newpos=pos.add(x, y, z);
								if(MethodHelper.CokeOvenIsValidBlock(event.getWorld().getBlockState(newpos))){
									shouldIgnite=true;
									break;
								}
							}
						}
					}
					if(shouldIgnite){
						//found coal pile to ignite
						igniteCoal(event.getWorld(),pos);
					}
				}else if(facing==EnumFacing.DOWN&&event.getWorld().getBlockState(pos).getBlock()==BlocksRegistry.potteryKiln){
					//found pottery kiln to ignite
					ignitePottery(event.getWorld(), pos);
				}else if(event.getWorld().getBlockState(pos).getBlock()==BlocksRegistry.hatch){
					//found bloomery to ignite
					igniteBloomery(event.getWorld(), pos);
				}
			}
		}
	}
	public void igniteLogs(World world, BlockPos pos){
		if(world.getBlockState(pos).getBlock()==BlocksRegistry.logPile){
			world.setBlockState(pos, BlocksRegistry.activeLogPile.getDefaultState());
			EnumFacing[] neighbors=EnumFacing.VALUES;
			for(int i=0;i<neighbors.length;i++){
				igniteLogs(world, pos.offset(neighbors[i]));
			}
		}
	}
	public void igniteCoal(World world, BlockPos pos){
		if(world.getBlockState(pos).getBlock()==Blocks.COAL_BLOCK){
			world.setBlockState(pos, BlocksRegistry.activeCoalPile.getDefaultState());
			EnumFacing[] neighbors=EnumFacing.VALUES;
			for(int i=0;i<neighbors.length;i++){
				igniteCoal(world, pos.offset(neighbors[i]));
			}
		}
	}
	public void ignitePottery(World world, BlockPos pos){
		if(world.getBlockState(pos).getBlock()==BlocksRegistry.potteryKiln&&
				((EnumKilnTypes)world.getBlockState(pos).getValue(BlockPotteryKiln.TYPE))==EnumKilnTypes.WOOD){
			world.setBlockState(pos, BlocksRegistry.potteryKiln.getDefaultState().withProperty(BlockPotteryKiln.TYPE, EnumKilnTypes.ACTIVE));
			((TilePotteryKiln)world.getTileEntity(pos)).setActive(true);
			for(int x=-1;x<2;x++){
				for(int z=-1;z<2;z++){
					ignitePottery(world, new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z));
				}
			}
		}
	}
	public void igniteBloomery(World world, BlockPos pos){
		TileEntity tile=world.getTileEntity(pos);
		if(tile instanceof TileBloomery){
			TileBloomery bloomery=(TileBloomery)tile;
			if(world.getBlockState(pos).getValue(BlockBloomeryHatch.ACTIVE)==false&&
					world.getBlockState(pos).getValue(BlockBloomeryHatch.OPEN)==false&&
					!OreSmeltingRecipes.BloomeryGetOutput(bloomery).isEmpty()&&
					bloomery.getFuelAmount()>=OreSmeltingRecipes.BloomeryGetFuelRequired(bloomery)){
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockBloomeryHatch.ACTIVE, true));
				bloomery.ignite();
			}
		}
	}
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void placeKiln(PlayerInteractEvent.RightClickBlock event){
		if(event.getWorld().getBlockState(event.getPos()).getBlock()==BlocksRegistry.potteryKiln){
			event.setUseBlock(Result.ALLOW);
		}else if(!event.isCanceled()&&event.getEntityPlayer().isSneaking()&&PotteryKilnRecipe.isValidInput(event.getItemStack())){
			if(event.getFace()==EnumFacing.UP&&event.getWorld().getBlockState(event.getPos()).isSideSolid(event.getWorld(), event.getPos(), EnumFacing.UP)&&
					event.getWorld().getBlockState(event.getPos().offset(EnumFacing.UP)).getBlock().isReplaceable(event.getWorld(), event.getPos().offset(EnumFacing.UP))){
				if(!event.getWorld().isRemote){
					event.getWorld().setBlockState(event.getPos().offset(EnumFacing.UP), BlocksRegistry.potteryKiln.getDefaultState());
					TilePotteryKiln tile=((TilePotteryKiln)event.getWorld().getTileEntity(event.getPos().offset(EnumFacing.UP)));
					event.getEntityPlayer().setHeldItem(event.getHand(), tile.pottery.insertItem(0, event.getItemStack(), false));
					event.getWorld().playSound(null, event.getPos(), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
					event.getWorld().notifyBlockUpdate(event.getPos().offset(EnumFacing.UP), BlocksRegistry.potteryKiln.getDefaultState(), BlocksRegistry.potteryKiln.getDefaultState(), 2);
				}
				event.setUseBlock(Result.DENY);
				event.setUseItem(Result.DENY);
			}
		}
	}
	@SubscribeEvent
	public void getStraw(HarvestDropsEvent event){
		if(event.getHarvester()!=null){
			if(event.getHarvester().getHeldItemMainhand().getItem() instanceof ItemHoe||
					MethodHelper.isHoe(event.getHarvester().getHeldItemMainhand())){
				if(!event.isSilkTouching()){
					if(event.getState().getBlock()==Blocks.TALLGRASS){
						event.getDrops().add(new ItemStack(ItemsRegistry.straw));
						event.getHarvester().getHeldItemMainhand().damageItem(1, event.getHarvester());
					}else{
						if(event.getState().getBlock()==Blocks.DOUBLE_PLANT&&
								(event.getState().getValue(BlockDoublePlant.VARIANT)==EnumPlantType.GRASS||
								event.getState().getValue(BlockDoublePlant.VARIANT)==EnumPlantType.FERN)){
							event.getDrops().add(new ItemStack(ItemsRegistry.straw, 2));
							event.getHarvester().getHeldItemMainhand().damageItem(1, event.getHarvester());
						}
					}
				}
			}
		}
	}
	@SubscribeEvent
	public void savePotInvenory(ItemCraftedEvent event){
		if(event.crafting.getItem() instanceof ItemBlockBase&&((ItemBlockBase)event.crafting.getItem()).getBlock() instanceof BlockDyedPot){
			for(int i=0;i<event.craftMatrix.getSizeInventory();i++){
				if(event.craftMatrix.getStackInSlot(i).getItem()==ItemsRegistry.ceramicPot){
					event.crafting.setTagCompound(event.craftMatrix.getStackInSlot(i).getTagCompound());
					return;
				}
			}
		}
	}
	@SubscribeEvent
	public void removeSmelting(OreRegisterEvent event){
		if(Config.DisableFurnaceCharcoal&&event.getName().equals("logWood")){
			Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
			for (Iterator<Map.Entry<ItemStack,ItemStack>> entries = recipes.entrySet().iterator(); entries.hasNext(); ){
				Map.Entry<ItemStack,ItemStack> entry = entries.next();
				ItemStack result = entry.getValue();
				ItemStack input = entry.getKey();
				if(input.isEmpty())
					continue;
				int[] ids=OreDictionary.getOreIDs(input);
				for(int id:ids){
					if(OreDictionary.getOreName(id).equals("logWood")&&ItemStack.areItemsEqual(result, CommonProxy.charcoal)){
						entries.remove();
						break;
					}
				}
			}
		}
		if(Config.DisableFurnaceOre&&event.getName().startsWith("ore")){
			Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
			for (Iterator<Map.Entry<ItemStack,ItemStack>> entries = recipes.entrySet().iterator(); entries.hasNext(); ){
				Map.Entry<ItemStack,ItemStack> entry = entries.next();
				ItemStack result = entry.getValue();
				ItemStack input = entry.getKey();
				if(input.isEmpty())
					continue;
				int[] ids=OreDictionary.getOreIDs(input);
				for(int id:ids){
					if(OreDictionary.getOreName(id).startsWith("ore")){
						int[] ids2=OreDictionary.getOreIDs(result);
						boolean ok=false;
						for(int id2:ids2){
							if(OreDictionary.getOreName(id2).startsWith("ingot")){
								entries.remove();
								ok=true;
								break;
							}
						}
						if(ok){
							break;
						}
					}
				}
			}
		}
	}
}
