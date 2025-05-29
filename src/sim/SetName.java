package sim;

/**
 * An instance of this enum is used to represent the name of a set.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public enum SetName {
    GENETIC_APEX("Genetic Apex"),
    MYTHICAL_ISLAND("Mythical Island"),
    SPACE_TIME_SMACKDOWN("Space-Time Smackdown"),
    TRIUMPHANT_LIGHT("Triumphant Light"),
    SHINING_REVELRY("Shining Revelry"),
    CELESTIAL_GUARDIANS("Celestial Guardians"),
    EXTRADIMENSIONAL_CRISIS("Extradimensional Crisis");

    public final String VAL;

    /**
     * Instantiates an instance of {@code SetName}, with the provided value.
     * @param VAL the value of the name.
     */
    SetName(final String VAL){
        this.VAL = VAL;
    }

    /**
     * Returns the instance whose name value is equal to the provided name.
     * @param name the name with which to compare values to.
     * @return the instance whose name value is equal to the provided name, if
     * one exists. Else, {@code null}.
     */
    public static SetName get(String name){
        for(SetName setName : values()){
            if(setName.VAL.equals(name)){
                return setName;
            }
        }

        return null;
    }
}
