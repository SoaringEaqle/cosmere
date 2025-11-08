package leaf.cosmere.client.gui;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MetalworkingResultSlot extends Slot {
	private final MetalworkingTableMenu menu;
	private final Player player;

	public MetalworkingResultSlot(MetalworkingTableMenu menu, Player player, int pSlot, int pX, int pY) {
		// slot index, x, y are controlled by the menu when adding; we just need a dummy container for ctor, but we won't use it
		super(menu.getResultContainer(), pSlot, pX, pY);
		this.menu = menu;
		this.player = player;
	}

	@Override
	public boolean mayPlace(ItemStack stack) { return false; }

	@Override
	public ItemStack remove(int amount) {
		// allow removing crafted stack even if container impl disallows normal removes
		return this.getItem().split(amount);
	}

	@Override
	public void onTake(Player player, ItemStack crafted) {
		crafted.onCraftedBy(player.level(), player, crafted.getCount());
		menu.onCrafted(player, crafted);
	}
}
