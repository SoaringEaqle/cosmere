package leaf.cosmere.surgebinding.common.capabilities;

import leaf.cosmere.api.math.MathHelper;
import leaf.cosmere.surgebinding.client.render.model.DynamicShardplateModel;
import leaf.cosmere.surgebinding.common.items.DeadplateItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DynamicShardplateData implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
	private final LazyOptional<DynamicShardplateData> opt = LazyOptional.of(() -> this);

	private CompoundTag nbt;

	private String headID;
	private String armsID;
	private String bodyID;
	private String legsID;
	private String kamaID;

	public DynamicShardplateData()
	{
		this.nbt = new CompoundTag();

		this.headID = "head_" + MathHelper.randomInt(1, DynamicShardplateModel.TOTAL_HELMET_IDS);
		this.armsID = "arms_" + MathHelper.randomInt(1, DynamicShardplateModel.TOTAL_ARM_IDS);
		this.bodyID = "body_" + MathHelper.randomInt(1, DynamicShardplateModel.TOTAL_TORSO_IDS);
		this.legsID = "legs_" + MathHelper.randomInt(1, DynamicShardplateModel.TOTAL_LEG_IDS);
		this.kamaID = "kama_" + MathHelper.randomInt(1, DynamicShardplateModel.TOTAL_KAMA_IDS);
	}
	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction)
	{
		return DeadplateItem.CAPABILITY.orEmpty(capability, opt);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap)
	{
		return ICapabilityProvider.super.getCapability(cap);
	}

	@Override
	public CompoundTag serializeNBT()
	{
		if (this.nbt == null)
		{
			this.nbt = new CompoundTag();
		}

		this.nbt.putString("headID", this.headID);
		this.nbt.putString("armsID", this.armsID);
		this.nbt.putString("bodyID", this.bodyID);
		this.nbt.putString("legsID", this.legsID);
		this.nbt.putString("kamaID", this.kamaID);

		return this.nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag compoundTag)
	{
		this.nbt = nbt;

		this.headID = nbt.getString("headID");
		this.armsID = nbt.getString("armsID");
		this.bodyID = nbt.getString("bodyID");
		this.legsID = nbt.getString("legsID");
		this.kamaID = nbt.getString("kamaID");
	}

	public String getHeadID()
	{
		return headID;
	}

	public String getArmsID()
	{
		return armsID;
	}

	public String getBodyID()
	{
		return bodyID;
	}

	public String getLegsID()
	{
		return legsID;
	}

	public String getKamaID()
	{
		return kamaID;
	}
}
