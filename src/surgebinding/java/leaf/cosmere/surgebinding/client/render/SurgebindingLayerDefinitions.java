/*
 * File updated ~ 14 - 1 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.client.render;

import leaf.cosmere.surgebinding.client.render.model.ChullModel;
import leaf.cosmere.surgebinding.client.render.model.CrypticModel;
import leaf.cosmere.surgebinding.client.render.model.HonorsprenModel;
import leaf.cosmere.surgebinding.client.render.model.ShardplateModel;
import leaf.cosmere.surgebinding.common.Surgebinding;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class SurgebindingLayerDefinitions
{
	public static final ModelLayerLocation SHARDPLATE = new ModelLayerLocation(Surgebinding.rl("shardplate"), "shardplate");
	public static final ModelLayerLocation CHULL = new ModelLayerLocation(Surgebinding.rl("chull"), "chull");

	// TODO This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation CRYPTIC = new ModelLayerLocation(Surgebinding.rl("cryptic"), "cryptic");
	public static final ModelLayerLocation HONORSPREN = new ModelLayerLocation(Surgebinding.rl("honorspren"), "honorspren");

	public static void register(EntityRenderersEvent.RegisterLayerDefinitions evt)
	{
		//shardplate
		evt.registerLayerDefinition(SurgebindingLayerDefinitions.SHARDPLATE, ShardplateModel::createBodyLayer);
		evt.registerLayerDefinition(SurgebindingLayerDefinitions.CHULL, ChullModel::createBodyLayer);
		evt.registerLayerDefinition(SurgebindingLayerDefinitions.CRYPTIC, CrypticModel::createBodyLayer);
		evt.registerLayerDefinition(SurgebindingLayerDefinitions.HONORSPREN, HonorsprenModel::createBodyLayer);
	}
}
