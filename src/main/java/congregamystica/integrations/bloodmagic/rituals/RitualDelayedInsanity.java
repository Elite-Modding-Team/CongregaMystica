package congregamystica.integrations.bloodmagic.rituals;

import WayofTime.bloodmagic.ritual.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.common.lib.potions.PotionWarpWard;

import java.util.List;
import java.util.function.Consumer;

@RitualRegister("delayed_insanity")
public class RitualDelayedInsanity extends Ritual {

    public RitualDelayedInsanity() {
        super("delayed_insanity", 1, 25000, "ritual.congregamystica.delayed_insanity");
        this.addBlockRange("ward", new AreaDescriptor.Rectangle(new BlockPos(-15, -15, -15), 31));
        this.setMaximumVolumeAndDistanceOfRange("ward", 0, 20, 20);
    }

    @Override
    public void performRitual(IMasterRitualStone ritualStone) {
        World world = ritualStone.getWorldObj();
        int currentEssence = ritualStone.getOwnerNetwork().getCurrentEssence();
        if (currentEssence < this.getRefreshCost()) {
            ritualStone.getOwnerNetwork().causeNausea();
        } else {
            BlockPos pos = ritualStone.getBlockPos();
            int maxEffects = currentEssence / this.getRefreshCost();
            int totalEffects = 0;
            int totalCost = 0;

            AreaDescriptor healArea = ritualStone.getBlockRange("ward");
            List<EntityPlayer> entities = world.getEntitiesWithinAABB(EntityPlayer.class, healArea.getAABB(pos));

            for(EntityPlayer entity : entities) {
                if (entity.isPotionApplicable(new PotionEffect(PotionWarpWard.instance))) {
                    entity.addPotionEffect(new PotionEffect(PotionWarpWard.instance, 600, 0, false, false));
                    totalCost += this.getRefreshCost();
                    totalEffects++;
                    if (totalEffects >= maxEffects) {
                        break;
                    }
                }
            }
            ritualStone.getOwnerNetwork().syphon(ritualStone.ticket(totalCost));
        }
    }

    @Override
    public int getRefreshTime() {
        return 100;
    }

    @Override
    public int getRefreshCost() {
        return 200;
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> components) {
        this.addOffsetRunes(components, 4, 0, 0, EnumRuneType.FIRE);
        this.addOffsetRunes(components, 5, 1, 0, EnumRuneType.DUSK);
        this.addOffsetRunes(components, 3, 5, 0, EnumRuneType.WATER);
        this.addCornerRunes(components, 3, 0, EnumRuneType.DUSK);
        this.addOffsetRunes(components, 4, 5, 0, EnumRuneType.EARTH);
        this.addOffsetRunes(components, 4, 5, -1, EnumRuneType.EARTH);
        this.addCornerRunes(components, 5, 0, EnumRuneType.EARTH);
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualDelayedInsanity();
    }
}
