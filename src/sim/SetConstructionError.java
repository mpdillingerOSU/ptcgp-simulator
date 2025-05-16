package sim;

/**
 * An instance of this class is used to represent an error that occured while
 * constructing a set of cards.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public class SetConstructionError extends Error{
    /**
     * Instantiates an instance of {@code SetConstructionError}, with the
     * provided message
     * @param m the message describing the error.
     */
    public SetConstructionError(String m) {
        super(m);
    }
}
