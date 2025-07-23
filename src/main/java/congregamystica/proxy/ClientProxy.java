package congregamystica.proxy;

import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.registry.RegistrarCM;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
        InitIntegrations.preInitClient();
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInitClient);
    }

    @Override
    public void init() {
        super.init();
        InitIntegrations.initClient();
        RegistrarCM.getProxyAdditions().forEach(IProxy::initClient);
    }

    @Override
    public void postINit() {
        super.postINit();
        InitIntegrations.postInitClient();
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInitClient);
    }
}
