package sim;

/**
 * An instance of this enum is used to represent the playable type of a given
 * instance of {@code Card}.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public enum CardType {
    CREATURE("Creature"),
    ITEM("Item"),
    SUPPORTER("Supporter"),
    TOOL("Tool");

    public final String NAME;

    /**
     * Instantiates an instance of {@code CardType}, with the provided name.
     * @param NAME the name of the instance.
     */
    CardType(final String NAME){
        this.NAME = NAME;
    }
}
