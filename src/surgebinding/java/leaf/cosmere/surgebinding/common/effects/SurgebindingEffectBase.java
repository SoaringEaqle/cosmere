package leaf.cosmere.surgebinding.common.effects;

import leaf.cosmere.api.IHasGemType;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.cosmereEffect.CosmereEffect;

public class SurgebindingEffectBase extends CosmereEffect
{
	private Roshar.Surges surge;

	SurgebindingEffectBase(Roshar.Surges surge)
	{
		super();
		this.surge = surge;
	}

	@Override
	protected int getActiveTick()
	{
		return surge.getID();
	}

	public Roshar.Surges getSurge()
	{
		return surge;
	}
}
