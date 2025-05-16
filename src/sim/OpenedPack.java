package sim;

import java.util.ArrayList;

/**
 * An instance of this class is used to represent the results of an opened
 * pack of cards.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class OpenedPack {
    public final Pack PACK;
    private final ArrayList<Card> CARDS;

    /**
     * Instantiates an instance of {@code OpenedPack} of the provided pack.
     * @param pack the pack to be opened.
     */
    public OpenedPack(Pack pack){
        this.PACK = pack;

        this.CARDS = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            this.CARDS.add(pack.pull(i));
        }
    }

    /**
     * Returns the cards of the instance.
     * @return the cards of the instance.
     */
    public ArrayList<Card> cards(){
        return new ArrayList<>(this.CARDS);
    }
}
