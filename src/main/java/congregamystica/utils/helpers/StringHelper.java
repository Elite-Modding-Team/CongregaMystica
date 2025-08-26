package congregamystica.utils.helpers;

import com.cleanroommc.groovyscript.compat.vanilla.command.infoparser.InfoParserItem;
import congregamystica.CongregaMystica;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.AspectList;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringHelper {
    public static String getTranslationKey(String unloc, String type, String... params) {
        StringBuilder str = new StringBuilder(type + "." + CongregaMystica.MOD_ID + ":" + unloc);
        for (String param : params) {
            str.append(".").append(param);
        }
        return str.toString();
    }

    public static String getTranslatedString(String unloc, String type, String... params) {
        return I18n.format(getTranslationKey(unloc, type, params));
    }

    public static String getTranslatedString(String translationKey, Object... params) {
        return I18n.format(translationKey, params);
    }

    public static ITextComponent getTranslatedComponent(String unloc, String type, String... params) {
        return new TextComponentTranslation(getTranslationKey(unloc, type, params));
    }

    public static String getDimensionName(int dimensionId) {
        if (!DimensionManager.isDimensionRegistered(dimensionId)) {
            return Integer.toString(dimensionId);
        }
        DimensionType type = DimensionManager.getProviderType(dimensionId);
        if (type == null) {
            return Integer.toString(dimensionId);
        }
        String name = type.getName();
        int[] dims = DimensionManager.getDimensions(type);
        if (dims != null && dims.length > 1) {
            name += " " + dimensionId;
        }
        return name;
    }

    public static String getCraftTweakerIItemStackScript(ItemStack stack, AspectList aspectList) {
        String stackStr = "<" + getSharedItemString(stack) + ">";
        StringBuilder builder = new StringBuilder(stackStr);
        String withNBT = "";
        if(stack.serializeNBT().hasKey("tag")) {
            String nbt = NBTConverter.from(stack.serializeNBT().getTag("tag"), false).toString();
            if(!nbt.isEmpty())
                withNBT = ".withTag(" + nbt + ")";
        }
        builder.append(withNBT).append(".setAspects(");
        String aspectStr = Arrays.stream(aspectList.getAspects())
                .map(aspect -> aspect.getName().toLowerCase() + "*" + aspectList.getAmount(aspect))
                .collect(Collectors.joining(","));
        builder.append(aspectStr).append(");");
        return builder.toString();
    }

    public static String getGroovyScriptItemStackScript(ItemStack stack, AspectList aspectList) {
        String stackStr = InfoParserItem.instance.text(stack, false, false);
        StringBuilder builder = new StringBuilder("mods.thaumcraft.AspectHelper.aspectBuilder()");
        builder.append(".object(").append(stackStr).append(")");
        aspectList.aspects.forEach((aspect, amount) -> builder.append(".aspect('").append(aspect.getName().toLowerCase()).append("',").append(amount).append(")"));
        builder.append(".register()");
        return builder.toString();
    }

    private static String getSharedItemString(ItemStack stack) {
        StringBuilder builder = new StringBuilder(stack.getItem().getRegistryName().toString());
        if(stack.getMetadata() != 0) {
            builder.append(":").append(stack.getMetadata() == OreDictionary.WILDCARD_VALUE ? "*" : stack.getMetadata());
        }
        return builder.toString();
    }
}
