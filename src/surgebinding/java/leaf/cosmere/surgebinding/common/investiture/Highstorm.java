package leaf.cosmere.surgebinding.common.investiture;

import leaf.cosmere.api.investiture.IInvContainer;
import leaf.cosmere.api.investiture.IInvCreator;
import leaf.cosmere.api.investiture.Investiture;
import net.minecraft.world.phys.Vec3;

public class Highstorm implements IInvCreator
{
	public Highstorm()
	{

	}

	public boolean isHighstorm(Vec3 position)
	{
		//todo: Highstorm Logic
		return true;
	}

	@Override
	public Investiture newInvest(IInvContainer<?> data)
	{
		Investiture investiture = new Stormlight(data, 500);
		return investiture;
	}
}
