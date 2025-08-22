package congregamystica.aspects;

import com.cleanroommc.groovyscript.compat.vanilla.command.infoparser.InfoParserItem;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.AspectList;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AspectScriptGenerator {


    public void run() {

    }

    public String getCraftTweakerIItemStackScript(ItemStack stack, AspectList aspectList) {
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

    public String getGroovyScriptItemStackScript(ItemStack stack, AspectList aspectList) {
        String stackStr = InfoParserItem.instance.text(stack, false, false);
        StringBuilder builder = new StringBuilder("mods.thaumcraft.AspectHelper.aspectBuilder()");
        builder.append(".object(").append(stackStr).append(")");
        aspectList.aspects.forEach((aspect, amount) -> {
            builder.append(".aspect('").append(aspect.getName().toLowerCase()).append("',").append(amount).append(")");
        });
        builder.append(".register()");
        return builder.toString();
    }

    public String getSharedItemString(ItemStack stack) {
        StringBuilder builder = new StringBuilder(stack.getItem().getRegistryName().toString());
        if(stack.getMetadata() != 0) {
            builder.append(":").append(stack.getMetadata() == OreDictionary.WILDCARD_VALUE ? "*" : stack.getMetadata());
        }
        return builder.toString();
    }
}
