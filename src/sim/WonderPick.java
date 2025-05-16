package sim;

import java.util.ArrayList;
import java.util.Random;

/**
 * An instance of this class is used to represent a wonder pick from which a
 * {@code Card} can be randomly selected.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class WonderPick {
    public final Pack PACK;
    private final ArrayList<Card> CARDS;
    public final int STAMINA;

    /**
     * Instantiates an instance of {@code WonderPick}, with the provided pack
     * from which to obtain the necessary cards.
     * @param pack the pack from which to obtain the necessary cards.
     */
    public WonderPick(Pack pack){
        this.PACK = pack;
        ArrayList<Card> cards;
        boolean isValid;
        do {
            cards = pack.open().cards();
            isValid = true;
            for(int i = 0; i < cards.size(); i++) {
                if(cards.get(i).RARITY.WONDER_STAMINA_COST == null) {
                    isValid = false;
                    break;
                }
            }
        } while (!isValid);
        this.CARDS = cards;

        Rarity highestRarity = Rarity.D;
        for(int i = 0; i < this.CARDS.size(); i++){
            if(this.CARDS.get(i).RARITY.ordinal() > highestRarity.ordinal()){
                highestRarity = this.CARDS.get(i).RARITY;
            }
        }
        this.STAMINA = highestRarity.WONDER_STAMINA_COST;
    }

    /**
     * Returns the cards contained in the instance.
     * @return the cards contained in the instance.
     */
    public ArrayList<Card> cards(){
        return new ArrayList<>(this.CARDS);
    }

    /**
     * Returns a random card from within the instance.
     * @return a random card from within the instance.
     */
    public Card select(){
        return this.CARDS.get(new Random().nextInt(this.CARDS.size()));
    }
}
