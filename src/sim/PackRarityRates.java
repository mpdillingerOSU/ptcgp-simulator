package sim;

/**
 * An instance of this class is used to contain the draw rates for a given
 * {@code Pack}.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class PackRarityRates {
    public final DrawRarityRates DRAW_ONE;
    public final DrawRarityRates DRAW_TWO;
    public final DrawRarityRates DRAW_THREE;
    public final DrawRarityRates DRAW_FOUR;
    public final DrawRarityRates DRAW_FIVE;

    /**
     * Instantiates an instance of {@code PackRarityRates}, with the provided
     * draw rates.
     * @param DRAW_ONE the draw rate of the first card.
     * @param DRAW_TWO the draw rate of the second card.
     * @param DRAW_THREE the draw rate of the third card.
     * @param DRAW_FOUR the draw rate of the fourth card.
     * @param DRAW_FIVE the draw rate of the fifth card.
     */
    public PackRarityRates(final DrawRarityRates DRAW_ONE,
                           final DrawRarityRates DRAW_TWO,
                           final DrawRarityRates DRAW_THREE,
                           final DrawRarityRates DRAW_FOUR,
                           final DrawRarityRates DRAW_FIVE){
        this.DRAW_ONE = DRAW_ONE;
        this.DRAW_TWO = DRAW_TWO;
        this.DRAW_THREE = DRAW_THREE;
        this.DRAW_FOUR = DRAW_FOUR;
        this.DRAW_FIVE = DRAW_FIVE;
    }
}
