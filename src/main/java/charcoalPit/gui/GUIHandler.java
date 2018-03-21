package charcoalPit.gui;

import charcoalPit.tile.TileCeramicPot;
import charcoalPit.tile.TileClayPot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case(0):return new ContainerCeramicPot(player.inventory, (TileCeramicPot)world.getTileEntity(new BlockPos(x, y, z)));
		case(1):return new ContainerClayPot(player.inventory, (TileClayPot)world.getTileEntity(new BlockPos(x, y, z)));
		default:return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case(0):return new GuiCeramicPot(new ContainerCeramicPot(player.inventory, (TileCeramicPot)world.getTileEntity(new BlockPos(x, y, z))));
		case(1):return new GuiClayPot(new ContainerClayPot(player.inventory, (TileClayPot)world.getTileEntity(new BlockPos(x, y, z))));
		default:return null;
		}
	}

}
