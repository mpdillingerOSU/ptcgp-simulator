package sim;

/**
 * An instance of this class is used to represent a key-value pair.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class Pair<K, V> {
    public final K KEY;
    public final V VAL;

    /**
     * Instantiates an instance of {@code Pair}, with the provided key and
     * associated value.
     * @param KEY the key of the pair.
     * @param VAL the value associated with the key of the pair.
     */
    public Pair(final K KEY, final V VAL){
        this.KEY = KEY;
        this.VAL = VAL;
    }
}
