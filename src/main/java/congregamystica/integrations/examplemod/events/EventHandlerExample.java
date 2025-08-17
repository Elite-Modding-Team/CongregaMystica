package congregamystica.integrations.examplemod.events;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.golems.EntityThaumcraftGolem;

public class EventHandlerExample {
    //The event should not be static as this handler is registered through the ExampleModCM integration module
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if(event.getEntityLiving() instanceof EntityThaumcraftGolem) {
            //Do golem stuff
        }
    }

    //Same as a normal event handler, you can just have as many of these as you want
    @SubscribeEvent
    public void onBlockHarvest(BlockEvent.HarvestDropsEvent event) {
        //Do harvest whatever
    }
}
