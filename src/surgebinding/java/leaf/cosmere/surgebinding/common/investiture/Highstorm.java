package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.investiture.IInvContainer;
import leaf.cosmere.api.investiture.IInvCreator;
import leaf.cosmere.api.investiture.InvHelpers;
import leaf.cosmere.api.investiture.Investiture;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.surgebinding.common.registries.SurgebindingDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Highstorm implements IInvCreator
{
	double x;
	public Highstorm(double x)
	{
		this.x = x;
	}

	//temp
	public Highstorm()
	{

	}
/*
	public boolean isHighstorm(Entity entity)
	{
		if(entity.level().dimension().equals(SurgebindingDimensions.ROSHAR_DIM_KEY))
		{
			if(Math.abs(entity.position().x - this.x) < 20.0)
			{
				return true;
			}
		}
	}
	*/
	public boolean isHighstorm(Entity entity)
	{
		if (entity.level().dimension().equals(SurgebindingDimensions.ROSHAR_DIM_KEY))
		{
			if (entity.level().isRainingAt(entity.blockPosition()) && entity.level().isThundering())
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public Investiture newInvest(IInvContainer data)
	{
		Investiture investiture = new Stormlight(data, 500);
		return investiture;
	}

	@Override
	public Investiture newInvest(IInvContainer data, int beu, int decay)
	{
		Investiture out = new Stormlight(data, beu);
		out.setDecayRate(decay);
		return out;
	}

	@Override
	public Investiture newInvest(IInvContainer data, int beu)
	{
		return new Stormlight(data, beu);
	}
}
