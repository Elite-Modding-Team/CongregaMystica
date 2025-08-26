package congregamystica.api.util;

/**
 * An enum interface used to sort CM items for creative tab/JEI order.
 */
public enum EnumSortType {
    /** Blocks will always appear first. */
    BLOCKS,
    /** Native clusters will always appear first on the list following any blocks. */
    NATIVE_CLUSTER,
    /** Eldritch clusters will follow Native Clusters. */
    ELDRITCH_CLUSTER,
    /** Mod specific items such as the Mimic Forks will appear next. */
    CONGREGA_MYSTICA,
    /** Caster Gaunlets will be grouped together following the mod specific items. */
    CASTER_GAUNTLET,
    /** All other items will be sorted in the order they were registered. */
    ITEMS
}
