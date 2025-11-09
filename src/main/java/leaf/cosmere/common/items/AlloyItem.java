package leaf.cosmere.common.items;

import leaf.cosmere.api.IHasAlloy;
import leaf.cosmere.api.IHasMetalType;
import leaf.cosmere.api.Metals.MetalType;
import leaf.cosmere.common.properties.PropTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class AlloyItem extends MetalItem implements IHasAlloy
{
	public AlloyItem(MetalType metalType)
	{
		super(metalType);
	}

	/**
	 * Returns true if the item can be used on the given entity, e.g. shears on sheep.
	 */
	public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand)
	{
		return InteractionResult.PASS;
	}

	public HashSet<MetalType> readMetalAlloyNbtData(ItemStack itemStack)
	{
		CompoundTag nbt = itemStack.getOrCreateTag();
		if(nbt.contains("alloyedMetals"))
		{
			int[] metalIds = nbt.getIntArray("alloyedMetals");
			HashSet<MetalType> metalTypes = new HashSet<>();
			for (int metalId : metalIds)
			{
				metalTypes.add(
						MetalType.valueOf(metalId).isPresent() ?
						MetalType.valueOf(metalId).get() : MetalType.SILVER);
			}
			return metalTypes;
		}
		return new HashSet<MetalType>();
	}

	public boolean writeMetalAlloyNbtData(ItemStack itemStack, HashSet<MetalType> alloyedMetals)
	{
		CompoundTag nbt = itemStack.getOrCreateTag();
		ArrayList<Integer> metalIds = new ArrayList<>();
		boolean containsGodMetal = false;
		boolean containsNormalMetal = false;
		for (MetalType alloyedMetal : alloyedMetals)
		{
			metalIds.add(alloyedMetal.getID());

			if (alloyedMetal.isGodMetal())
			{
				containsGodMetal = true;
			}
			else
			{
				if (containsGodMetal && containsNormalMetal) return false;
				containsNormalMetal = true;
			}
		}
		nbt.putIntArray("alloyedMetals", metalIds);
		return true;
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

	public Color getColour(HashSet<MetalType> metals)
	{
		ArrayList<Color> colors = new ArrayList<>();
		for(MetalType metal : metals)
		{
			if (metal == this.getMetalType()) continue;
			colors.add(metal.getColor());
		}
		return mixColors(colors);
	}

	public int getColourValue(HashSet<MetalType> metals)
	{
		return getColour(metals).getRGB();
	}
}
