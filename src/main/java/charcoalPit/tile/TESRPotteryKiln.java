package charcoalPit.tile;

import charcoalPit.blocks.BlockPotteryKiln;
import charcoalPit.blocks.BlockPotteryKiln.EnumKilnTypes;
import charcoalPit.blocks.BlocksRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TESRPotteryKiln extends TileEntitySpecialRenderer<TilePotteryKiln>{
	
	public EntityItem item;
	@Override
	public void render(TilePotteryKiln te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.disableRescaleNormal();
		if(te.getWorld().getBlockState(te.getPos()).getBlock()==BlocksRegistry.potteryKiln&&te.getWorld().getBlockState(te.getPos()).getValue(BlockPotteryKiln.TYPE)==EnumKilnTypes.EMPTY){
			ItemStack stack=te.pottery.getStackInSlot(0);
			if(!stack.isEmpty()){
				item=new EntityItem(te.getWorld());
				item.setItem(stack);
				item.hoverStart=0F;
				RenderHelper.enableStandardItemLighting();
				GlStateManager.enableLighting();
				GlStateManager.pushMatrix();
				GlStateManager.translate(0.5D, -0.1D, 0.5D);
				GlStateManager.scale(1D, 1D, 1D);
				Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, false);
				GlStateManager.popMatrix();
			}
		}
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}
}
