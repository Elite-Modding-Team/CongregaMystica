package congregamystica.integrations.congregamystica.util;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClusterData {
    private final String nativeDisplayName;
    private final String eldritchDisplayName;
    private final String clusterId;
    private final String eldritchId;
    private final String clusterOreDict;
    private final String associatedOre;
    private final String associatedIngot;
    private final String associatedNugget;
    private final int overlayColor;

    public ClusterData(String associatedOre, int overlayColor) {
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
        this.eldritchDisplayName = "Eldritch " + name + " Cluster";
        this.clusterId = "cluster_" + name.toLowerCase().replaceAll("\\s", "_");
        this.eldritchId = "eldritch_cluster_" + name.toLowerCase().replaceAll("\\s", "_");
        this.clusterOreDict = associatedOre.replaceFirst("^ore", "cluster");
        this.associatedOre = associatedOre;
        this.associatedIngot = associatedOre.replaceFirst("^ore", "ingot");
        this.associatedNugget = associatedOre.replaceFirst("^ore", "nugget");
        this.overlayColor = overlayColor;
    }

    public ClusterData(String associatedOre) {
        this(associatedOre, Color.WHITE.getRGB());
    }

    public String getNativeDisplayName() {
        return nativeDisplayName;
    }

    public String getEldritchDisplayName() {
        return eldritchDisplayName;
    }

    public String getClusterId() {
        return clusterId;
    }

    public String getEldritchId() {
        return eldritchId;
    }

    public String getClusterOreDict() {
        return clusterOreDict;
    }

    public String getAssociatedOre() {
        return associatedOre;
    }

    public String getAssociatedIngot() {
        return associatedIngot;
    }

    public String getAssociatedNugget() {
        return associatedNugget;
    }

    public int getOverlayColor() {
        return overlayColor;
    }
}
