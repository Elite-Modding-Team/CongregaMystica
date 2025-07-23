package congregamystica.integrations;

import congregamystica.api.IProxy;

import java.util.ArrayList;
import java.util.List;

public class InitIntegrations {
    private static List<IProxy> MOD_ADDITIONS;

    private static void initModAdditions() {
        //Mod integration modules are initialized and added to the MOD_ADDITIONS list here.
    }

    public static List<IProxy> getModAdditions() {
        if(MOD_ADDITIONS == null) {
            MOD_ADDITIONS = new ArrayList<>();
            initModAdditions();
        }
        return MOD_ADDITIONS;
    }
}
