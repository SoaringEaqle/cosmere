package leaf.cosmere.common.items;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.IHasSize;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Metals;
import leaf.cosmere.api.manifestation.Manifestation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class GodMetalNuggetItem extends MetalNuggetItem implements IHasSize
{
	public static int MIN_SIZE = 1;
	public static int MAX_SIZE = 16;

	public GodMetalNuggetItem(Metals.MetalType metalType)
	{
		super(metalType);
	}

	@Override
	public int getMaxSize()
	{
		return MAX_SIZE;
	}

	@Override
	public int getMinSize()
	{
		return MIN_SIZE;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
	{
		Integer size = readMetalAlloySizeNbtData(stack);

		tooltip.add(Component.literal("Size: ").withStyle(ChatFormatting.WHITE).append(
				Component.literal(size + "/" + MAX_SIZE).withStyle(ChatFormatting.GRAY)));

		ArrayList<Manifestation> manifestations = determineManifestations();

		if (!manifestations.isEmpty() & size != getMaxSize())
		{
			tooltip.add(Component.empty());
			tooltip.add(Component.literal("When consumed:").withStyle(ChatFormatting.GOLD));
			for (Manifestation manifestation : manifestations)
			{
				tooltip.add(Component.literal("+" + size + " ").append(
								Component.translatable(manifestation.getTranslationKey()))
						.withStyle(ChatFormatting.BLUE));
			}
		}
	}

	public ArrayList<Manifestation> determineManifestations()
	{
		ArrayList<Manifestation> manifestations = new ArrayList<>();

		if (this.getMetalType() == Metals.MetalType.LERASIUM)
		{
			for (Metals.MetalType metal : EnumUtils.METAL_TYPES)
			{
				Manifestation manifestation = Manifestations.ManifestationTypes.ALLOMANCY.getManifestation(metal.getID());
				if (manifestation.getManifestationType() != Manifestations.ManifestationTypes.NONE)
				{
					manifestations.add(manifestation);
				}
			}
		}
		else if (this.getMetalType() == Metals.MetalType.LERASATIUM)
		{
			for (Metals.MetalType metal : EnumUtils.METAL_TYPES)
			{
				Manifestation manifestation = Manifestations.ManifestationTypes.FERUCHEMY.getManifestation(metal.getID());
				if (manifestation.getManifestationType() != Manifestations.ManifestationTypes.NONE)
				{
					manifestations.add(manifestation);
				}
			}
		}
		return manifestations;
	}
}
