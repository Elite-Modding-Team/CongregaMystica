package congregamystica.integrations.crafttweaker.handlers;

import congregamystica.CongregaMystica;
import congregamystica.utils.helpers.PechHelper;
import congregamystica.utils.misc.EnumPechType;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenRegister
@ZenClass("mods." + CongregaMystica.MOD_ID + ".PechTrades")
public class PechTradesCT {
    @ZenMethod
    public static void addPechTrade(String pechType, int tradeLevel, IItemStack stack) {
        try {
            EnumPechType type = EnumPechType.valueOf(pechType);
            if(tradeLevel > 5 || tradeLevel < 1) {
                CraftTweakerAPI.logError("Invalid Pech trade level. Values can only be 1 through 5");
                return;
            }
            PechHelper.addPechTrade(type, tradeLevel, CraftTweakerMC.getItemStack(stack));
        } catch (IllegalArgumentException e) {
            CraftTweakerAPI.logError("Invalid Pech type: " + pechType + ". Values can only be 'MINER', 'MAGE', or 'ARCHER'");
        }
    }

    @ZenMethod
    public static void removePechTrade(String pechType, IIngredient ingredient) {
        try {
            EnumPechType type = EnumPechType.valueOf(pechType);
            PechHelper.removePechTrade(type, CraftTweakerMC.getIngredient(ingredient));
        } catch (IllegalArgumentException e) {
            CraftTweakerAPI.logError("Invalid Pech type: " + pechType + ". Values can only be 'MINER', 'MAGE', or 'ARCHER'");
        }
    }

    @ZenMethod
    public static void removeAllPechTrades(String pechType) {
        try {
            EnumPechType type = EnumPechType.valueOf(pechType);
            PechHelper.removeAllPechTrades(type);
        } catch (IllegalArgumentException e) {
            CraftTweakerAPI.logError("Invalid Pech type: " + pechType + ". Values can only be 'MINER', 'MAGE', 'ARCHER', or 'COMMON'");
        }
    }

    @ZenMethod
    public static void removeAllPechTrades() {
        PechHelper.removeAllPechTrades();
    }
}
