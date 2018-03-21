package charcoalPit.core;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class FilteredItemHandler extends ItemStackHandler{
	
	public boolean canInsert, canExtract;
	
	public FilteredItemHandler()
    {
        super();
        canInsert=true;
        canExtract=true;
    }

    public FilteredItemHandler(int size)
    {
        super(size);
        canInsert=true;
        canExtract=true;
    }

    public FilteredItemHandler(NonNullList<ItemStack> stacks)
    {
        super(stacks);
        canInsert=true;
        canExtract=true;
    }
	
	public boolean isItemValid(int slot, @Nonnull ItemStack stack){
		return true;
	}
	
	public boolean canInsert(int slot){
		return canInsert;
	}
	
	public boolean canExtract(int slot){
		return canExtract;
	}
	
	@Override
	public int getSlotLimit(int slot) {
		return super.getSlotLimit(slot);
	}
	
	@Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if(canInsert(slot)&&isItemValid(slot, stack)){
        	return super.insertItem(slot, stack, simulate);
        }else{
        	return ItemStack.EMPTY;
        }
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if(canExtract(slot)){
        	return super.extractItem(slot, amount, simulate);
        }else{
        	return ItemStack.EMPTY;
        }
    }
}
