package leaf.cosmere.allomancy;

import leaf.cosmere.BaseDatapackRegistryProvider;
import leaf.cosmere.allomancy.common.Allomancy;
import leaf.cosmere.allomancy.common.registries.AllomancyDamageTypesRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class AllomancyDatapackRegistryProvider extends BaseDatapackRegistryProvider
{
	protected AllomancyDatapackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
	{
		super(output, lookupProvider, BUILDER, Allomancy.MODID);
	}

	private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.DAMAGE_TYPE, context ->
			{
				for (AllomancyDamageTypesRegistry.AllomancyDamageType damageType : AllomancyDamageTypesRegistry.DAMAGE_TYPES.values())
				{
					context.register(damageType.key(), new DamageType(damageType.getMsgId(), damageType.exhaustion()));
				}
			});
}
