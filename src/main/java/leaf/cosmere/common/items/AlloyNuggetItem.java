package leaf.cosmere.common.items;

import leaf.cosmere.api.Metals.MetalType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;

public class AlloyNuggetItem extends MetalNuggetItem
{
	MetalType alloyedMetalType;

	public AlloyNuggetItem(MetalType metalType, MetalType alloyedMetalType)
	{
		super(metalType);
		this.alloyedMetalType = alloyedMetalType;
	}

	public MetalType getAlloyedMetalType()
	{
		return alloyedMetalType;
	}

	public Color mixColors(ArrayList<Color> colors)
	{
		double r = 0;
		double g = 0;
		double b = 0;

		double ratio = 1f / colors.size();

		for(Color color: colors) {
			r += (color.getRed() * ratio);
			g += (color.getGreen() * ratio);
			b += (color.getBlue() * ratio);
		}

		// Clamp just in case of rounding
		r = Math.min(255, Math.max(0, r));
		g = Math.min(255, Math.max(0, g));
		b = Math.min(255, Math.max(0, b));

		return new Color((int) r, (int) g, (int) b);
	}

	public Color getColour()
	{
		ArrayList<Color> colors = new ArrayList<>();
		colors.add(this.getMetalType().getColor());
		colors.add(this.getAlloyedMetalType().getColor());
		return mixColors(colors);
	}

	public int getColourValue()
	{
		return getColour().getRGB();
	}
}
