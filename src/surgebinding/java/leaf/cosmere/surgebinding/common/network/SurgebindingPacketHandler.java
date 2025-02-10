/*
 * File updated ~ 4 - 2 - 2025 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.network;

import leaf.cosmere.common.network.BasePacketHandler;
import leaf.cosmere.surgebinding.common.Surgebinding;
import leaf.cosmere.surgebinding.common.network.packets.BreatheStormlight;
import leaf.cosmere.surgebinding.common.network.packets.DrawStormlight;
import leaf.cosmere.surgebinding.common.network.packets.SummonShardblade;
import net.minecraftforge.network.simple.SimpleChannel;

public class SurgebindingPacketHandler extends BasePacketHandler
{
	private final SimpleChannel NETWORK_CHANNEL = createChannel(Surgebinding.rl(Surgebinding.MODID), Surgebinding.instance.versionNumber);

	@Override
	protected SimpleChannel getChannel()
	{
		return NETWORK_CHANNEL;
	}

	@Override
	public void initialize()
	{
		registerClientToServer(SummonShardblade.class, SummonShardblade::new);
		registerClientToServer(BreatheStormlight.class, BreatheStormlight::new);
		registerClientToServer(DrawStormlight.class, DrawStormlight::new);
	}


}
