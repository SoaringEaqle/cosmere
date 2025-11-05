package leaf.cosmere.common.items;

import leaf.cosmere.api.*;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.text.TextHelper;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.registry.CosmereDamageTypesRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.*;

public class GodMetalAlloyNuggetItem extends AlloyItem implements IHasSize, IGrantsManifestations
{
	public static int MIN_SIZE = 1;
	public static int MAX_SIZE = 16;

	public int getMinSize()
	{
		return MIN_SIZE;
	}

	public int getMaxSize()
	{
		return MAX_SIZE;
	}

	public GodMetalAlloyNuggetItem()
	{
		super();
	}

	public void addFilled(CreativeModeTab.Output output, HashSet<Metals.MetalType> alloyedMetals, int size)
	{
		ItemStack itemStack = new ItemStack(this);
		writeMetalAlloyNbtData(itemStack, alloyedMetals);
		writeMetalAlloySizeNbtData(itemStack, size);
		output.accept(itemStack);
	}

	@Nonnull
	@Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return UseAnim.EAT;
	}


	@Override
	public int getUseDuration(ItemStack stack)
	{
		//be annoying enough that people prefer metal vials
		Integer size = readMetalAlloySizeNbtData(stack);
		if (size == null) return 16;
		return size;
	}

	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		if (player.canEat(true))
		{
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(stack);
		}
		return InteractionResultHolder.pass(stack);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level pLevel, LivingEntity pLivingEntity)
	{
		if (pLevel.isClientSide)
		{
			return itemstack;
		}

		if (pLivingEntity instanceof Player player && !player.isCreative())
		{
			itemstack.shrink(1);
		}

		return itemstack;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
	{
		HashSet<Metals.MetalType> metals = readMetalAlloyNbtData(stack);
		Integer size = readMetalAlloySizeNbtData(stack);

		if (metals != null)
		{
			MutableComponent metalListComponent = Component.empty();
			for (Iterator<Metals.MetalType> it = metals.iterator(); it.hasNext(); )
			{
				Metals.MetalType metal = it.next();
				metalListComponent.append(Component.translatable(metal.getTranslationKey())
						.withStyle(Style.EMPTY.withColor(metal.getColorValue())));
				if (it.hasNext()) metalListComponent.append(Component.literal(" / ").withStyle(ChatFormatting.WHITE));
			}
			tooltip.add(metalListComponent);
		}

		tooltip.add(Component.literal("Size: ").withStyle(ChatFormatting.WHITE).append(
				Component.literal(size + "/" + MAX_SIZE).withStyle(ChatFormatting.GRAY)));

		ArrayList<Manifestation> manifestations = determineManifestations(stack);

		if (!manifestations.isEmpty() & size != null)
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

	@Override
	public Rarity getRarity(ItemStack itemStack)
	{
		Integer size = readMetalAlloySizeNbtData(itemStack);
		if (size == null) return Rarity.COMMON;

		boolean containsGodMetal = false;
		Set<Metals.MetalType> metalTypes = readMetalAlloyNbtData(itemStack);
		if (metalTypes == null) return Rarity.COMMON;
		for (Metals.MetalType metalType : metalTypes)
		{
			if (metalType.isGodMetal())
			{
				containsGodMetal = true;
				break;
			}
		}

		if (containsGodMetal)
		{
			if (size <= 8) return Rarity.UNCOMMON;
			else if (size == MAX_SIZE) return Rarity.EPIC;
			return Rarity.RARE;
		}

		return Rarity.COMMON;
	}

	// This needs to be replaced once a connection system is in place
	// https://wob.coppermind.net/events/361-skyward-pre-release-ama/#e11225
	// Mixing Lerasium with other god metals or magic systems metals could create connections
	// for new Investiture sources and new Manifestations
	public ArrayList<Manifestation> determineManifestations(ItemStack itemStack)
	{
		ArrayList<Manifestation> manifestations = new ArrayList<>();

		HashSet<Metals.MetalType> metals = readMetalAlloyNbtData(itemStack);
		if (metals == null) return manifestations;

		Manifestation manifestation;
		if (metals.contains(Metals.MetalType.LERASIUM))
		{
			for (Metals.MetalType metal : metals)
			{
				if (metal != Metals.MetalType.LERASIUM)
				{
					manifestation = Manifestations.ManifestationTypes.ALLOMANCY.getManifestation(metal.getID());
					if (manifestation.getManifestationType() != Manifestations.ManifestationTypes.NONE)
					{
						manifestations.add(manifestation);
					}
				}
			}
		}
		else if (metals.contains(Metals.MetalType.LERASATIUM))
		{
			for (Metals.MetalType metal : metals)
			{
				if (metal != Metals.MetalType.LERASATIUM)
				{
					manifestation = Manifestations.ManifestationTypes.FERUCHEMY.getManifestation(metal.getID());
					if (manifestation.getManifestationType() != Manifestations.ManifestationTypes.NONE)
					{
						manifestations.add(manifestation);
					}
				}
			}
		}
		return manifestations;


	}

	@Override
	public void grantManifestations(LivingEntity livingEntity, ArrayList<Manifestation> manifestations, int strength)
	{
		SpiritwebCapability.get(livingEntity).ifPresent(iSpiritweb ->
		{
			SpiritwebCapability spiritweb = (SpiritwebCapability) iSpiritweb;

			for(Manifestation manifestation: manifestations)
			{
				int currentStrength = 0;
				if(!(manifestation.getAttribute() instanceof RangedAttribute attribute)) return;
				AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
				if(attributeInstance != null) {
					currentStrength = (int) attributeInstance.getValue();
				}

				// Let's ensure not to update the base value if it's out of range,
				// even if it will get sanitized
				int newStrength = strength + currentStrength;
				if(newStrength >= attribute.getMinValue() && newStrength <= attribute.getMaxValue())
				{
					spiritweb.giveManifestation(manifestation, strength+currentStrength);
				}
			}

			if (livingEntity instanceof ServerPlayer serverPlayer)
			{
				spiritweb.syncToClients(serverPlayer);
			}
		});
	}
}