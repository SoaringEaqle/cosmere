package leaf.cosmere.client.gui;

import leaf.cosmere.common.Cosmere;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MetalworkingTableScreen extends AbstractContainerScreen<MetalworkingTableMenu>
{
	private static final ResourceLocation BG = new ResourceLocation(Cosmere.MODID, "textures/gui/metalworking_table.png");

	public MetalworkingTableScreen(MetalworkingTableMenu menu, Inventory inv, Component title)
	{
		super(menu, inv, title);
		this.imageWidth = 175;
		this.imageHeight = 183;
	}

	@Override
	protected void renderBg(GuiGraphics gfx, float partialTicks, int mouseX, int mouseY)
	{
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		gfx.blit(BG, x, y, 0, 0, this.imageWidth, this.imageHeight);

	}

	@Override
	protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY)
	{
		gfx.drawString(this.font, this.title, 21, 6, 0x404040, false);
		gfx.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 94 + 2, 0x404040, false);
	}

	@Override
	public void render(GuiGraphics g, int mouseX, int mouseY, float partial) {
		this.renderBackground(g);
		super.render(g, mouseX, mouseY, partial);
		this.renderTooltip(g, mouseX, mouseY);
	}
}

