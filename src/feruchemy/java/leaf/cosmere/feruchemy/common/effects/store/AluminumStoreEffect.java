/*
 * File updated ~ 29 - 10 - 2023 ~ Leaf
 */

package leaf.cosmere.feruchemy.common.effects.store;

import leaf.cosmere.api.Metals;
import leaf.cosmere.common.registry.AttributesRegistry;
import leaf.cosmere.feruchemy.common.effects.FeruchemyEffectBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

//luck
public class AluminumStoreEffect extends FeruchemyEffectBase
{
	public AluminumStoreEffect(Metals.MetalType type)
	{
		super(type);

		addAttributeModifier(
				AttributesRegistry.IDENTITY.getAttribute(),
				-1.0D,
				AttributeModifier.Operation.ADDITION);
	}
}
