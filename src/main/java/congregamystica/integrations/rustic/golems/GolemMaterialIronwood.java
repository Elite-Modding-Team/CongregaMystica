package congregamystica.integrations.rustic.golems;

import congregamystica.CongregaMystica;
import congregamystica.api.golem.IGolemAddition;
import congregamystica.config.ConfigHandlerCM;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import rustic.common.blocks.ModBlocks;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ScanBlockState;
import thaumcraft.api.research.ScanItem;
import thaumcraft.api.research.ScanningManager;

public class GolemMaterialIronwood implements IGolemAddition {
    @Override
    public String getGolemMaterialKey() {
        return "CM_IRONWOOD";
    }

    @Override
    public void registerOreDicts() {
        //Ironwood needs a custom oreDict value registered.
        OreDictionary.registerOre("plankIronwood", new ItemStack(ModBlocks.LOG, 1, 1));
    }

    @Override
    public void registerGolemMaterial() {
        GolemMaterial.register(new GolemMaterial(
                this.getGolemMaterialKey(),
                new String[]{"CM_GOLEM_MAT_IRONWOOD"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/rustic/mat_ironwood.png"),
                11045996,
                ConfigHandlerCM.golems.ironWood.statHealth,
                ConfigHandlerCM.golems.ironWood.statArmor,
                ConfigHandlerCM.golems.ironWood.statDamage,
                ConfigHandlerCM.golems.ironWood.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[] {EnumGolemTrait.BLASTPROOF}
        ));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerResearchLocation() {
        ItemStack stack = ConfigHandlerCM.golems.ironWood.getMaterialStack();
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            ScanningManager.addScannableThing(new ScanBlockState("f_CM_IRONWOOD", block.getStateFromMeta(stack.getMetadata()), false));
        }
        ScanningManager.addScannableThing(new ScanItem("f_CM_IRONWOOD", stack));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/golems/golem_mat_ironwood"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.golems.ironWood.enable;
    }
}
