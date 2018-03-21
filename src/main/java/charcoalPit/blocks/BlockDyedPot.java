package charcoalPit.blocks;

import net.minecraft.item.EnumDyeColor;

public class BlockDyedPot extends BlockCeramicPot{

	public BlockDyedPot(String name, EnumDyeColor color) {
		super(color.getName()+"_"+name);
	}
}
