package congregamystica.integrations.harkenscythe.additions;

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

public class GolemMaterialLivingmetal extends GolemMaterial implements IAddition, IProxy {
	
    public GolemMaterialLivingmetal() {
        super(
                "CM_LIVINGMETAL",
                new String[] {"CM_GOLEM_MAT_LIVINGMETAL"},
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
    public void preInit() {
        //TODO: Register material property. Remember to use GolemHelper#registerGolemTrait()
    }

    @Override
    public void init() {
    	register(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void registerResearchLocation() {
        ItemStack stack = ConfigHandlerCM.golems.livingmetal.getMaterialStack();
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block != Blocks.AIR) {
            ScanningManager.addScannableThing(new ScanBlockState("f_CM_LIVINGMETAL", block.getStateFromMeta(stack.getMetadata()), false));
        }
        ScanningManager.addScannableThing(new ScanItem("f_CM_LIVINGMETAL", stack));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID,
                "research/harkenscythe/golem_mat_livingmetal"));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.golems.livingmetal.enable;
    }
}
