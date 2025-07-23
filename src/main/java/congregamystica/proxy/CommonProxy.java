package congregamystica.proxy;

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
    }
    public void postINit() {
        InitIntegrations.getModAdditions().forEach(IProxy::postInit);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInit);
    }
}
