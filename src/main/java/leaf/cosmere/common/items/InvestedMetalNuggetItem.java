package leaf.cosmere.common.items;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Manifestations.ManifestationTypes;
import leaf.cosmere.api.Metals.MetalType;
import leaf.cosmere.api.manifestation.Manifestation;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.*;
import java.util.List;

public class InvestedMetalNuggetItem extends MetalNuggetItem
{
	public static int MIN_SIZE = 1;
	public static int MAX_SIZE = 16;

	public InvestedMetalNuggetItem(MetalType metalType)
	{
		super(metalType);
	}

	public void addFilled(CreativeModeTab.Output output, HashSet<MetalType> alloyedMetals, int size)
	{
		output.accept(new ItemStack(this));
		ItemStack itemStack = new ItemStack(this);
		writeMetalAlloyNbtData(itemStack.getOrCreateTag(), alloyedMetals, size);
		output.accept(itemStack);
	}

	// Allow Lerasium to have alloy nuggets of multiple god metals in the future
	// This will allow for establishing connections with other shards via God metal injestion
	// https://wob.coppermind.net/events/361-skyward-pre-release-ama/#e11225
	// (eg. Lerasium + Tanavastium + Koravellium) would give access to raw Investiture for surges without stormlight
	// but the spren bond would still be needed for use of surges

	public static boolean isAlloy(CompoundTag nbt)
	{
		if(nbt.contains("alloyedMetals"))
		{
			int[] metalIds = nbt.getIntArray("alloyedMetals");
			if (metalIds.length > 1) return true;
		}
		return false;
	}

	public static HashSet<MetalType> readMetalAlloyNbtData(CompoundTag nbt)
	{
		if(nbt.contains("alloyedMetals"))
		{
			int[] metalIds = nbt.getIntArray("alloyedMetals");
			HashSet<MetalType> metalTypes = new HashSet<>();
			for (int metalId : metalIds)
			{
				metalTypes.add(
						MetalType.valueOf(metalId).isPresent() ? MetalType.valueOf(metalId).get() : MetalType.SILVER);
			}
			return metalTypes;
		}
		return null;
	}

	public static Integer readMetalAlloySizeNbtData(CompoundTag nbt)
	{
		if(nbt.contains("nuggetSize")) return nbt.getInt("nuggetSize");
		nbt.putInt("nuggetSize", MAX_SIZE);
		return MAX_SIZE;
	}

	public static boolean writeMetalAlloyNbtData(CompoundTag nbt, HashSet<MetalType> alloyedMetals, int size)
	{
		if (size > MAX_SIZE || size < MIN_SIZE) return false;

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
		nbt.putInt("nuggetSize", size);
		return true;
	}

	public static Color mixColors(ArrayList<Color> colors, int size)
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

	public Color getColour(HashSet<MetalType> metals, int size)
	{
		ArrayList<Color> colors = new ArrayList<>();
		int i = 0;
		for(MetalType metal : metals)
		{
			colors.add(metal.getColor());
		}
		return mixColors(colors, size);
	}

