package sim;

/**
 * An instance of this class is used to represent the results of a single
 * card-opening simulation.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class SimulationPacket {
    public final int ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS;
    public final int ATTEMPTS_FOR_ALL_D_RARITIES;
    public final int ATTEMPTS_FOR_ALL_DD_RARITIES;
    public final int ATTEMPTS_FOR_ALL_DDD_RARITIES;
    public final int ATTEMPTS_FOR_ALL_DDDD_RARITIES;

    /**
     * Instantiates an instance of {@code SimulationPacket}, with the provided
     * data.
     * @param ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 10% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 20% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 30% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 40% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 50% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 60% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 70% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 80% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 90% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS the attempts needed to
     * acquire 100% of cards of all standard rarity.
     * @param ATTEMPTS_FOR_ALL_D_RARITIES the attempts needed to acquire all
     * cards of {@code Rarity} equal to {@code D}.
     * @param ATTEMPTS_FOR_ALL_DD_RARITIES the attempts needed to acquire all
     * cards of {@code Rarity} equal to {@code DD}.
     * @param ATTEMPTS_FOR_ALL_DDD_RARITIES the attempts needed to acquire all
     * cards of {@code Rarity} equal to {@code DDD}.
     * @param ATTEMPTS_FOR_ALL_DDDD_RARITIES the attempts needed to acquire
     * all cards of {@code Rarity} equal to {@code DDDD}.
     */
    public SimulationPacket(final int ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS,
                            final int ATTEMPTS_FOR_ALL_D_RARITIES,
                            final int ATTEMPTS_FOR_ALL_DD_RARITIES,
                            final int ATTEMPTS_FOR_ALL_DDD_RARITIES,
                            final int ATTEMPTS_FOR_ALL_DDDD_RARITIES){
        this.ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS = ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS;
        this.ATTEMPTS_FOR_ALL_D_RARITIES = ATTEMPTS_FOR_ALL_D_RARITIES;
        this.ATTEMPTS_FOR_ALL_DD_RARITIES = ATTEMPTS_FOR_ALL_DD_RARITIES;
        this.ATTEMPTS_FOR_ALL_DDD_RARITIES = ATTEMPTS_FOR_ALL_DDD_RARITIES;
        this.ATTEMPTS_FOR_ALL_DDDD_RARITIES = ATTEMPTS_FOR_ALL_DDDD_RARITIES;
    }
}
