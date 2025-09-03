package congregamystica.proxy;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IModModule;
import congregamystica.api.IProxy;
import congregamystica.api.golem.IGolemAddition;
import congregamystica.integrations.ModuleManager;
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
        ModuleManager.getModModules().forEach(IModModule::preInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInit);
    }

    public void init() {
        ModuleManager.getModModules().forEach(IModModule::init);
        RegistrarCM.getProxyAdditions().forEach(IProxy::init);
        registerResearch();
    }

    public void postInit() {
        ModuleManager.getModModules().forEach(IModModule::postInit);
        RegistrarCM.getGolemAdditions().forEach(IGolemAddition::registerGolemMaterial);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInit);
    }

    private void registerResearch() {
    	ResearchCategories.registerCategory(
                "CONGREGA_MYSTICA", "FIRSTSTEPS", new AspectList(),
    			new ResourceLocation(CongregaMystica.MOD_ID, "textures/research/r_congrega_mystica.png"),
    			new ResourceLocation(CongregaMystica.MOD_ID, "textures/gui/research_background.jpg"),
                new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_research_back_over.png"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(CongregaMystica.MOD_ID, "research/misc"));

        ModuleManager.getModModules().forEach(IModModule::registerResearchNode);
        RegistrarCM.getAdditions().forEach(IAddition::registerResearchLocation);
    }

}
