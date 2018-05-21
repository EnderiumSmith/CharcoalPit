package charcoalPit.gui;

import charcoalPit.tile.TileClayPot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerClayPot extends Container{

public final TileClayPot tile;
	
	public ContainerClayPot(IInventory player, TileClayPot tile) {
		this.tile=tile;
		int i=0,j=0;
		for (i = 0; i < 2; ++i)
        {
            for (j = 0; j < 2; ++j)
            {
                this.addSlotToContainer(new SlotItemHandler(tile.items, j + i * 2, 62+9 + j * 18, 17 + i * 18));
            }
        }
		this.addSlotToContainer(new SlotItemHandler(tile.items, 4, 62-18 + j * 18, 17 + i * 18));

        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(player, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(player, l, 8 + l * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tile.getWorld().getTileEntity(tile.getPos()) != tile ? false : playerIn.getDistanceSq((double)tile.getPos().getX() + 0.5D, (double)tile.getPos().getY() + 0.5D, (double)tile.getPos().getZ() + 0.5D) <= 64.0D;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 5)
            {
                if (!this.mergeItemStack(itemstack1, 5, 41, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 5, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
	
	
	
}
