/*
 * File updated ~ 4 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.network.packets;

import com.google.common.collect.Iterables;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.charge.IChargeable;
import leaf.cosmere.common.charge.ItemChargeHelper;
import leaf.cosmere.common.network.ICosmerePacket;
import leaf.cosmere.surgebinding.common.capabilities.SurgebindingSpiritwebSubmodule;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;
import leaf.cosmere.surgebinding.common.items.GemstoneItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Predicate;

public class BreatheStormlight implements ICosmerePacket
{
	public static final Predicate<ItemStack> SUPPORTED = (itemStack) ->
	{
		return (itemStack.getItem() instanceof GemstoneItem gemstoneItem)
				//&& containsMetal
				//&& !isUncommonMetal
				;
	};

	public BreatheStormlight()
	{
	}

	public BreatheStormlight(FriendlyByteBuf buffer)
	{
	}

	@Override
	public void handle(NetworkEvent.Context context)
	{
		ServerPlayer sender = context.getSender();
		MinecraftServer server = sender.getServer();
		server.submitAsync(() -> SpiritwebCapability.get(sender).ifPresent((cap) ->
		{
			SurgebindingSpiritwebSubmodule ssm = SurgebindingSpiritwebSubmodule.getSubmodule(cap);

			if (ssm != null)
			{
				if (ssm.getStormlight() != 0)
				{
					if (ssm.isOathed() || ssm.isHerald())
					{
						ssm.breatheStormlight();
					}
				}
			}

		}));
		context.setPacketHandled(true);
	}


	@Override
	public void encode(FriendlyByteBuf buf)
	{

	}

}
