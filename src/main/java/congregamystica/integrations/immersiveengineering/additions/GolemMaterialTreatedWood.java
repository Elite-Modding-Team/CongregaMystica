package congregamystica.integrations.immersiveengineering.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
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

//Because this feature is not a block or item, it should extend the IProxy so it can be registered correctly.
public class GolemMaterialTreatedWood extends GolemMaterial implements IAddition, IProxy {

    public GolemMaterialTreatedWood() {
        super(
                "CM_TREATED_WOOD",
                new String[]{"CM_GOLEM_MAT_TREATED_WOOD"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/immersiveengineering/mat_treated_wood.png"),
                6566425,
                6,
                2,
                1,
                ConfigHandlerCM.golems.treated_wood.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[]{EnumGolemTrait.DEFT}
        );
    }

    @Override
    public void init() {
        register(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerResearchLocation() {
        ItemStack stack = ConfigHandlerCM.golems.treated_wood.getMaterialStack();
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            ScanningManager.addScannableThing(new ScanBlockState("f_CM_TREATED_WOOD", block.getStateFromMeta(stack.getMetadata()), false));
        }
        ScanningManager.addScannableThing(new ScanItem("f_CM_TREATED_WOOD", stack));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/immersiveengineering/golem_mat_treated_wood"));
    }

    @Override
    public boolean isEnabled() {
        //Configuration or OreDict toggles here
        return ConfigHandlerCM.golems.treated_wood.enable;
    }
}
