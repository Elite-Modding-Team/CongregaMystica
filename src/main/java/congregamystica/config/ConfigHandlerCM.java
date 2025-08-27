package congregamystica.config;

import congregamystica.config.generics.CasterTypeCategory;
import congregamystica.config.generics.DrillHeadCategory;
import congregamystica.config.generics.GolemMaterialCategory;
import net.minecraftforge.common.config.Config;

//TODO: Remove comment before release
// @Mod(modid = CongregaMystica.MOD_ID)
public class ConfigHandlerCM {
    @Config.Name("Native Clusters")
    public static ClustersCategory clusters = new ClustersCategory();
    @Config.Name("Caster Gauntlets")
    public static CasterCategory casters = new CasterCategory();
    @Config.Name("Golem Materials")
    public static GolemCategory golems = new GolemCategory();


    @Config.Name("Blood Magic")
    public static BloodMagicCategory blood_magic = new BloodMagicCategory();
    @Config.Name("Botania")
    public static BotaniaCategory botania = new BotaniaCategory();
    @Config.Name("Immersive Engineering")
    public static ImmersiveEngineeringCategory immersive_engineering = new ImmersiveEngineeringCategory();
    @Config.Name("Rustic")
    public static RusticCategory rustic = new RusticCategory();
    @Config.Name("The One Probe")
    public static TheOneProbeCategory the_one_probe = new TheOneProbeCategory();

    public static class BloodMagicCategory {
        @Config.Name("Bloody Scrivener's Tools")
        public BloodScribingToolsCategory bloodyScribingTools = new BloodScribingToolsCategory();
        @Config.RequiresMcRestart
        @Config.Name("Eldritch Blood Orb")
        public EldritchOrbCategory eldritchOrb = new EldritchOrbCategory();

        public static class BloodScribingToolsCategory {
            @Config.RequiresMcRestart
            @Config.Name("Enable Bloody Scrivener's Tools")
            public boolean enable = true;

            @Config.RangeInt(min = 1, max = 10000)
            @Config.Name("LP Cost")
            @Config.Comment("The amount of LP drained from the player's soul network per use.")
            public int lpCost = 100;
        }

        public static class EldritchOrbCategory {
            @Config.Name("Enable Eldritch Blood Orb")
            @Config.Comment("Enables the high capacity end-game blood orb.")
            public boolean enable = true;

            @Config.Name("Eldritch Orb Capacity")
            @Config.Comment("The Eldritch Blood Orb soul network capacity")
            public int capacity = 50000000;

            @Config.RangeInt(min = 1, max = 6)
            @Config.Name("Required Altar Tier")
            @Config.Comment("The Blood Atlar tier required to fill the Eldritch Blood Orb.")
            public int tier = 5;

            @Config.RangeInt(min = 1, max = 10000)
            @Config.Name("Eldritch Orb Fill Rate")
            @Config.Comment("The speed the Eldritch Orb can drain LP from the Blood Altar. A drain rate of 50 is equal to the Archmage Blood Orb.")
            public int drainRate = 100;
        }
    }

    public static class BotaniaCategory {
        @Config.Name("Taintthistle")
        public TaintthistleCategory taintthistle = new TaintthistleCategory();
        @Config.Name("Whisperweed")
        public WhisperweedCategory whisperweed = new WhisperweedCategory();

        public static class TaintthistleCategory {
            @Config.RequiresMcRestart
            @Config.Name("Enable Taintthistle")
            @Config.Comment("Enables the Taintthistle, a functional flower that uses mana to destroy nearby taint blocks.")
            public boolean enable = true;

            @Config.RequiresMcRestart
            @Config.RangeInt(min = 0, max = 100000)
            @Config.Name("Mana cost")
            @Config.Comment("The amount of mana consumed for every taint block the Taintthistle destroys.")
            public int manaCost = 800;

            @Config.RangeInt(min = 1, max = 1000)
            @Config.Name("Taint Blocks Destroyed")
            @Config.Comment("The maximum number of taint blocks destroyed every operation of the flower.")
            public int taintLimit = 3;
        }

        public static class WhisperweedCategory {
            @Config.RequiresMcRestart
            @Config.Name("Enable Whsiperweed")
            @Config.Comment("")
            public boolean enable = true;

            @Config.RequiresMcRestart
            @Config.RangeInt(min = 1, max = 100000)
            @Config.Name("Mana Cost")
            @Config.Comment
                    ({
                            "The amount of mana consumed every time the Whispwerweed consumes a Zombie Brain. Consuming",
                            "operations occur approximately every 15 seconds."
                    })
            public int manaCost = 300;

            @Config.RequiresMcRestart
            @Config.RangeInt(min = 1, max = 1000)
            @Config.Name("Brains Required")
            @Config.Comment("The number of Zombie Brains required before the Whisperweed can grant research knowledge.")
            public int progressReq = 20;
        }
    }

    public static class CasterCategory {
        @Config.Name("Bound Caster's Gauntlet")
        @Config.Comment("Configuration options for the Blood Magic LP-powered casting gauntlet.")
        public CasterTypeCategory bound = new CasterTypeCategory(400);
    }

    public static class ClustersCategory {
        @Config.RequiresMcRestart
        @Config.Name("Register Gem Ore Refining")
        @Config.Comment
                ({
                        "Enables Crucible gem ore refining, registering the Crucible recipe that refines gem ores into clusters.",
                        "A value of false will mirror the behavior of Native Quartz Clusters."
                })
        public boolean registerGemCrucibleRefining = false;

