package charcoalPit.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item{
	
	public ItemBase(String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(CreativeTabs.MATERIALS);
	}
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	public void register(){
		ForgeRegistries.ITEMS.register(this);
	}
}
