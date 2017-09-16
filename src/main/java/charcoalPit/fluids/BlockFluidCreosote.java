package charcoalPit.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidCreosote extends BlockFluidClassic{

	public BlockFluidCreosote() {
		super(FluidsRegistry.Creosote, Material.WATER);
		setRegistryName("block_fluid_creosote");
		Blocks.FIRE.setFireInfo(this, 100, 5);
	}

}
