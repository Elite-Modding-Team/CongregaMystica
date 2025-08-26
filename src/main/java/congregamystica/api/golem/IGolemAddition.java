package congregamystica.api.golem;

import congregamystica.api.IAddition;

public interface IGolemAddition extends IAddition {
    String getGolemMaterialKey();

    void registerGolemMaterial();
}
