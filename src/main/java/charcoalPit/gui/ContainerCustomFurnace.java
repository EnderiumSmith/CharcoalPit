package charcoalPit.gui;

import charcoalPit.tile.TileCustomFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCustomFurnace extends Container{

	public final TileCustomFurnace tile;
	
	public ContainerCustomFurnace(InventoryPlayer player, TileCustomFurnace furnace) {
		tile=furnace;
		this.addSlotToContainer(new SlotItemHandler(tile.top, 0, 56, 17));
		this.addSlotToContainer(new SlotItemHandler(tile.fuel, 0, 56, 53));
		this.addSlotToContainer(new SlotExtractOnly(tile.result, 0, 116, 35));
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(player, k, 8 + k * 18, 142));
        }
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);
            icontainerlistener.sendWindowProperty(this, 0, tile.burnTime);
            icontainerlistener.sendWindowProperty(this, 1, tile.totalBurnTime);
            icontainerlistener.sendWindowProperty(this, 2, tile.cookTime);
        }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		switch(id){
		case 0:tile.burnTime=data;
		break;
		case 1:tile.totalBurnTime=data;
		break;
		case 2:tile.cookTime=data;
		break;
		default:break;
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

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (TileCustomFurnace.canSmelt(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
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
