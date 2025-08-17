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

//Because this feature is not a block or item, it should extend the IProxy so it can be registered correctly.
public class GolemMaterialLivingmetal extends GolemMaterial implements IAddition, IProxy {
	
    public GolemMaterialLivingmetal() {
        super(
                "CM_LIVINGMETAL",
                new String[] {"MATSTUDIRON"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/harkenscythe/mat_livingmetal.png"),
                46030,
                16,
                6,
                3,
                ConfigHandlerCM.golems.livingmetal.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {}
        );
    }

    @Override
    public void init() {
    	register(this);
    }

    @Override
    public void registerResearchLocation() {
        //Register associated research here
    }

    @Override
    public boolean isEnabled() {
        //Configuration or OreDict toggles here
        return ConfigHandlerCM.golems.livingmetal.enable;
    }
}