        @Config.RequiresMcRestart
        @Config.Name("Register Smelting Bonus")
        @Config.Comment("Registers infernal smelting metal nugget bonuses when smelting ores associated with new native clusters.")
        public boolean registerSmeltingBonuses = true;

        @Config.RequiresMcRestart
        @Config.Name("Register Pech Trades")
        @Config.Comment("Native Clusters added by this mod will be included on the Pech Forager trade table.")
        public boolean registerPechTrades = true;

        @Config.RequiresMcRestart
        @Config.Name("Special Gem Harvest")
        @Config.Comment
                ({
                        "Registered cluster types with an output type of 'gem' have a chance be converted into Native Clusters when",
                        "harvested by tools with refining. A value of true will mirror the behavior of Native Quartz Clusters."
                })
        public boolean specialGemHarvest = true;

        @Config.RequiresMcRestart
        @Config.Name("Additional Cluster Types")
        @Config.Comment
                ({
                        "Additional Native Clusters that will be registered. If Thaumic Wonders Unofficial is installed",
                        "Eldritch Cluster variants will also be registered.",
                        "Format:",
                        "  oreblock;outputtype;colorcode",
                        "  oreblock;outputtype",
                        "",
                        "  oreblock - The ore block oredictionary value. This value is case sensitive.",
                        "  outputtype - The output oredict type (ingotIron = ingot, gemDiamond = gem, et cetera)",
                        "  colorcode - (optional) The hexidecimal RBG color color code used for dynamic generation.",
                        "",
                        "If the color code value is excluded from a cluster, a non-dynamic cluster will be registered, requiring",
                        "manual creation of a model and texture file.",
                        "",
                        "Due to the dynamic nature of these clusters, occasionally the cluster display name will be incorrect.",
                        "You can override the default name by adding the cluster's translation key to a language file."
                })
        public String[] additionalClusters = new String[] {
                //Vanilla Ores
                "oreDiamond;gem",
                "oreEmerald;gem",
                //Thaumcraft Ores
                "oreAmber;gem",
                //General Ores
                "oreAluminum;ingot;0xefeff1",
                "oreNickel;ingot;0x9ea59e",
                "oreUranium;ingot;0x57744b",
                "orePlatinum;ingot;0xc6d5f2",
                "oreIridium;ingot;0xbdbed1",
                //"oreTitanium;ingot;0x3a4e70",
                //Applied Energistics 2
                "oreQuartzCertus;gem",
                "oreChargedQuartzCertus;gem",
                //Astral Sorcery Ores
                "oreAstralStarmetal;ingot",
                //Mekanism
                "oreOsmium;ingot;0xb5cacc",
                //Mystical World
                "oreAmethyst;gem",
                //Project Red
                "orePeridot;gem",
                "oreRuby;gem",
                "oreSapphire;gem",
                //Thermal Foundation Ores
                "oreMithril;ingot;0x5cd6ff", //aka Mana Infused
                //Tinkers Construct Ores
                "oreCobalt;ingot;0x2e7ce6",
                "oreArdite;ingot;0xe5b740",
                };
    }

    public static class GolemCategory {
        @Config.Name("Harken Scythe Biomass")
        public GolemMaterialCategory biomass = new GolemMaterialCategory(20, 8, 5, "ingotBiomass");
        @Config.Name("Rustic Ironwood")
        public GolemMaterialCategory ironWood = new GolemMaterialCategory(6, 2, 1, "plankIronwood");
        @Config.Name("Harken Scythe Livingmetal")
        public GolemMaterialCategory livingmetal = new GolemMaterialCategory(20, 8, 5, "ingotLivingmetal");
        @Config.Name("Steel")
        public GolemMaterialCategory steel = new GolemMaterialCategory( 20, 14, 6,"ingotSteel");
        @Config.Name("Immersive Engineering Treated Wood")
        public GolemMaterialCategory treatedWood = new GolemMaterialCategory(6, 2, 1,"plankTreatedWood");
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

    public static class RusticCategory {
        @Config.RequiresMcRestart
        @Config.Name("Enable Brass Features")
        @Config.Comment("Enables brass features such as candles, chandeliers and chains.")
        public boolean enableBrassFeatures = true;

        @Config.RequiresMcRestart
        @Config.Name("Enable Furniture")
        @Config.Comment("Enables chairs and tables that use Thaumcraft's greatwood and silverwood materials.")
        public boolean enableFurniture = true;
    }

    public static class TheOneProbeCategory {
        @Config.RequiresMcRestart
        @Config.Name("Brain in a Jar")
        @Config.Comment("Enables Brain in a Jar The One Probe progress info.")
        public boolean brainJar = true;

        @Config.RequiresMcRestart
        @Config.Name("Vis Battery")
        @Config.Comment("Enables Vis Battery The One Probe storage info.")
        public boolean visBattery = true;

        @Config.RequiresMcRestart
        @Config.Name("Vis Generator")
        @Config.Comment("Enables Vis Generator The One Probe rf storage info.")
        public boolean visGenerator = true;

        @Config.RequiresMcRestart
        @Config.Name("Void Siphon")
        @Config.Comment("Enables Void Siphon The One Probe progress info.")
        public boolean voidSiphon = true;
    }
}
