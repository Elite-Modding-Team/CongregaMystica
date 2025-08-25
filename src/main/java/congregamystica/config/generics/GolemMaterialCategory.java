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

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 100)
    @Config.Name("Material Armor")
    @Config.Comment("The base golem material armor value.")
    public int statArmor;

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 100)
    @Config.Name("Material Damage")
    @Config.Comment("The base golem material damage value.")
    public int statDamage;

    @Config.RequiresMcRestart
    @Config.RangeInt(min = 0, max = 100)
    @Config.Name("Material Health")
    @Config.Comment("The base golem material health value. Actual golem health will be this value plus 10.")
    public int statHealth;

    @Config.Ignore
    public String fallbackOreDict;

    @Config.Ignore
    private ItemStack stackCache = ItemStack.EMPTY;

    public GolemMaterialCategory(int health, int armor, int damage, String fallbackOreDict) {
        this.statArmor = armor;
        this.statDamage = damage;
        this.statHealth = health;
        this.fallbackOreDict = fallbackOreDict;
    }

    public ItemStack getMaterialStack() {
        if(this.stackCache.isEmpty()) {
            this.stackCache = RegistryHelper.getConfigStack(this.materialItem);
        }
        if(this.stackCache.isEmpty()) {
            this.stackCache = OreDictionary.getOres(this.fallbackOreDict).stream().findFirst().orElse(ItemStack.EMPTY);
            if(this.stackCache.getItemDamage() == Short.MAX_VALUE) {
                this.stackCache.setItemDamage(0);
            }
        }
        return this.stackCache;
    }
}
