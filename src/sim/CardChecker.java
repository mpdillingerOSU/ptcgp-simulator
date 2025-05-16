package sim;

/**
 * An instance of this class is only intended to be used as a lambda
 * expression to check whether a {@code Card} fulfills specific requirements.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public interface CardChecker {
    /**
     * Returns whether the provided card fulfills the specific requirements.
     * @param card the card to be inspected.
     * @return {@code true}, if the card fulfills the specific requirements.
     * Else, {@code false}.
     */
    boolean inspect(Card card);
}
