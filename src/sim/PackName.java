package sim;

/**
 * An instance of this enum is used to represent the name of a pack.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public enum PackName {
    CHARIZARD("Charizard", SetName.GENETIC_APEX),
    MEWTWO("Mewtwo", SetName.GENETIC_APEX),
    PIKACHU("Pikachu", SetName.GENETIC_APEX),
    MEW("Mew", SetName.MYTHICAL_ISLAND),
    DIALGA("Dialga", SetName.SPACE_TIME_SMACKDOWN),
    PALKIA("Palkia", SetName.SPACE_TIME_SMACKDOWN),
    ARCEUS("Arceus", SetName.TRIUMPHANT_LIGHT),
    SHINY_CHARIZARD("Shiny Charizard", SetName.SHINING_REVELRY),
    SOLGALEO("Solgaleo", SetName.CELESTIAL_GUARDIANS),
    LUNALA("Lunala", SetName.CELESTIAL_GUARDIANS);

    public final String VAL;
    public final SetName SET_NAME;

    /**
     * Instantiates an instance of {@code PackName}, with the provided value,
     * along with the name of the set of which it is a member of.
     * @param VAL the value of the name.
     * @param SET_NAME the name of the set of which the instance is a member
     * of.
     */
    PackName(final String VAL, final SetName SET_NAME){
        this.VAL = VAL;
        this.SET_NAME = SET_NAME;
    }
}
