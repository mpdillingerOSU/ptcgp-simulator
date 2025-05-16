package sim;

/**
 * An instance of this enum is used to represent the rarity of a {@code Card}.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public enum Rarity {
    D("D", 35, 1),
    DD("DD", 70, 1),
    DDD("DDD", 150, 2),
    DDDD("DDDD", 500, 3),
    S("S", 400, 3),
    SS("SS", 1250, 4),
    SSS("SSS", 1500, null),
    R("R", 1000, null),
    RR("RR", 1350, null),
    C("C", 2500, null);

    public final String REPRESENTATION;
    public final int PACK_POINT_COST;
    public final Integer WONDER_STAMINA_COST;

    /**
     * Instantiates an instance of {@code Rarity}, with the provided
     * representation, pack point cost, and wonder stamina cost.
     * @param representation the string representation of the instance.
     * @param packPointCost the pack point cost needed to purchase a card of
     * this instance.
     * @param wonderStaminaCost the wonder stamina cost needed to select a
     * {@code WonderPick} where this is the card of the highest rarity.
     */
    Rarity(String representation, int packPointCost, Integer wonderStaminaCost){
        this.REPRESENTATION = representation;
        this.PACK_POINT_COST = packPointCost;
        this.WONDER_STAMINA_COST = wonderStaminaCost;
    }
}
