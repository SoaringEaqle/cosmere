package leaf.cosmere.client.gui;

import leaf.cosmere.common.recipe.MetalworkingRecipe;
import leaf.cosmere.common.registry.BlocksRegistry;
import leaf.cosmere.common.registry.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.Recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class MetalworkingTableMenu extends AbstractContainerMenu
{
	private final Level level;
	private final ContainerLevelAccess access;
	private final Player player;

	private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 4, 4);
	private final ResultContainer resultSlots = new ResultContainer();

	public MetalworkingTableMenu(int id, Inventory playerInv, BlockPos pos)
	{
		super(MenuRegistry.METALWORKING_TABLE_MENU.get(), id);
		this.level = playerInv.player.level();
		this.player = playerInv.player;
		this.access = ContainerLevelAccess.create(level, pos);

		// Output slot (index 0)
		// was: new ResultSlot(playerInv.player, craftSlots, resultSlots, 0, 134, 44)
		Slot result = new MetalworkingResultSlot(this, playerInv.player, 0, 134, 44);
		result.index = 0; // ensure index 0
		this.addSlot(result);

		// 4×4 crafting grid (indexes 1..16)
		int index = 1;
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				this.addSlot(new Slot(craftSlots, index - 1, 22 + x * 18, 17 + y * 18));
				index++;
			}
		}

		// Player inventory
		int invY = 102;
		for (int r = 0; r < 3; r++)
		{
			for (int c = 0; c < 9; c++)
			{
				this.addSlot(new Slot(playerInv, c + r * 9 + 9, 8 + c * 18, invY + r * 18));
			}
		}
		// Hotbar
		for (int c = 0; c < 9; c++)
		{
			this.addSlot(new Slot(playerInv, c, 8 + c * 18, invY + 58));
		}

		// Initial compute
		this.slotsChanged(craftSlots);
	}

	@Override
	public void slotsChanged(Container container)
	{
		super.slotsChanged(container);
		if (!level.isClientSide)
		{
			updateResult();
		}
	}

	private void updateResult()
	{
		ItemStack out = ItemStack.EMPTY;

		Optional<? extends Recipe<?>> r =
				level.getRecipeManager().getRecipeFor(MetalworkingRecipe.TYPE, craftSlots, level);

		if (r.isPresent())
		{
			MetalworkingRecipe recipe = (MetalworkingRecipe) r.get();
			out = recipe.assemble(craftSlots, level.registryAccess());
		}

		resultSlots.setItem(0, out);
		broadcastChanges();
	}

	@Override
	public void removed(Player player)
	{
		super.removed(player);
		this.access.execute((level, pos) -> this.clearContainer(player, this.craftSlots));
		this.resultSlots.clearContent();
	}

	@Override
	public boolean stillValid(Player player)
	{
		return stillValid(access, player, BlocksRegistry.METALWORKING_TABLE.getBlock());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack ret = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (!slot.hasItem()) return ItemStack.EMPTY;

		ItemStack stack = slot.getItem();
		ret = stack.copy();

		final int RESULT = 0;
		final int GRID_START = 1, GRID_END = 17;
		final int INV_START = 17, INV_END = 44;
		final int HOT_START = 44, HOT_END = 53;

		if (index == RESULT) {
			if (!this.moveItemStackTo(stack, INV_START, HOT_END, true)) return ItemStack.EMPTY;
			slot.onTake(player, stack); // triggers onCrafted → consumes inputs properly
		} else if (index >= GRID_START && index < GRID_END) {
			if (!this.moveItemStackTo(stack, INV_START, HOT_END, false)) return ItemStack.EMPTY;
		} else if (index >= INV_START && index < INV_END) {
			if (!this.moveItemStackTo(stack, HOT_START, HOT_END, false)) return ItemStack.EMPTY;
		} else if (index >= HOT_START && index < HOT_END) {
			if (!this.moveItemStackTo(stack, INV_START, INV_END, false)) return ItemStack.EMPTY;
		}

		if (stack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
		if (stack.getCount() == ret.getCount()) return ItemStack.EMPTY;

		slot.onTake(player, stack);
		this.slotsChanged(this.craftSlots);
		return ret;
	}

	public ResultContainer getResultContainer() { return this.resultSlots; }

	void onCrafted(Player player, ItemStack crafted) {
		// consume ingredients using YOUR recipe’s remaining items
		NonNullList<ItemStack> remaining = getRemainingItemsForCurrentRecipe();

		for (int i = 0; i < remaining.size(); ++i) {
			ItemStack inGrid = this.craftSlots.getItem(i);
			ItemStack rem = remaining.get(i);

			if (!inGrid.isEmpty()) {
				this.craftSlots.removeItem(i, 1);
				inGrid = this.craftSlots.getItem(i);
			}

			if (!rem.isEmpty()) {
				if (inGrid.isEmpty()) {
					this.craftSlots.setItem(i, rem);
				} else if (ItemStack.isSameItemSameTags(inGrid, rem)) {
					rem.grow(inGrid.getCount());
					this.craftSlots.setItem(i, rem);
				} else if (!this.player.getInventory().add(rem)) {
					this.player.drop(rem, false);
				}
			}
		}

		this.broadcastChanges();
	}


	private NonNullList<ItemStack> getRemainingItemsForCurrentRecipe() {
		Optional<? extends Recipe<?>> r =
				level.getRecipeManager().getRecipeFor(MetalworkingRecipe.TYPE, craftSlots, level);
		if (r.isPresent()) {
			MetalworkingRecipe recipe = (MetalworkingRecipe) r.get();
			return recipe.getRemainingItems(craftSlots);
		}
		return NonNullList.withSize(16, ItemStack.EMPTY);
	}
}