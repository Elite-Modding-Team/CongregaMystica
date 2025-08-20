package congregamystica.integrations;

import congregamystica.api.IProxy;
import congregamystica.integrations.appliedenergistics2.AppliedEnergisticsCM;
import congregamystica.integrations.bloodmagic.BloodMagicCM;
import congregamystica.integrations.congregamystica.CongregaMysticaCM;
import congregamystica.integrations.crafttweaker.CrafttweakerCM;
import congregamystica.integrations.harkenscythe.HarkenScytheCM;
import congregamystica.integrations.immersiveengineering.ImmersiveEngineeringCM;
import congregamystica.integrations.thaumicwonders.ThaumicWondersCM;
import congregamystica.integrations.theoneprobe.TheOneProbeCM;
import congregamystica.utils.libs.ModIds;

import java.util.ArrayList;
import java.util.List;

public class InitIntegrations {
    private static List<IProxy> MOD_ADDITIONS;

    private static void initModAdditions() {
        //The built-in additions should register first
        MOD_ADDITIONS.add(new CongregaMysticaCM());
        if(ModIds.applied_energistics.isLoaded) {
            MOD_ADDITIONS.add(new AppliedEnergisticsCM());
        }
        if(ModIds.blood_magic.isLoaded) {
            MOD_ADDITIONS.add(new BloodMagicCM());
        }
        if(ModIds.crafttweaker.isLoaded) {
            MOD_ADDITIONS.add(new CrafttweakerCM());
        }
        if(ModIds.harken_scythe.isLoaded) {
            MOD_ADDITIONS.add(new HarkenScytheCM());
        }
        if(ModIds.immersive_engineering.isLoaded) {
            MOD_ADDITIONS.add(new ImmersiveEngineeringCM());
        }
        if(ModIds.thaumic_wonders.isLoaded) {
            MOD_ADDITIONS.add(new ThaumicWondersCM());
        }
        if(ModIds.the_one_probe.isLoaded) {
            MOD_ADDITIONS.add(new TheOneProbeCM());
        }

    }

    public static List<IProxy> getModAdditions() {
        if (MOD_ADDITIONS == null) {
            MOD_ADDITIONS = new ArrayList<>();
            initModAdditions();
        }
        return MOD_ADDITIONS;
    }
}
