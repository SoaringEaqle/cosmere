package leaf.cosmere.surgebinding.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlayerTransportationStandin extends Entity
{
	public Player realPlayer;

	public PlayerTransportationStandin(EntityType<?> type, Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	@Override
	protected void defineSynchedData() {}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {}

	@Override
	public boolean isInvisible() { return true; }

	@Override
	public boolean isNoGravity() { return true; }

	// allow directly setting motion and applying a move (client-side only)
	public void applyClientMotion(Vec3 motion) {
		this.setDeltaMovement(motion);
		this.move(net.minecraft.world.entity.MoverType.SELF, this.getDeltaMovement());
	}
}
