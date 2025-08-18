package congregamystica.config;

import congregamystica.config.generics.GolemMaterialCategory;
import net.minecraftforge.common.config.Config;

public class ConfigHandlerCM {
    public static GolemCategory golems = new GolemCategory();

    public static ImmersiveEngineeringCategory immersive_engineering = new ImmersiveEngineeringCategory();


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

        public static class DrillHeadCategory {
            @Config.Name("Enable Drill Head")
            public boolean enable;
            @Config.Name("Drill Area")
            public int drillSize;
            @Config.Name("Drill Level")
            public int drillLevel;
            @Config.Name("Drill Speed")
            public int drillSpeed;
            @Config.Name("Drill Attack Damage")
            public int drillAttack;
            @Config.Name("Drill Durability")
            public int drillDurability;

            public DrillHeadCategory(int drillSize, int drillLevel, int drillSpeed, int drillAttack, int drillDurability) {
                this.enable = true;
                this.drillSize = drillSize;
                this.drillLevel = drillLevel;
                this.drillSpeed = drillSpeed;
                this.drillAttack = drillAttack;
                this.drillDurability = drillDurability;
            }
        }
    }
}
