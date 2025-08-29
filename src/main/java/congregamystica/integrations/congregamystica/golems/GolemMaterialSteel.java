package congregamystica.integrations.congregamystica.golems;

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

public class GolemMaterialSteel implements IGolemAddition {
    @Override
    public String getGolemMaterialKey() {
        return "CM_STEEL";
    }

    @Override
    public void registerGolemMaterial() {
        GolemMaterial.register(new GolemMaterial(
                this.getGolemMaterialKey(),
                new String[]{"CM_GOLEM_MAT_STEEL"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/mat_steel.png"),
                4934475,
                ConfigHandlerCM.golems.steel.statHealth,
                ConfigHandlerCM.golems.steel.statArmor,
                ConfigHandlerCM.golems.steel.statDamage,
                ConfigHandlerCM.golems.steel.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[]{EnumGolemTrait.HEAVY, EnumGolemTrait.CLUMSY, EnumGolemTrait.BLASTPROOF, EnumGolemTrait.FIREPROOF}
        ));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerResearchLocation() {
        ItemStack stack = ConfigHandlerCM.golems.steel.getMaterialStack();
        if(!stack.isEmpty()) {
            Block block = Block.getBlockFromItem(stack.getItem());
            if (block != Blocks.AIR) {
                ScanningManager.addScannableThing(new ScanBlockState("f_CM_STEEL", block.getStateFromMeta(stack.getMetadata()), false));
            }
            ScanningManager.addScannableThing(new ScanItem("f_CM_STEEL", stack));
            ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/golems/golem_mat_steel"));
        }
    }

    @Override
    public boolean isEnabled() {
        //Configuration toggles here
        return ConfigHandlerCM.golems.steel.enable;
    }
}
