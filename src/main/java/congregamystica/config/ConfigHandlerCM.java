package congregamystica.config;

import congregamystica.config.generics.GolemMaterialCategory;

public class ConfigHandlerCM {
    public static GolemCategory golems = new GolemCategory();


    public static class GolemCategory {
        public GolemMaterialCategory steel = new GolemMaterialCategory("ingotSteel");
        public GolemMaterialCategory treated_wood = new GolemMaterialCategory("plankTreatedWood");
    }
}
