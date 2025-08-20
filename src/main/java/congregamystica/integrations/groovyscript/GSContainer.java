package congregamystica.integrations.groovyscript;

import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import congregamystica.integrations.groovyscript.handlers.PechTrades;

public class GSContainer extends GroovyPropertyContainer {
    public static final PechTrades PechTrades = new PechTrades();

    public GSContainer() {
        this.addProperty(PechTrades);
    }
}
