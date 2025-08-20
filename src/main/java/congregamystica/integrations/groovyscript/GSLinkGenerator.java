package congregamystica.integrations.groovyscript;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import congregamystica.CongregaMystica;

public class GSLinkGenerator extends BasicLinkGenerator {
    @Override
    public String id() {
        return CongregaMystica.MOD_ID;
    }

    @Override
    protected String domain() {
        return "https://github.com/Elite-Modding-Team/CongregaMystica/";
    }

    @Override
    protected String version() {
        return CongregaMystica.MOD_VERSION;
    }
}
