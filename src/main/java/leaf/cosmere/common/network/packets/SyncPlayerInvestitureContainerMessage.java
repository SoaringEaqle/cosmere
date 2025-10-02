/*
 * File updated ~ 24 - 4 - 2021 ~ Leaf
 */

package leaf.cosmere.common.network.packets;

import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.investiture.InvestitureContainer;
import leaf.cosmere.common.network.ICosmerePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

public class SyncPlayerInvestitureContainerMessage implements ICosmerePacket
{
	public int entityID;
	public CompoundTag entityNBT;

	public SyncPlayerInvestitureContainerMessage(int entityID, CompoundTag entityNBT)
	{
		this.entityID = entityID;
		this.entityNBT = entityNBT;
	}

	@Override
	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(entityID);
		buf.writeNbt(entityNBT);
	}

	public static SyncPlayerInvestitureContainerMessage decode(FriendlyByteBuf buf)
	{
		return new SyncPlayerInvestitureContainerMessage(buf.readInt(), buf.readNbt());
	}

	@Override
	public void handle(NetworkEvent.Context cont)
	{
		Entity result = Minecraft.getInstance().level.getEntity(entityID);
		if (result != null)
		{
			InvestitureContainer.get((LivingEntity) result).ifPresent((c) ->
			{
				c.deserializeNBT(entityNBT);
				c.getEntity().refreshDimensions();
			});

		}
	}

}
