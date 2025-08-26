package congregamystica.integrations.rustic.golems;

import congregamystica.CongregaMystica;
import congregamystica.api.golem.IGolemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;

public class GolemMaterialIronwood implements IGolemAddition {
    @Override
    public String getGolemMaterialKey() {
        return "CM_IRONWOOD";
    }

    @Override
    public void registerGolemMaterial() {
        //TODO: Research
        String[] research = new String[0];
        GolemMaterial.register(new GolemMaterial(
                this.getGolemMaterialKey(),
                research,
                new ResourceLocation(CongregaMystica.MOD_ID, ""),
                11045996,
                ConfigHandlerCM.golems.ironWood.statHealth,
                ConfigHandlerCM.golems.ironWood.statArmor,
                ConfigHandlerCM.golems.ironWood.statDamage,
                ConfigHandlerCM.golems.ironWood.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {EnumGolemTrait.BLASTPROOF}
        ));
    }

    @Override
    public void registerResearchLocation() {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
