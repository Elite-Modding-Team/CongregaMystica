package congregamystica.integrations.congregamystica.util;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClusterData {
    public final String nativeDisplayName;
    public final String clusterId;
    public final String clusterOreDict;

    public final String eldritchDisplayName;
    public final String eldritchId;
    public final String eldritchOreDict;

    public final String associatedOre;
    public final String associatedIngot;
    public final String associatedNugget;
    public final int overlayColor;

    public ClusterData(String associatedOre, String outputType, int overlayColor) {
        String name = associatedOre.replaceFirst("^ore", "");
        StringBuilder sBuilder = new StringBuilder(name);
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(name);
        int extraFeed = 0;
        while(matcher.find()) {
            if(matcher.start() != 0) {
                sBuilder = sBuilder.insert(matcher.start() + extraFeed, " ");
                extraFeed++;
            }
        }
        name = sBuilder.toString();

        this.nativeDisplayName = "Native " + name + " Cluster";
        this.clusterId = "cluster_" + name.toLowerCase().replaceAll("\\s", "_");
        this.clusterOreDict = associatedOre.replaceFirst("^ore", "cluster");

        this.eldritchDisplayName = "Eldritch " + name + " Cluster";
        this.eldritchId = "eldritch_cluster_" + name.toLowerCase().replaceAll("\\s", "_");
        this.eldritchOreDict = associatedOre.replaceFirst("^ore", "clusterEldritch");

        this.associatedOre = associatedOre;
        this.associatedIngot = associatedOre.replaceFirst("^ore", outputType);
        this.associatedNugget = associatedOre.replaceFirst("^ore", "nugget");
        this.overlayColor = overlayColor;
    }

    public ClusterData(String associatedOre, String outputType) {
        this(associatedOre, outputType, Color.WHITE.getRGB());
    }
}
