/*
 * File updated ~ 20 - 12 - 2024 ~ Leaf
 */

package leaf.cosmere.surgebinding.common.items;

import leaf.cosmere.api.IHasGemType;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Roshar;
import leaf.cosmere.api.investiture.IInvContainer;
import leaf.cosmere.api.investiture.InvHelpers;
import leaf.cosmere.api.investiture.Transferer;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.items.InvestableItemBase;
import leaf.cosmere.common.properties.PropTypes;
import leaf.cosmere.surgebinding.common.config.SurgebindingConfigs;
import leaf.cosmere.surgebinding.common.investiture.Highstorm;
import leaf.cosmere.surgebinding.common.investiture.LightTransferer;
import leaf.cosmere.surgebinding.common.investiture.Stormlight;
import leaf.cosmere.surgebinding.common.registries.SurgebindingManifestations;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class GemstoneItem extends InvestableItemBase implements IHasGemType
{
	private final Roshar.Gemstone gemstone;
	private final Roshar.GemSize gemSize;

	public GemstoneItem(Roshar.Gemstone gemstone, Roshar.GemSize gemSize)
	{
		super(PropTypes.Items.SIXTY_FOUR.get().rarity(Rarity.UNCOMMON));
		this.gemstone = gemstone;
		this.gemSize = gemSize;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack)
	{
		return Mth.floor(10000 * getMaxChargeModifier()) * itemStack.getCount();
	}

	@Override
	public float getMaxChargeModifier()
	{
		return gemSize.getChargeModifier();
	}


	@Override
	public Roshar.Gemstone getGemType()
	{
		return gemstone;
	}

	public Roshar.GemSize getSize()
	{
		return this.gemSize;
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem)
	{
		Highstorm highstorm = new Highstorm();
		if(highstorm.isHighstorm(entityItem))
		{
				if (getCharge(stack) < getMaxCharge(stack))
				{
					//gemstones charge faster in the world
					highstorm.newInvest(getAsContainer(stack), 10);
				}
		}

		return super.onEntityItemUpdate(stack, entityItem);
	}

	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected)
	{
		Highstorm storm = new Highstorm();
		if (storm.isHighstorm(pEntity))
		{
			if (pStack.getItem() instanceof GemstoneItem gemstoneItem)
			{
				storm.newInvest(getAsContainer(pStack), 5);
			}
		}

		super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
	{
		ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

		if (pLevel.isClientSide)
		{
			return InteractionResultHolder.pass(itemStack);
		}

		SpiritwebCapability.get(pPlayer).ifPresent(spiritweb ->
		{
			SpiritwebCapability data = (SpiritwebCapability) spiritweb;
			IInvContainer playerContainer = data.getInvestitureContainer();

			boolean hasAnySurgebinding = SurgebindingManifestations.SURGEBINDING_POWERS.values().stream().anyMatch((manifestation -> spiritweb.hasManifestation(manifestation.getManifestation())));

			if (!hasAnySurgebinding)
			{
				return;
			}

			final int charge = getCharge(itemStack);

			/* Old code
			SurgebindingSpiritwebSubmodule sb = (SurgebindingSpiritwebSubmodule) data.getSubmodule(Manifestations.ManifestationTypes.SURGEBINDING);

			int playerStormlight = sb.getStormlight();

			 */

			final int maxPlayerStormlight = SurgebindingConfigs.SERVER.PLAYER_MAX_STORMLIGHT.get();

			Stormlight invest = (Stormlight) (playerContainer.findInvestiture(Manifestations.ManifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING)));
			Stormlight gemInvest = (Stormlight) (getAsContainer(itemStack).findInvestiture(Manifestations.ManifestArrayBuilder.getAllType(Manifestations.ManifestationTypes.SURGEBINDING)));


			//Get stormlight from gems
			if (!pPlayer.isCrouching())
			{
				//if charge is less than max stormlight, put all charge into player.



				final int attemptedTotal = charge + invest.getBEU();
				if (attemptedTotal <= maxPlayerStormlight)
				{
					Transferer gemTransfer = new LightTransferer(gemInvest, playerContainer, 400, 0, Integer.MAX_VALUE);
				}
				else
				{
					int remainder = attemptedTotal - maxPlayerStormlight;
					final int chargeLevelUsed = charge - remainder;
					Transferer gemTransfer = new Transferer(gemInvest, playerContainer, 400, 0, Integer.MAX_VALUE);
					gemTransfer.setKillAmount(chargeLevelUsed);
				}
			}
			//put remaining stormlight into gem.
			else
			{
				if (invest.getBEU() > 0)
				{
					if ((charge + invest.getBEU()) > getMaxCharge(itemStack))
					{
						Transferer transferer = new LightTransferer(invest, getAsContainer(itemStack), 1000, 0, Integer.MAX_VALUE);
						transferer.setKillAmount(getMaxCharge(itemStack) - charge);
					}
					else
					{
						Transferer transferer = new LightTransferer(invest, getAsContainer(itemStack), 1000, 0, Integer.MAX_VALUE);
						transferer.setKillAmount(getMaxCharge(itemStack) - charge);
					}
				}
			}
		});


		return InteractionResultHolder.consume(itemStack);
	}
}
