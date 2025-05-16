package sim;

/**
 * An instance of this enum is used to represent the creature card type of a
 * given creature card.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public enum CreatureCardType {
    STANDARD("Standard"),
    EX("EX");

    public final String NAME;

    /**
     * Instantiates an instance of {@code CreatureCardType}, with the provided
     * name.
     * @param NAME the name of the instance.
     */
    CreatureCardType(final String NAME){
        this.NAME = NAME;
    }
}
