package congregamystica.integrations.harkenscythe.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;

public class GolemMaterialBiomass extends GolemMaterial implements IAddition, IProxy {
	
    public GolemMaterialBiomass() {
        super(
                "CM_BIOMASS",
                new String[] {"MATSTUDIRON"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/harkenscythe/mat_biomass.png"),
                8588557,
                16,
                6,
                3,
                ConfigHandlerCM.golems.biomass.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {}
        );
    }

    @Override
    public void preInit() {
        //TODO: Register material property. Remember to use GolemHelper#registerGolemTrait()
    }

    @Override
    public void init() {
    	register(this);
    }

    @Override
    public void registerResearchLocation() {
        //TODO: Add research
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.golems.biomass.enable;
    }
}
