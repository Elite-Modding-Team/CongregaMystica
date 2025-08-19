package congregamystica.integrations.thaumicwonders.items;

import congregamystica.CongregaMystica;
import congregamystica.api.item.IColoredItem;
import congregamystica.integrations.congregamystica.util.ClusterData;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

public class ItemEldritchClusterDynamic extends ItemEldritchCluster implements IColoredItem {
    public ItemEldritchClusterDynamic(ClusterData clusterData) {
        super(clusterData);
    }

    @Override
    public int getOverlayColor() {
        return clusterData.getOverlayColor();
    }

    //##########################################################
    // IItemAddition

    @Override
    public void registerModel(ModelRegistryEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation(new ResourceLocation(CongregaMystica.MOD_ID, "eldritch_cluster"), "inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, loc);
    }
}
