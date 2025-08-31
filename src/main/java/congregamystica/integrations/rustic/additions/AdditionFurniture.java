package congregamystica.integrations.rustic.additions;

import congregamystica.api.IAddition;
import congregamystica.api.IProxy;
import congregamystica.config.ConfigHandlerCM;
import congregamystica.integrations.rustic.blocks.furniture.BlockChairCM;
import congregamystica.integrations.rustic.blocks.furniture.BlockTableCM;
import congregamystica.registry.ModBlocksCM;
import congregamystica.registry.RegistrarCM;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;

import java.util.Map;

public class AdditionFurniture implements IAddition, IProxy {
    @Override
    public void preInit() {
        //Chairs
        RegistrarCM.addAdditionToRegister(new BlockChairCM("greatwood"));
        RegistrarCM.addAdditionToRegister(new BlockChairCM("silverwood"));
        //Tables
        RegistrarCM.addAdditionToRegister(new BlockTableCM("greatwood"));
        RegistrarCM.addAdditionToRegister(new BlockTableCM("silverwood"));
    }

    @Override
    public void registerAspects(AspectEventProxy registry, Map<ItemStack, AspectList> aspectMap) {
        aspectMap.put(new ItemStack(ModBlocksCM.CHAIR_GREATWOOD), new AspectList().add(Aspect.PLANT, 10).add(Aspect.LIFE, 3).add(Aspect.MAN, 5));
        aspectMap.put(new ItemStack(ModBlocksCM.CHAIR_SILVERWOOD), new AspectList().add(Aspect.PLANT, 10).add(Aspect.AURA, 3).add(Aspect.MAN, 5));
        aspectMap.put(new ItemStack(ModBlocksCM.TABLE_GREATWOOD), new AspectList().add(Aspect.PLANT, 8).add(Aspect.LIFE, 2).add(Aspect.MAN, 4));
        aspectMap.put(new ItemStack(ModBlocksCM.TABLE_SILVERWOOD), new AspectList().add(Aspect.PLANT, 8).add(Aspect.AURA, 2).add(Aspect.MAN, 4));
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCM.rustic.enableFurniture;
    }
}
