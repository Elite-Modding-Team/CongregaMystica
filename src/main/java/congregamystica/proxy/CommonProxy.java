package congregamystica.proxy;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.registry.RegistrarCM;

public class CommonProxy {
    public void preInit() {
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
        //Set up research categories here. Make sure to do it before the IAddition research is fired.

        RegistrarCM.getAdditions().forEach(IAddition::registerResearchLocation);
    }

}
