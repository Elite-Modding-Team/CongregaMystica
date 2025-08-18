package congregamystica.integrations.immersiveengineering.util;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class DrillStats {
    public final String repairMaterial;
    public final int drillSize;
    public final int drillDepth;
    public final int drillLevel;
    public final int drillSpeed;
    public final int drillAttack;
    public final int maxDamage;
    public final String textureLoc;
    public TextureAtlasSprite sprite;

    public DrillStats(String repairMaterial, int drillSize, int drillDepth, int drillLevel, int drillSpeed, int drillAttack, int maxDamage, String textureLoc) {
        this.repairMaterial = repairMaterial;
        this.drillSize = drillSize;
        this.drillDepth = drillDepth;
        this.drillLevel = drillLevel;
        this.drillSpeed = drillSpeed;
        this.drillAttack = drillAttack;
        this.maxDamage = maxDamage;
        this.textureLoc = textureLoc;
    }
}
