package congregamystica.integrations;

import congregamystica.api.IProxy;
import congregamystica.integrations.congregamystica.CongregaMysticaCM;
import congregamystica.integrations.examplemod.ExampleModCM;
import congregamystica.integrations.immersiveengineering.ImmersiveEngineeringCM;
import congregamystica.utils.libs.ModIds;

import java.util.ArrayList;
import java.util.List;

public class InitIntegrations {
    private static List<IProxy> MOD_ADDITIONS;

    private static void initModAdditions() {
        //Mod integration modules are initialized and added to the MOD_ADDITIONS list here. The array is initialized prior to this method call.

        //When adding an integration, check that the mod or mods are loaded then add it to the MOD_ADDITIONS. Specific item checks should be included
        //in the specific mod integration IProxy class.
        if(ModIds.exampleMod.isLoaded) {
            MOD_ADDITIONS.add(new ExampleModCM());
        }
        
        MOD_ADDITIONS.add(new CongregaMysticaCM());
        
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
