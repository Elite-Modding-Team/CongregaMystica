package congregamystica.config.generics;

import net.minecraftforge.common.config.Config;

public class CasterTypeCategory {
    @Config.RequiresMcRestart
    @Config.Name("Enable Caster Gauntlet")
    @Config.Comment("Enables this alternate resource caster gauntlet.")
    public boolean enable = true;

    @Config.RangeDouble(min = 0, max = 1.0)
    @Config.Name("Alternate Resource Modifier")
    @Config.Comment
            ({
                    "The modifier that determines the amount of Vis that will be removed from the spell cast and converted",
                    "into an alternate resource cost. A value of 0.5 will decrease the Vis cost by 50%. A value of 1.0 will",
                    "cause the caster to consume 0 Vis when casting a spell."
            })
    public double consumptionModifier = 0.5;

    @Config.RangeDouble(min = 0, max = 1000000.0)
    @Config.Name("Alternate Resource Conversion")
    @Config.Comment("The amount of alternate resource consumed per point of Vis.")
    public double conversionRate;

    @Config.RangeInt(min = 0, max = 10)
    @Config.Name("Vis Drain Radius")
    @Config.Comment
            ({
                    "The radius this gauntlet can pull vis from when casting a spell. The number of chunks determined by",
                    "this value is equal to ((radius * 2) + 1) ^ 2. A value of 0 = 1 chunk, 1 = 9 chunks, 2 = 25 chunks..."
            })
    public int visDrainRadius = 0;

    public CasterTypeCategory(double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
