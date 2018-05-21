package charcoalPit.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBronzeReinforcedBrick extends BlockBase{

	public BlockBronzeReinforcedBrick() {
		super(Material.ROCK, "bronze_reinforced_brick");
		setHardness(20);
		setResistance(20);
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		tooltip.add("Creeper Proof");
	}

}
