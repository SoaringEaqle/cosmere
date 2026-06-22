
package leaf.cosmere.surgebinding.common.capabilities;

import leaf.cosmere.surgebinding.common.utils.ShardHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class DynamicShardplateData extends ShardData implements ICapabilityProvider, INBTSerializable<CompoundTag>, IShardplateDynamicData
{
	private final LazyOptional<IShard> opt = LazyOptional.of(() -> this);

	private String headID;
	private String faceplateID;
	private String bodyID;
	private String kamaID;

	private String rightArmID;
	private String rightPaldronsID;
	private String rightLegID;
	private String rightBootOutsideID;
	private String rightBootTipID;

	private String leftArmID;
	private String leftPaldronsID;
	private String leftLegID;
	private String leftBootOutsideID;
	private String leftBootTipID;

	private HashMap<ShardHelper.PlateComponent, Integer> idMap;

	private boolean colored;



	public DynamicShardplateData(ItemStack stack)
	{
		super(stack);

		idMap = ShardHelper.PlateComponent.randomComponentMap();

		this.colored = true;
	}
	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction)
	{
		return ShardData.SHARD_DATA.orEmpty(capability, opt);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap)
	{
		return super.getCapability(cap);
	}

	@Override
	public CompoundTag serializeNBT()
	{

		super.serializeNBT();

		super.nbt.putString("headID", this.headID);
		super.nbt.putString("faceplateID" , this.faceplateID);
		super.nbt.putString("bodyID", this.bodyID);
		super.nbt.putString("kamaID", this.kamaID);

		this.nbt.putString("rightArmID", this.rightArmID);
		this.nbt.putString("rightPaldronID", this.rightPaldronsID);
		this.nbt.putString("rightLegID", this.rightLegID);
		this.nbt.putString("rightBootOutsideID", this.rightBootOutsideID);
		this.nbt.putString("rightBootTipID", this.rightBootTipID);

		this.nbt.putString("leftArmID", this.leftArmID);
		this.nbt.putString("leftPaldronID", this.leftPaldronsID);
		this.nbt.putString("leftLegID", this.leftLegID);
		this.nbt.putString("leftBootOutsideID", this.leftBootOutsideID);
		this.nbt.putString("leftBootTipID", this.leftBootTipID);

		super.nbt.putBoolean("isColored", colored);

		return this.nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag compoundTag)
	{
		super.deserializeNBT(compoundTag);

		this.headID = nbt.getString("headID");
		this.faceplateID = nbt.getString("faceplateID");
		this.bodyID = nbt.getString("bodyID");
		this.kamaID = nbt.getString("kamaID");

		this.rightArmID = nbt.getString("rightArmID");
		this.rightPaldronsID = nbt.getString("rightPaldronID");
		this.rightLegID = nbt.getString("rightLegID");
		this.rightBootOutsideID = nbt.getString("rightBootOutsideID");
		this.rightBootTipID = nbt.getString("rightBootTipID");

		this.leftArmID = nbt.getString("leftArmID");
		this.leftPaldronsID = nbt.getString("leftPaldronID");
		this.leftLegID = nbt.getString("leftLegID");
		this.leftBootOutsideID = nbt.getString("leftBootOutsideID");
		this.leftBootTipID = nbt.getString("leftBootTipID");

		this.colored = nbt.getBoolean("isColored");

	}

	public String getHeadID()
	{
		return headID;
	}
	public String getFaceplateID()
	{
		return faceplateID;
	}
	public String getBodyID()
	{
		return bodyID;
	}

	public String getKamaID()
	{
		return kamaID;
	}

	public String getRightArmID()
	{
		return rightArmID;
	}

	public String getRightPaldronsID()
	{
		return rightPaldronsID;
	}

	public String getRightLegID()
	{
		return rightLegID;
	}

	public String getRightBootOutsideID()
	{
		return rightBootOutsideID;
	}

	public String getRightBootTipID()
	{
		return rightBootTipID;
	}

	public String getLeftArmID()
	{
		return leftArmID;
	}

	public String getLeftPaldronsID()
	{
		return leftPaldronsID;
	}

	public String getLeftLegID()
	{
		return leftLegID;
	}

	public String getLeftBootOutsideID()
	{
		return leftBootOutsideID;
	}

	public String getLeftBootTipID()
	{
		return leftBootTipID;
	}

	public boolean isColored()
	{
		return colored;
	}

	@Override
	public int id(ShardHelper.PlateComponent comp)
	{
		return idMap.getOrDefault(comp, 0);
	}

	@Override
	public int setId(ShardHelper.PlateComponent comp, int id)
	{
		//invalid error code 2
		var out = -2;
		if (comp.isValid(id))
		{
			//object did not exist
			out = -1;
			if(idMap.containsKey(comp))
			{
				out = idMap.get(comp);
			}
			idMap.put(comp, id);
		}
		return out;
	}

	@Override
	public String compID(ShardHelper.PlateComponent comp)
	{
		return comp.componentNameID(idMap);
	}
}
