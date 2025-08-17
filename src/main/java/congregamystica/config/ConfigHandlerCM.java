package congregamystica.config;

import congregamystica.config.generics.GolemMaterialCategory;

public class ConfigHandlerCM {
    public static GolemCategory golems = new GolemCategory();


    public static class GolemCategory {
        public GolemMaterialCategory biomass = new GolemMaterialCategory("ingotBiomass");
        public GolemMaterialCategory livingmetal = new GolemMaterialCategory("ingotLivingmetal");
        public GolemMaterialCategory steel = new GolemMaterialCategory("ingotSteel");
        public GolemMaterialCategory treated_wood = new GolemMaterialCategory("plankTreatedWood");
    }
}
