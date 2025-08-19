package congregamystica.integrations;

import congregamystica.api.IProxy;
import congregamystica.integrations.bloodmagic.BloodMagicCM;
import congregamystica.integrations.congregamystica.CongregaMysticaCM;
import congregamystica.integrations.examplemod.ExampleModCM;
import congregamystica.integrations.harkenscythe.HarkenScytheCM;
import congregamystica.integrations.immersiveengineering.ImmersiveEngineeringCM;
import congregamystica.integrations.thaumicwonders.ThaumicWondersCM;
import congregamystica.utils.libs.ModIds;

import java.util.ArrayList;
import java.util.List;

public class InitIntegrations {
    private static List<IProxy> MOD_ADDITIONS;

    private static void initModAdditions() {
        //The built-in additions should register first
        MOD_ADDITIONS.add(new CongregaMysticaCM());
        //Thaumic Wonders registers second to keep the clusters together
        if(ModIds.thaumic_wonders.isLoaded) {
            MOD_ADDITIONS.add(new ThaumicWondersCM());
        }

        //Mod integration modules are initialized and added to the MOD_ADDITIONS list here. The array is initialized prior to this method call.

        //When adding an integration, check that the mod or mods are loaded then add it to the MOD_ADDITIONS. Specific item checks should be included
        //in the specific mod integration IProxy class.
        if(ModIds.exampleMod.isLoaded) {
            MOD_ADDITIONS.add(new ExampleModCM());
        }

        if(ModIds.blood_magic.isLoaded) {
            MOD_ADDITIONS.add(new BloodMagicCM());
        }
        
        if(ModIds.harken_scythe.isLoaded) {
            MOD_ADDITIONS.add(new HarkenScytheCM());
        }
        
        if(ModIds.immersive_engineering.isLoaded) {
            MOD_ADDITIONS.add(new ImmersiveEngineeringCM());
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
