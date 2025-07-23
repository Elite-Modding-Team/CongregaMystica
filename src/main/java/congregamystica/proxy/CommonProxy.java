package congregamystica.proxy;

import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.registry.RegistrarCM;

public class CommonProxy {
    public void preInit() {
        InitIntegrations.preInit();
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInit);
    }
    public void init() {
        InitIntegrations.init();
        RegistrarCM.getProxyAdditions().forEach(IProxy::init);
    }
    public void postINit() {
        InitIntegrations.postInit();
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInit);
    }
}
