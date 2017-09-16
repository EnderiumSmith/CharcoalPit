package charcoalPit.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockBase extends Block{

	public BlockBase(Material materialIn,String name) {
		super(materialIn);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
	}
	public void register(){
		ForgeRegistries.BLOCKS.register(this);
		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(getRegistryName()));
	}
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}

}
