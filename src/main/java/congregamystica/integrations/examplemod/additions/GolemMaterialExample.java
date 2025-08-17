package congregamystica.integrations.examplemod.additions;

import congregamystica.CongregaMystica;
import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.golems.EnumGolemTrait;
import thaumcraft.api.golems.parts.GolemMaterial;

//Because this feature is not a block or item, it should extend the IProxy so it can be registered correctly.
public class GolemMaterialExample extends GolemMaterial implements IAddition, IProxy {

    public GolemMaterialExample() {
        super(
                "example_golem_material",
                new String[] {"example_research"},
                new ResourceLocation(CongregaMystica.MOD_ID, "example_golem_material"),
                0,
                20,
                20,
                7,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                new EnumGolemTrait[] {EnumGolemTrait.ARMORED, EnumGolemTrait.BLASTPROOF}
        );
    }

    @Override
    public void preInit() {
        //Any FMLPreInitialization actions here. This includes EVENT_BUS registries. You can also override the preInitClient() for a client-side version.
    }

    @Override
    public void init() {
        //Any FMLInitialization phase actions here. You can also override the initClient() for a client-side version.
    }

    @Override
    public void postInit() {
        //Any FMLPostInitialization phase actions here. You can also override the postInitClient() for a client-side version.
    }

    @Override
    public void registerRecipe(IForgeRegistry<IRecipe> registry) {
        //Register recipes here
    }

    @Override
    public void registerModel(ModelRegistryEvent event) {
        //Register models here
    }

    @Override
    public void registerResearchLocation() {
        //Register associated research here
    }

    @Override
    public boolean isEnabled() {
        //Configuration or OreDict toggles here
        return true;
    }
}
