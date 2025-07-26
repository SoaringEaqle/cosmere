package leaf.cosmere.api.investiture;

import leaf.cosmere.api.helpers.StackNBTHelper;
import leaf.cosmere.api.manifestation.Manifestation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public interface IInvestitureItemContainer extends IInvestitureContainer
{

	static ArrayList<Investiture> availableInvestitures(ItemStack stack, Manifestation manifest)
	{
		ListTag tag = StackNBTHelper.getList(stack, "investitures", 0, true);
		ArrayList<Investiture> investitures = new ArrayList<>();
		for(int i = 0; i < tag.size(); i++)
		{
			Investiture invest = Investiture.buildFromNBT((CompoundTag) tag.get(i), this);
			if(invest.isUsable(manifest))
			{
				investitures.add(invest);
			}
		}
	}

	static int handleUseInvestiture(ItemStack stack, Manifestation manifest)
	{
		ArrayList<Investiture> availibles = availableInvestitures(stack, manifest);
		int totalInvestiture = 0;
		for(int i = 5; i > 0; i--)
		{
			int finalI = i;
			List<Investiture> current = availibles.stream().filter(investiture -> finalI == investiture.getPriority()).toList();
			int subTotal = current.stream().mapToInt(Investiture::getBEU).sum();
			if(subTotal + totalInvestiture >= manifest.maxInvestitureDraw(this))
			{
				int quo = (manifest.maxInvestitureDraw(this) - totalInvestiture) / current.size();
				int mod = (manifest.maxInvestitureDraw(this) - totalInvestiture) % current.size();
				current.forEach(invest -> invest.removeBEU(quo));
				current.get(0).removeBEU(mod);
				totalInvestiture =  manifest.maxInvestitureDraw(this);
				break;
			}
			else
			{
				int quo = subTotal / current.size();
				int mod = subTotal % current.size();
				current.forEach(invest -> invest.removeBEU(quo));
				current.get(0).removeBEU(mod);
				totalInvestiture += subTotal;
			}
		}
		return totalInvestiture;
	}


	static Investiture findInvestiture(ItemStack stack, Manifestation[] appManifest)
	{
		return null;
	}

	static boolean hasInvestiture(ItemStack stack, Investiture investiture)
	{
		ListTag tag = StackNBTHelper.getList(stack, "investitures", 0, true);
		for(int i = 0; i < tag.size(); i++)
		{
			Investiture invest = Investiture.buildFromNBT((CompoundTag) tag.get(i), this);
			if(invest == investiture)
			{
				return true;
			}
		}
		return false;
	}

	static void clearInvestiture(ItemStack stack, Investiture investiture)
	{
		ListTag tag = StackNBTHelper.getList(stack, "investitures", 0, true);
		for(int i = 0; i < tag.size(); i++)
		{
			Investiture invest = Investiture.buildFromNBT((CompoundTag) tag.get(i), this);
			if(invest == investiture)
			{
				tag.remove(i);
				return;
			}
		}
	}


	void mergeOrAddInvestiture(ItemStack stack, IInvestiture invest);



}
