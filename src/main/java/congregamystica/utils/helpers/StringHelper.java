package congregamystica.utils.helpers;

import congregamystica.CongregaMystica;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

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

    public static ITextComponent getTranslatedComponent(String unloc, String type, String... params) {
        return new TextComponentTranslation(getTranslationKey(unloc, type, params));
    }


}
