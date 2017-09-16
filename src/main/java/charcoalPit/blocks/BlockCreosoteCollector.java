package charcoalPit.blocks;

import java.util.List;

import charcoalPit.tile.TileCreosoteCollector;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockCreosoteCollector extends BlockBase implements ITileEntityProvider{

	public final Boolean refractory;
	public BlockCreosoteCollector(String name,boolean fire) {
		super(Material.ROCK, name);
		refractory=fire;
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(CreativeTabs.MISC);
		setHardness(2);
	}
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(GuiScreen.isShiftKeyDown()){
			tooltip.add("Collects creosote oil produced by log/coal piles above");
			tooltip.add("Collection area is a 9x9 '+' shape");
			tooltip.add("Piles need to be connected to funnel");
			tooltip.add("Creosote oil only flows down between piles");
			tooltip.add("If redstone signal is applied:");
			tooltip.add("-Funnel will also collect from neighboring funnels");
			tooltip.add("-Funnel will auto output creosote oil");
			tooltip.add("Creosote oil can only be extracted from the bottom");
			tooltip.add("A line of funnels works best");
		}else{
			tooltip.add("<Hold Shift>");
		}
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCreosoteCollector();
	}

}
