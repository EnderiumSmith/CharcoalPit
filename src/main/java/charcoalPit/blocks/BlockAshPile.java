package charcoalPit.blocks;

import java.util.List;
import java.util.Random;

import charcoalPit.core.Config;
import charcoalPit.items.ItemsRegistry;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockAshPile extends BlockBase{

	public final Boolean isCoke;
	public BlockAshPile(String name,Boolean coke) {
		super(Material.GROUND, name);
		isCoke=coke;
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setHardness(0.6F);
		setHarvestLevel("shovel", 0);
		setSoundType(SoundType.SAND);
	}
	@Override
	public void register() {
		ForgeRegistries.BLOCKS.register(this);
	}
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		 List<ItemStack> ret = new java.util.ArrayList<ItemStack>();

	        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

	        int count = quantityDropped(state, fortune, rand);
	        for(int i = 0; i < count; i++)
	        {
	            Item item = this.getItemDropped(state, rand, fortune);
	            if (item != null)
	            {
	                ret.add(new ItemStack(item, 1, this.damageDropped(state)));
	            }
	        }
	        for(int i=0;i<(isCoke?Config.CokeTotal:Config.CharcoalTotal)-count;i++){
	        	ret.add(ItemsRegistry.ash_stack.copy());
	        }
	        return ret;
	}
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return isCoke?ItemsRegistry.coke_stack.getItem():Items.COAL;
	}
	@Override
	public int damageDropped(IBlockState state) {
		return isCoke?ItemsRegistry.coke_stack.getItemDamage():1;
	}
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return isCoke?Math.min(Config.CokeTotal, Config.CokeMin+random.nextInt(Config.CokeMax-Config.CokeMin+1+(Config.AllowFortune?fortune:0))):Math.min(Config.CharcoalTotal, Config.CharcoalMin+random.nextInt(Config.CharcoalMax-Config.CharcoalMin+1+(Config.AllowFortune?fortune:0)));
	}
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		return rand.nextInt(3);
	}
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

}
