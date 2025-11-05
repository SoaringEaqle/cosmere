/*
 * File updated ~ 9 - 8 - 2024 ~ Leaf
 */

package leaf.cosmere.feruchemy.common.utils;

import leaf.cosmere.api.Metals;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.api.text.TextHelper;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.items.GodMetalAlloyNuggetItem;
import leaf.cosmere.common.items.MetalNuggetItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
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

			if(itemStack.getItem() instanceof GodMetalAlloyNuggetItem godMetalAlloyNuggetItem)
			{
				HashSet<Metals.MetalType> metalTypes = godMetalAlloyNuggetItem.readMetalAlloyNbtData(itemStack);
				Integer size = godMetalAlloyNuggetItem.readMetalAlloySizeNbtData(itemStack);

				if(size != null)
				{
					// Ensure it is for Allomancy only
					if (metalType == Metals.MetalType.LERASATIUM || (metalTypes != null && metalTypes.contains(Metals.MetalType.LERASATIUM) && metalTypes.size() == 2))
					{
						ArrayList<Manifestation> manifestations = godMetalAlloyNuggetItem.determineManifestations(itemStack);

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
							int newStrength = size + currentStrength;
							if(newStrength > attribute.getMinValue() && newStrength < attribute.getMaxValue())
							{
								spiritweb.giveManifestation(manifestation, size+currentStrength);
							}
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
