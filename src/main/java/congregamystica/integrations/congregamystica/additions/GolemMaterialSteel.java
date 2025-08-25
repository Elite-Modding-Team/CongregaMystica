package congregamystica.integrations.congregamystica.additions;

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
public class GolemMaterialSteel extends GolemMaterial implements IAddition, IProxy {

    public GolemMaterialSteel() {
        super(
                "CM_STEEL",
                new String[]{"CM_GOLEM_MAT_STEEL"},
                new ResourceLocation(CongregaMystica.MOD_ID, "textures/entity/golem/mat_steel.png"),
                4934475,
                16,
                12,
                6,
                ConfigHandlerCM.golems.steel.getMaterialStack(),
                new ItemStack(ItemsTC.mechanismSimple),
                new EnumGolemTrait[]{EnumGolemTrait.HEAVY, EnumGolemTrait.CLUMSY, EnumGolemTrait.BLASTPROOF, EnumGolemTrait.FIREPROOF}
        );
    }

    @Override
    public void init() {
        ItemStack stack = ConfigHandlerCM.golems.steel.getMaterialStack();
        if(!stack.isEmpty()) {
            register(this);
        }
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
            ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/golem_mat_steel"));
        }
    }

    @Override
    public boolean isEnabled() {
        //Configuration toggles here
        return ConfigHandlerCM.golems.steel.enable;
    }
}