	public int getColourValue(HashSet<MetalType> metals, int size)
	{
		return getColour(metals, size).getRGB();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
	{
		HashSet<MetalType> metals = readMetalAlloyNbtData(stack.getOrCreateTag());
		Integer size = readMetalAlloySizeNbtData(stack.getOrCreateTag());

		if(metals != null)
		{
			MutableComponent metalListComponent = Component.empty();
			for (Iterator<MetalType> it = metals.iterator(); it.hasNext(); )
			{
				MetalType metal = it.next();
				metalListComponent.append(Component.translatable(metal.getTranslationKey())
						.withStyle(Style.EMPTY.withColor(metal.getColorValue())));
				if (it.hasNext()) metalListComponent.append(Component.literal(" / ").withStyle(ChatFormatting.WHITE));
			}
			tooltip.add(metalListComponent);


		}

		tooltip.add(Component.literal("Size: ").withStyle(ChatFormatting.WHITE).append(
				Component.literal(size + "/" + MAX_SIZE).withStyle(ChatFormatting.GRAY)));

		ArrayList<Manifestation> manifestations = determineManifestations(stack);

		if(!manifestations.isEmpty() & size != null)
		{
			tooltip.add(Component.empty());
			tooltip.add(Component.literal("When consumed:").withStyle(ChatFormatting.GOLD));
			for(Manifestation manifestation : manifestations)
			{
				tooltip.add(Component.literal("+" + size + " ").append(
								Component.translatable(manifestation.getTranslationKey()))
						.withStyle(ChatFormatting.BLUE));
			}
		}
	}

	@Override
	public Component getName(ItemStack stack) {
		HashSet<MetalType> metals = readMetalAlloyNbtData(stack.getOrCreateTag());
		if(metals != null && metals.size() == 1)
		{
			return Component.translatable("item.cosmere." + metals.iterator().next().getName() + "_nugget");
		}
		return super.getName(stack);
	}

	@Override
	public Rarity getRarity(ItemStack itemStack) {
		if(!(itemStack.getItem() instanceof InvestedMetalNuggetItem investedMetalNuggetItem)) return Rarity.COMMON;

		Integer size = InvestedMetalNuggetItem.readMetalAlloySizeNbtData(itemStack.getOrCreateTag());
		if(size == null) return Rarity.COMMON;

		boolean containsGodMetal = false;
		Set<MetalType> metalTypes = InvestedMetalNuggetItem.readMetalAlloyNbtData(itemStack.getOrCreateTag());
		if(metalTypes == null) return investedMetalNuggetItem.getMetalType().getRarity();
		for(MetalType metalType : metalTypes)
		{
			if (metalType.isGodMetal()) { containsGodMetal = true; break; }
		}

		if (containsGodMetal)
		{
			if (size <= 8) return Rarity.UNCOMMON;
			else if (size == MAX_SIZE) return Rarity.EPIC;
			return Rarity.RARE;
		}

		return Rarity.COMMON;
	}

	@Override
	public boolean isFoil(ItemStack itemStack)
	{
		if(!(itemStack.getItem() instanceof InvestedMetalNuggetItem investedMetalNuggetItem)) return false;
		if(InvestedMetalNuggetItem.readMetalAlloyNbtData(itemStack.getOrCreateTag()) == null) {
			return investedMetalNuggetItem.getMetalType().isGodMetal();
		}
		return super.isFoil(itemStack);
	}

	// This needs to be replaced once a connection system is in place
	// https://wob.coppermind.net/events/361-skyward-pre-release-ama/#e11225
	// Mixing Lerasium with other god metals or magic systems metals could create connections
	// for new Investiture sources and new Manifestations
	public static ArrayList<Manifestation> determineManifestations(ItemStack itemStack) {
		ArrayList<Manifestation> manifestations = new ArrayList<>();

		CompoundTag nbt = itemStack.getOrCreateTag();
		HashSet<MetalType> metals = readMetalAlloyNbtData(nbt);
		if (metals == null && itemStack.getItem() instanceof InvestedMetalNuggetItem item)
		{
			if (item.getMetalType() == MetalType.LERASIUM)
			{
				for(MetalType metal : EnumUtils.METAL_TYPES)
				{
					Manifestation manifestation = ManifestationTypes.ALLOMANCY.getManifestation(metal.getID());
					if (manifestation.getManifestationType() != ManifestationTypes.NONE)
					{
						 manifestations.add(manifestation);
					}
				}
			}
			else if (item.getMetalType() == MetalType.LERASATIUM)
			{
				for(MetalType metal : EnumUtils.METAL_TYPES)
				{
					Manifestation manifestation = ManifestationTypes.FERUCHEMY.getManifestation(metal.getID());
					if (manifestation.getManifestationType() != ManifestationTypes.NONE)
					{
						manifestations.add(manifestation);
					}
				}
			}
			return manifestations;
		}
		else {
			Manifestation manifestation = null;
			if (metals.contains(MetalType.LERASIUM)) {
				for (MetalType metal : metals)
				{
					if (metal != MetalType.LERASIUM)
					{
						manifestation = ManifestationTypes.ALLOMANCY.getManifestation(metal.getID());
						if (manifestation.getManifestationType() != ManifestationTypes.NONE)
						{
							manifestations.add(manifestation);
						}
					}
				}
			}
			else if (metals.contains(MetalType.LERASATIUM))
			{
				for (MetalType metal : metals)
				{
					if (metal != MetalType.LERASATIUM)
					{
						manifestation = ManifestationTypes.FERUCHEMY.getManifestation(metal.getID());
						if (manifestation.getManifestationType() != ManifestationTypes.NONE)
						{
							manifestations.add(manifestation);
						}
					}
				}
			}
			return manifestations;
		}

	}
}
