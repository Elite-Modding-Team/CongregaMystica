package congregamystica.config.generics;

import congregamystica.utils.helpers.RegistryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.oredict.OreDictionary;

public class GolemMaterialCategory {
    @Config.RequiresMcRestart
    @Config.Name("Enable Golem Material")
    @Config.Comment("Enables this golem material.")
    public boolean enable = true;

    @Config.RequiresMcRestart
    @Config.Name("Golem Material Override")
    @Config.Comment
            ({
                    "The golem material item override. Golem materials are normally selected from the first value registered",
                    "in the ore dictionary. This overrides that value with a specific item.",
                    "Examples:",
                    "  minecraft:iron_ingot",
                    "  minecraft:iron_ingot:0"
            })
    public String materialItem = "";

    @Config.Ignore
    public String fallbackOreDict;

    @Config.Ignore
    private ItemStack stackCache = ItemStack.EMPTY;

    public GolemMaterialCategory(String fallbackOreDict) {
        this.fallbackOreDict = fallbackOreDict;
    }

    public ItemStack getMaterialStack() {
        if(this.stackCache.isEmpty()) {
            this.stackCache = RegistryHelper.getConfigStack(this.materialItem);
        }
        if(this.stackCache.isEmpty()) {
            this.stackCache = OreDictionary.getOres("ingotSteel").stream().findFirst().orElse(ItemStack.EMPTY);
        }
        return this.stackCache;
    }
}
