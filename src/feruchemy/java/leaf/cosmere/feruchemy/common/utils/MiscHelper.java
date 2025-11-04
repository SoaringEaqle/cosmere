/*
 * File updated ~ 9 - 8 - 2024 ~ Leaf
 */

package leaf.cosmere.feruchemy.common.utils;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Metals;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.text.TextHelper;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.items.InvestedMetalNuggetItem;
import leaf.cosmere.common.items.MetalNuggetItem;
import leaf.cosmere.feruchemy.common.config.FeruchemyConfigs;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MiscHelper
{

	public static void consumeNugget(LivingEntity livingEntity, ItemStack itemStack, boolean isInvestedNugget)
	{
		MetalNuggetItem nuggetItem = (MetalNuggetItem) itemStack.getItem();
		Metals.MetalType metalType = nuggetItem.getMetalType();

		if (livingEntity.level().isClientSide)
		{
			return;
		}

		SpiritwebCapability.get(livingEntity).ifPresent(iSpiritweb ->
		{
			SpiritwebCapability spiritweb = (SpiritwebCapability) iSpiritweb;

			if(isInvestedNugget)
			{
				HashSet<Metals.MetalType> metalTypes = InvestedMetalNuggetItem.readMetalAlloyNbtData(itemStack.getOrCreateTag());
				Integer size = InvestedMetalNuggetItem.readMetalAlloySizeNbtData(itemStack.getOrCreateTag());

				if(metalTypes != null && size != null)
				{
					// Ensure it is for Allomancy only
					if (metalType == Metals.MetalType.LERASATIUM || (metalTypes != null && metalTypes.contains(Metals.MetalType.LERASATIUM) && metalTypes.size() == 2))
					{
						ArrayList<Manifestation> manifestations = InvestedMetalNuggetItem.determineManifestations(itemStack);
						HashMap<Manifestation, Integer> existingManifestations = spiritweb.getManifestations();

						for(Manifestation manifestation: manifestations)
						{
							Integer currentStrength = existingManifestations.get(manifestation);
							spiritweb.giveManifestation(manifestation, currentStrength == null ? size : currentStrength + size);
						}
					}

					//https://www.theoryland.com/intvmain.php?i=977#43
					if (metalType == Metals.MetalType.LERASATIUM)
					{
						if (livingEntity instanceof Llama && !livingEntity.hasCustomName())
						{
							//todo translations
							livingEntity.setCustomName(TextHelper.createTranslatedText("Full Feruchemist Llama"));
						}
					}
				}
			}

			if (livingEntity instanceof ServerPlayer serverPlayer)
			{
				spiritweb.syncToClients(serverPlayer);
			}

		});
	}


}
