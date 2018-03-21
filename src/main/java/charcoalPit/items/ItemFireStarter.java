package charcoalPit.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemFireStarter extends ItemBase{

	public ItemFireStarter() {
		super("fire_starter");
		setCreativeTab(CreativeTabs.TOOLS);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 30;
	}
	
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		RayTraceResult trace=getRayTraceFromEntity(worldIn, playerIn, false, playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue());
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(trace.typeOfHit==RayTraceResult.Type.BLOCK){
			playerIn.setActiveHand(handIn);
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}else{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		RayTraceResult trace=getRayTraceFromEntity(player.world, player, false, player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue());
		if(!player.world.isRemote){
			if(trace.typeOfHit==RayTraceResult.Type.BLOCK){
				if(count==1){
					BlockPos hit=trace.getBlockPos().offset(trace.sideHit);
					if(player.world.getBlockState(hit).getBlock().isReplaceable(player.world, hit)){
						player.world.setBlockState(hit, Blocks.FIRE.getDefaultState());
						player.world.playSound(null, hit, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
						stack.shrink(1);
					}
				}
			}else{
				player.stopActiveHand();
			}
		}else{
			if(trace.typeOfHit==RayTraceResult.Type.BLOCK){
				player.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, trace.hitVec.x, trace.hitVec.y, trace.hitVec.z, 0, 0, 0, new int[0]);
			}
		}
	}
	
	public static RayTraceResult getRayTraceFromEntity(World worldIn, Entity entityIn, boolean useLiquids, double range)
    {
        Vec3d eyesVec = new Vec3d(entityIn.posX, entityIn.posY + entityIn.getEyeHeight(), entityIn.posZ);
        Vec3d rangedLookRot = entityIn.getLook(1f).scale(range);
        Vec3d lookVec = eyesVec.add(rangedLookRot);

        RayTraceResult result = worldIn.rayTraceBlocks(eyesVec, lookVec, useLiquids, false, false);

        if (result == null)
        {
            result = new RayTraceResult(RayTraceResult.Type.MISS, Vec3d.ZERO, EnumFacing.UP, BlockPos.ORIGIN);
        }

        AxisAlignedBB bb = entityIn.getEntityBoundingBox().expand(rangedLookRot.x, rangedLookRot.y, rangedLookRot.z).expand(1d, 1d, 1d);
        List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(entityIn, bb);

        double closest = result.typeOfHit == RayTraceResult.Type.BLOCK ? eyesVec.distanceTo(result.hitVec) : Double.MAX_VALUE;
        RayTraceResult entityTrace = null;
        Entity targetEntity = null;

        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = list.get(i);
            bb = entity.getEntityBoundingBox();
            RayTraceResult traceTmp = bb.calculateIntercept(lookVec, eyesVec);

            if (traceTmp != null)
            {
                double distance = eyesVec.distanceTo(traceTmp.hitVec);

                if (distance <= closest)
                {
                    targetEntity = entity;
                    entityTrace = traceTmp;
                    closest = distance;
                }
            }
        }

        if (targetEntity != null)
        {
            result = new RayTraceResult(targetEntity, entityTrace.hitVec);
        }

        return result;
    }

}
