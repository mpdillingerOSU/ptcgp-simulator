package sim;

/**
 * An instance of this class is only intended to be used as a lambda
 * expression to check whether a {@code Card} has been obtained from a special
 * unlock.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public interface SpecialUnlock {
    /**
     * Returns whether the {@code SetCollection} has fulfilled the special
     * requirements necessary in order to unlock a specific card.
     * @param setCollection the set collection needed to be checked.
     * @return {@code true}, if the special requirements have been fulfilled.
     * Else, {@code false}.
     */
    boolean check(SetCollection setCollection);
}
