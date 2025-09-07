package congregamystica.proxy;

import congregamystica.api.IModModule;
import congregamystica.api.IProxy;
import congregamystica.integrations.ModuleManager;
import congregamystica.registry.RegistrarCM;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        ModuleManager.getModModules().forEach(IModModule::preInitClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::preInitClient);
    }

    @Override
    public void init() {
        ModuleManager.getModModules().forEach(IModModule::initClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::initClient);
    }

    @Override
    public void postInit() {
        ModuleManager.getModModules().forEach(IModModule::postInitClient);
        RegistrarCM.getProxyAdditions().forEach(IProxy::postInitClient);
    }
}
