/*
 * File updated ~ 14 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.client.render;

import leaf.cosmere.api.Roshar;
import leaf.cosmere.client.render.CosmereRenderers;
import leaf.cosmere.common.registration.impl.ItemRegistryObject;
import leaf.cosmere.surgebinding.client.render.renderer.ArmorRenderer;
import leaf.cosmere.surgebinding.client.render.renderer.ChullRenderer;
import leaf.cosmere.surgebinding.client.render.renderer.CrypticRenderer;
import leaf.cosmere.surgebinding.client.render.renderer.HonorsprenRenderer;
import leaf.cosmere.surgebinding.common.items.DeadplateItem;
import leaf.cosmere.surgebinding.common.items.LivingplateItem;
import leaf.cosmere.surgebinding.common.registries.SurgebindingEntityTypes;
import leaf.cosmere.surgebinding.common.registries.SurgebindingItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class SurgebindingRenderers
{

	public static void register()
	{
		CosmereRenderers.register(SurgebindingItems.DEADPLATE_HELMET.get(),
				() -> new ArmorRenderer(SurgebindingItems.DEADPLATE_HELMET.get().getColour()));

		CosmereRenderers.register(SurgebindingItems.DEADPLATE_CHEST.get(),
				() -> new ArmorRenderer(SurgebindingItems.DEADPLATE_CHEST.get().getColour()));

		CosmereRenderers.register(SurgebindingItems.DEADPLATE_LEGGINGS.get(),
				() -> new ArmorRenderer(SurgebindingItems.DEADPLATE_LEGGINGS.get().getColour()));

		CosmereRenderers.register(SurgebindingItems.DEADPLATE_BOOTS.get(),
				() -> new ArmorRenderer(SurgebindingItems.DEADPLATE_BOOTS.get().getColour()));

		SurgebindingItems.SHARDPLATES.values().forEach(map ->
		{
			map.values()
					.forEach(
							item ->
									CosmereRenderers.register(item.get(), () -> new ArmorRenderer(item.get().getColour()))
					);
		});


		EntityRenderers.register(SurgebindingEntityTypes.CHULL.get(), ChullRenderer::new);
		EntityRenderers.register(SurgebindingEntityTypes.CRYPTIC.get(), CrypticRenderer::new);
		EntityRenderers.register(SurgebindingEntityTypes.HONORSPREN.get(), HonorsprenRenderer::new);

		SurgebindingItems.SHARDPLATE_SUITS.values().forEach(item ->
		{
			CosmereRenderers.register(item.get(), () -> new ArmorRenderer(item.get().getColour()));
		});
	}


}
