package congregamystica.utils.libs;

import congregamystica.utils.helpers.ModHelper;

import javax.annotation.Nullable;

public enum ModIds {
    blood_magic(ConstIds.blood_magic),
    botania(ConstIds.botania),
    harken_scythe(ConstIds.harken_scythe),
    immersive_engineering(ConstIds.immersive_engineering),
    thaumic_wonders(ConstIds.thaumic_wonders, ConstVersions.thaumic_wonders, true, false);

    public final String modId;
    public final String version;
    public final boolean isLoaded;

    ModIds(String modId, @Nullable String version, boolean isMinVersion, boolean isMaxVersion) {
        this.modId = modId;
        this.version = version;
        this.isLoaded = ModHelper.isModLoaded(modId, version, isMinVersion, isMaxVersion);
    }

    ModIds(String modId, @Nullable String version) {
        this.modId = modId;
        this.version = version;
        this.isLoaded = ModHelper.isModLoaded(modId, version);
    }

    ModIds(String modId) {
        this(modId, null);
    }

    @Override
    public String toString() {
        return this.modId;
    }

    public static class ConstIds {
        public static final String blood_magic = "blood_magic";
        public static final String botania = "botania";
        public static final String harken_scythe = "harkenscythe";
        public static final String immersive_engineering = "immersiveengineering";
        public static final String thaumcraft = "thaumcraft";
        public static final String thaumic_wonders = "thaumicwonders";
    }

    public static class ConstVersions {
        public static final String thaumic_wonders = "2.0.0";
    }
}
