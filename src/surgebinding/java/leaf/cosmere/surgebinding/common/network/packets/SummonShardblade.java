/*
 * File updated ~ 14 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.network.packets;

import com.google.common.collect.Multimap;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.helpers.CompoundNBTHelper;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.network.ICosmerePacket;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;
import leaf.cosmere.surgebinding.common.items.HonorbladeItem;
import leaf.cosmere.surgebinding.common.items.ShardbladeItem;
import leaf.cosmere.surgebinding.common.registries.SurgebindingAttributes;
import leaf.cosmere.surgebinding.common.registries.SurgebindingManifestations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.UUID;

public class SummonShardblade implements ICosmerePacket
{
	public SummonShardblade()
	{
	}

	public SummonShardblade(FriendlyByteBuf buffer)
	{
	}

	@Override
	public void handle(NetworkEvent.Context context)
	{
		ServerPlayer sender = context.getSender();
		MinecraftServer server = sender.getServer();
		server.submitAsync(() -> SpiritwebCapability.get(sender).ifPresent((cap) ->
		{
			var spiritwebTags = cap.getCompoundTag();
			var shardblades = CompoundNBTHelper.getOrCreate(spiritwebTags, "shardblades");

			final LivingEntity livingEntity = cap.getLiving();
			final ItemStack itemInHand = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);

			final int maxShardblades = SurgebindingConfigs.SERVER.MAX_SHARDBLADES.get();

			if (itemInHand.isEmpty())
			{
				for (int i = 0; i < maxShardblades; i++)
				{
					final String pKey = String.valueOf(i);
					if (shardblades.contains(pKey))
					{
						final CompoundTag test = shardblades.getCompound(pKey);
						ItemStack stack = ItemStack.of(test);
						livingEntity.setItemInHand(InteractionHand.MAIN_HAND, stack);
						shardblades.remove(pKey);

						if (stack.getItem() instanceof HonorbladeItem honorbladeItem)
						{
							UpdateIntrinsicPowers(honorbladeItem, livingEntity, false);
						}

						//todo do we actually need to tell the player about changes to their spiritweb here?
						//cap.syncToClients(sender);
						break;
					}
				}
			}
			else if (itemInHand.getItem() instanceof ShardbladeItem shardbladeItem)
			{
				if (shardbladeItem.canSummonDismiss(sender))
				{

					for (int i = 0; i < maxShardblades; i++)
					{
						final String pKey = String.valueOf(i);
						if (!shardblades.contains(pKey))
						{
							shardblades.put(pKey, itemInHand.serializeNBT());
							livingEntity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

							if (shardbladeItem instanceof HonorbladeItem honorbladeItem)
							{
								UpdateIntrinsicPowers(honorbladeItem, livingEntity, true);
							}

							//todo do we actually need to tell the player about changes to their spiritweb here?
							//cap.syncToClients(sender);
							break;
						}
					}
				}
			}

		}));
		context.setPacketHandled(true);
	}

	private static void UpdateIntrinsicPowers(HonorbladeItem honorbladeItem, LivingEntity livingEntity, boolean isStoringHonorblade)
	{
		final Roshar.RadiantOrder radiantOrder = honorbladeItem.radiantOrder;
		Attribute firstSurge = SurgebindingAttributes.SURGEBINDING_ATTRIBUTES.get(radiantOrder.getFirstSurge()).getAttribute();
		Attribute secondSurge = SurgebindingAttributes.SURGEBINDING_ATTRIBUTES.get(radiantOrder.getSecondSurge()).getAttribute();
		UUID primary = honorbladeItem.getPrimaryHonorbladeSurgeUuid();
		UUID secondary = honorbladeItem.getSecondaryHonorbladeSurgeUuid();
		ISpiritweb web = SpiritwebCapability.get(livingEntity).resolve().get();

		if (isStoringHonorblade)
		{
			//then add the power to the spiritweb
			livingEntity.getAttribute(firstSurge).addPermanentModifier(new AttributeModifier(primary, radiantOrder.getName() + "PrimaryHonorbladeSurge", 5, AttributeModifier.Operation.ADDITION));
			livingEntity.getAttribute(secondSurge).addPermanentModifier(new AttributeModifier(secondary, radiantOrder.getName() + "SecondaryHonorbladeSurge", 5, AttributeModifier.Operation.ADDITION));
			if(!web.hasManifestation(SurgebindingManifestations.SURGEBINDING_POWERS.get(radiantOrder.getFirstSurge()).get()))
			{
				web.giveManifestation(SurgebindingManifestations.SURGEBINDING_POWERS.get(radiantOrder.getFirstSurge()).get(),0);
			}
			if(!web.hasManifestation(SurgebindingManifestations.SURGEBINDING_POWERS.get(radiantOrder.getSecondSurge()).get()))
			{
				web.giveManifestation(SurgebindingManifestations.SURGEBINDING_POWERS.get(radiantOrder.getSecondSurge()).get(),0);
			}

		}
		else
		{
			//otherwise remove, the power will be granted by having the item in hand
			livingEntity.getAttribute(firstSurge).removePermanentModifier(primary);
			livingEntity.getAttribute(secondSurge).removePermanentModifier(secondary);
		}
	}

	@Override
	public void encode(FriendlyByteBuf buf)
	{

	}

}
