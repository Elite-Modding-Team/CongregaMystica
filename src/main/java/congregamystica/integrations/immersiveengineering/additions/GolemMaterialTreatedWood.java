package congregamystica.integrations.immersiveengineering.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;

//Because this feature is not a block or item, it should extend the IProxy so it can be registered correctly.
public class GolemMaterialTreatedWood extends GolemMaterial implements IAddition, IProxy {
	
    public GolemMaterialTreatedWood() {
        super(
                "CM_TREATED_WOOD",
                new String[] {"MATSTUDIRON"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/immersiveengineering/mat_treated_wood.png"),
                6566425,
                6,
                2,
                1,
                ConfigHandlerCM.golems.treated_wood.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {EnumGolemTrait.DEFT}
        );
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
        //Configuration or OreDict toggles here
        return ConfigHandlerCM.golems.treated_wood.enable;
    }
}
