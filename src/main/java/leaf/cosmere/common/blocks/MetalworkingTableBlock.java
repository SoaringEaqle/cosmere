/*
 * File updated ~ 20 - 3 - 2022 ~ Leaf
 */

package leaf.cosmere.common.blocks;

import leaf.cosmere.client.gui.MetalworkingTableMenu;
import leaf.cosmere.common.properties.PropTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class MetalworkingTableBlock extends BaseBlock
{
	public MetalworkingTableBlock()
	{
		super(PropTypes.Blocks.METAL.get(), SoundType.METAL, 1F, 2F);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos,
	                             Player player, InteractionHand hand, BlockHitResult hit)
	{
		if (!level.isClientSide)
		{
			NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider()
			{
				@Override
				public Component getDisplayName()
				{
					return Component.translatable("block.cosmere.metalworking_table");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inv, Player p)
				{
					return new MetalworkingTableMenu(id, inv, pos);
				}
			}, buf -> buf.writeLong(pos.asLong()));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
}
