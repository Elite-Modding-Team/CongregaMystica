package congregamystica.config;

import congregamystica.config.generics.DrillHeadCategory;
import congregamystica.config.generics.GolemMaterialCategory;
import net.minecraftforge.common.config.Config;

public class ConfigHandlerCM {
    public static GolemCategory golems = new GolemCategory();

    public static ClustersCategory clusters = new ClustersCategory();

    @Config.Name("Blood Magic")
    public static BloodMagicCategory blood_magic = new BloodMagicCategory();
    @Config.Name("Immersive Engineering")
    public static ImmersiveEngineeringCategory immersive_engineering = new ImmersiveEngineeringCategory();

    public static class ClustersCategory {
        @Config.Name("Register Smelting Bonus")
        @Config.Comment("Registers infernal smelting metal nugget bonuses when smelting ores associated with new native clusters.")
        public boolean registerSmeltingBonuses = true;

        @Config.RequiresMcRestart
        @Config.Name("Additional Cluster Types")
        @Config.Comment
                ({
                        "Additional Native Clusters that will be registered.",
                        "If the color code value is excluded, a non-dynamic cluster will be registered, requiring the manual",
                        "creation of a model and texture file loaded from a resource pack.",
                        "",
                        "Examples: (oreDictValue;colorcode)",
                        "  oreUranium;0x79896b",
                        "  oreUranium"
                })
        public String[] additionalClusters = new String[] {
                //General Ores
                "oreAluminum;ingot;0xefeff1",
                "oreNickel;ingot;0x9ea59e",
                "oreUranium;ingot;0x57744b",
                "orePlatinum;ingot;0xc6d5f2",
                "oreIridium;ingot;0xbdbed1",
                //Thermal Foundation Ores
                "oreMithril;ingot;0x5cd6ff", //aka Mana Infused
                //Tinkers Construct Ores
                "oreCobalt;ingot;0x2e7ce6",
                "oreArdite;ingot;0xe5b740",

                "oreEmerald;gem;0xffffff"
        };
    }

    public static class BloodMagicCategory {
        @Config.Name("Bloody Scrivener's Tools")
        public BloodScribingToolsCategory bloodyScribingTools = new BloodScribingToolsCategory();

        public static class BloodScribingToolsCategory {
            @Config.RequiresMcRestart
            @Config.Name("Enable Bloody Scrivener's Tools")
            public boolean enable = true;

            @Config.RangeInt(min = 1, max = 10000)
            @Config.Name("LP Cost")
            @Config.Comment("The amount of LP drained from the player's soul network per use.")
            public int lpCost = 100;
        }
    }

    public static class GolemCategory {
        public GolemMaterialCategory biomass = new GolemMaterialCategory("ingotBiomass");
        public GolemMaterialCategory livingmetal = new GolemMaterialCategory("ingotLivingmetal");
        public GolemMaterialCategory steel = new GolemMaterialCategory("ingotSteel");
        public GolemMaterialCategory treated_wood = new GolemMaterialCategory("plankTreatedWood");
    }

    public static class ImmersiveEngineeringCategory {
        @Config.Name("Refining Drill Upgrade")
        public RefiningUpgradeCategory refiningUpgrade = new RefiningUpgradeCategory();

        @Config.RequiresMcRestart
        @Config.Name("Thaumium Drill Head")
        public DrillHeadCategory thaumiumDrillHead = new DrillHeadCategory(3, 2, 12, 6, 6000);

        @Config.RequiresMcRestart
        @Config.Name("Void Metal Drill Head")
        public DrillHeadCategory voidDrillHead = new DrillHeadCategory(5, 3, 8, 7, 8000);

        public static class RefiningUpgradeCategory {
            @Config.RequiresMcRestart
            @Config.Name("Enable Refining Upgrade")
            @Config.Comment("Enables the Refining drill upgrade. This upgrade has a chance to convert harvested ores into native clusters.")
            public boolean enable = true;

            @Config.RangeInt(min = 1, max = 10)
            @Config.Name("Maximum Upgrades")
            @Config.Comment("The maximum number of refining upgrades that can be stacked in a drill at once. Each upgrade increases the chance of a native cluster drop by 10%.")
            public int maxUpgrades = 4;
        }
    }
}
