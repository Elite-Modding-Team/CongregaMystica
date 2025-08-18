package congregamystica.config.generics;

import net.minecraftforge.common.config.Config;

public class DrillHeadCategory {
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
