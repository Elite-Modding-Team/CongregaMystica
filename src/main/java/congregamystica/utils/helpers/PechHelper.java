package congregamystica.utils.helpers;

import congregamystica.utils.misc.EnumPechType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import thaumcraft.common.entities.monster.EntityPech;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("rawtypes")
public class PechHelper {
    public static void addPechTrade(EnumPechType pechType, int trustLevel, ItemStack stack) {
        ArrayList<List> trades = getPechTrades(pechType);
        if(trades == null) {
            getPechTrades().put(pechType.ordinal(), new ArrayList<>());
            addPechTrade(pechType, trustLevel, stack);
        } else {
            trades.add(Arrays.asList(trustLevel, stack));
        }
    }

    public static void removePechTrade(EnumPechType pechType, Ingredient ingredient) {
        try {
            ArrayList<List> trades = getPechTrades(pechType);
            if(trades != null) {
                trades.removeIf(list -> ingredient.apply((ItemStack) list.get(1)));
            }
        } catch (Exception e) {
            LogHelper.error("Failed to remove pech trade. Thaumcraft is terrible and I hate it.");
        }
    }

    private static HashMap<Integer, ArrayList<List>> getPechTrades() {
        return EntityPech.tradeInventory;
    }

    @Nullable
    private static  ArrayList<List> getPechTrades(EnumPechType pechType) {
        return getPechTrades().get(pechType.ordinal());
    }
}
