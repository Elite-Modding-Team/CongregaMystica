package congregamystica.api;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModModule {
    default void preInit() {}

    default void init() {}

    default void postInit() {}

    @SideOnly(Side.CLIENT)
    default void preInitClient() {}

    @SideOnly(Side.CLIENT)
    default void initClient() {}

    @SideOnly(Side.CLIENT)
    default void postInitClient() {}

    default void registerResearchNode() {}
}
