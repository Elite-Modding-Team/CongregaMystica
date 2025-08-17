package congregamystica.integrations.congregamystica.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;

//Because this feature is not a block or item, it should extend the IProxy so it can be registered correctly.
public class GolemMaterialSteel extends GolemMaterial implements IAddition, IProxy {
	
    public GolemMaterialSteel() {
        super(
                "CM_STEEL",
                new String[] {"MATSTUDIRON"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/mat_steel.png"),
                4934475,
                16,
                12,
                6,
                ConfigHandlerCM.golems.steel.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {EnumGolemTrait.HEAVY, EnumGolemTrait.CLUMSY, EnumGolemTrait.BLASTPROOF, EnumGolemTrait.FIREPROOF}
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
        //Configuration toggles here
        return ConfigHandlerCM.golems.steel.enable;
    }
}
