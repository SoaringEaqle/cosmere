/*
 * File updated ~ 6 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.capabilities;

import leaf.cosmere.surgebinding.common.utils.ShardHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IShardbladeDynamicData extends INBTSerializable<CompoundTag>
{
	String getBladeID();

	String getHandleID();

	String getPommelID();

	String getCrossGuardID();

	int id(ShardHelper.BladeComponent comp);
	int setId(ShardHelper.BladeComponent comp, int id);
}
