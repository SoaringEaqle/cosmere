/*
 * File updated ~ 14 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.eventHandlers;

import leaf.cosmere.api.EnumUtils;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.entity.Chull;
import leaf.cosmere.surgebinding.common.entity.spren.Cryptic;
import leaf.cosmere.surgebinding.common.entity.spren.Honorspren;
import leaf.cosmere.surgebinding.common.registries.SurgebindingAttributes;
import leaf.cosmere.surgebinding.common.registries.SurgebindingEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Surgebinding.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SurgebindingModEventHandler
{

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event)
	{
		for (Roshar.Surges surge : EnumUtils.SURGES)
		{
			event.add(EntityType.PLAYER, SurgebindingAttributes.SURGEBINDING_ATTRIBUTES.get(surge).getAttribute());
		}

	}

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event)
	{
		event.put(SurgebindingEntityTypes.CHULL.get(), Chull.createAttributes().build());
		event.put(SurgebindingEntityTypes.CRYPTIC.get(), Cryptic.createAttributes().build());
		event.put(SurgebindingEntityTypes.HONORSPREN.get(), Honorspren.createAttributes().build());
	}
}
