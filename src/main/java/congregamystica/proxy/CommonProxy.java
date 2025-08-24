package congregamystica.proxy;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.network.PacketHandlerCM;
import congregamystica.registry.RegistrarCM;
import net.minecraft.util.ResourceLocation;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

public class CommonProxy {
    public void preInit() {
        PacketHandlerCM.preInit();
        InitIntegrations.getModAdditions().forEach(IProxy::preInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInit);
    }

    public void init() {
        InitIntegrations.getModAdditions().forEach(IProxy::init);
        RegistrarCM.getProxyAdditions().forEach(IProxy::init);
        registerResearch();
    }

    public void postInit() {
        InitIntegrations.getModAdditions().forEach(IProxy::postInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInit);
    }

    private void registerResearch() {
    	ResearchCategories.registerCategory(
                "CONGREGA_MYSTICA", "FIRSTSTEPS", new AspectList(),
    			new ResourceLocation(CongregaMystica.MOD_ID, "textures/items/cluster_amber.png"),
    			new ResourceLocation(CongregaMystica.MOD_ID, "textures/gui/research_background.jpg"),
                new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_research_back_over.png"));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/misc"));
        
        RegistrarCM.getAdditions().forEach(IAddition::registerResearchLocation);
    }

}
