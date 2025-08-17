package congregamystica.proxy;

import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.registry.RegistrarCM;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
        InitIntegrations.getModAdditions().forEach(IProxy::preInitClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInitClient);
    }

    @Override
    public void init() {
        super.init();
        InitIntegrations.getModAdditions().forEach(IProxy::initClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::initClient);
    }

    @Override
    public void postInit() {
        super.postInit();
        InitIntegrations.getModAdditions().forEach(IProxy::postInitClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInitClient);
    }
}
