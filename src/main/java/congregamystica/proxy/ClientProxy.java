package congregamystica.proxy;

import congregamystica.api.IModModule;
import congregamystica.api.IProxy;
import congregamystica.integrations.InitIntegrations;
import congregamystica.registry.RegistrarCM;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
        InitIntegrations.getModModules().forEach(IModModule::preInitClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInitClient);
    }

    @Override
    public void init() {
        super.init();
        InitIntegrations.getModModules().forEach(IModModule::initClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::initClient);
    }

    @Override
    public void postInit() {
        super.postInit();
        InitIntegrations.getModModules().forEach(IModModule::postInitClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInitClient);
    }
}
