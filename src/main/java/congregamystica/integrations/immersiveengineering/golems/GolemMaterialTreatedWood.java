package congregamystica.integrations.immersiveengineering.golems;

import congregamystica.CongregaMystica;
import congregamystica.api.golem.IGolemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ScanBlockState;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;

public class GolemMaterialTreatedWood implements IGolemAddition {
    @Override
    public String getGolemMaterialKey() {
        return "CM_TREATED_WOOD";
    }

    @Override
    public void registerGolemMaterial() {
        GolemMaterial.register(new GolemMaterial(
                this.getGolemMaterialKey(),
                new String[]{"CM_GOLEM_MAT_TREATED_WOOD"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/immersiveengineering/mat_treated_wood.png"),
                6566425,
                ConfigHandlerCM.golems.treatedWood.statHealth,
                ConfigHandlerCM.golems.treatedWood.statArmor,
                ConfigHandlerCM.golems.treatedWood.statDamage,
                ConfigHandlerCM.golems.treatedWood.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[]{EnumGolemTrait.DEFT}
        ));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerResearchLocation() {
        ItemStack stack = ConfigHandlerCM.golems.treatedWood.getMaterialStack();
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            ScanningManager.addScannableThing(new ScanBlockState("f_CM_TREATED_WOOD", block.getStateFromMeta(stack.getMetadata()), false));
        }
        ScanningManager.addScannableThing(new ScanItem("f_CM_TREATED_WOOD", stack));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/golems/golem_mat_treated_wood"));
    }

    @Override
    public boolean isEnabled() {
        //Configuration or OreDict toggles here
        return ConfigHandlerCM.golems.treatedWood.enable;
    }
}
