package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * An instance of this class is used to represent a pack of cards.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class Pack {
    public final PackName NAME;
    public final SetName SET_NAME;
    public final PackRarityRates PACK_RARITY_RATES;
    private final Random RAND = new Random();
    private final HashMap<Rarity, ArrayList<Card>> CARDS = new HashMap<>(){{
        for(Rarity rarity : Rarity.values()){
            put(rarity, new ArrayList<>());
        }
    }};
    private final int TOTAL_CARDS;
    private final int STANDARD_CARDS;
    private final int RARE_CARDS;

    /**
     * Instantiates an instance of {@code Pack}, with the provided pack name,
     * name of the set of which it is a member of, pack rarity rates, and the
     * possible cards contained within the instance.
     * @param NAME the name of the instance.
     * @param SET_NAME the name of the set of which the instance is a member
     * of.
     * @param PACK_RARITY_RATES the pack rarity rates of the instance.
     * @param CARDS the possible cards contained within the instance.
     */
    public Pack(final PackName NAME, final SetName SET_NAME,
                final PackRarityRates PACK_RARITY_RATES,
                final ArrayList<Card> CARDS){
        this.NAME = NAME;
        this.SET_NAME = SET_NAME;

        this.PACK_RARITY_RATES = PACK_RARITY_RATES;

        int standardCards = 0;
        int rareCards = 0;
        for(int i = 0; i < CARDS.size(); i++){
            this.CARDS.get(CARDS.get(i).RARITY).add(CARDS.get(i));
            if(CARDS.get(i).isStandardRarity()){
                standardCards++;
            } else {
                rareCards++;
            }
        }

        this.TOTAL_CARDS = CARDS.size();
        this.STANDARD_CARDS = standardCards;
        this.RARE_CARDS = rareCards;
    }

    /**
     * Returns the total possible cards contained within the instance.
     * @return the total possible cards contained within the instance.
     */
    public int totalCards(){
        return TOTAL_CARDS;
    }

    /**
     * Returns the total possible standard cards contained within the instance.
     * @return the total possible standard cards contained within the instance.
     */
    public int standardCards(){
        return STANDARD_CARDS;
    }

    /**
     * Returns the total possible rare cards contained within the instance.
     * @return the total possible rare cards contained within the instance.
     */
    public int rareCards(){
        return RARE_CARDS;
    }

    /**
     * Return the total possible cards of the provided rarity contained within
     * the instance.
     * @param rarity the rarity for which to obtain the total possible cards
     * of.
     * @return the total possible cards of the provided rarity contained
     * within the instance.
     */
    public int cardsOf(Rarity rarity){
        return CARDS.get(rarity).size();
    }

    /**
     * Returns a random opened pack of the instance.
     * @return a random opened pack of the instance.
     */
    public OpenedPack open(){
        return new OpenedPack(this);
    }

    /**
     * (package-private) Helper method to obtain a random rarity of the
     * provided draw ordinal. Intended to be used only by {@code OpenedPack}
     * in order for it to draw its cards.
     * @param ordinal the ordinal for which to obtain the random rarity for.
     * @return a random rarity of the provided draw ordinal.
     */
    Card pull(int ordinal){
        final Rarity RARITY = switch(ordinal) {
            case 0 -> PACK_RARITY_RATES.DRAW_ONE.draw();
            case 1 -> PACK_RARITY_RATES.DRAW_TWO.draw();
            case 2 -> PACK_RARITY_RATES.DRAW_THREE.draw();
            case 3 -> PACK_RARITY_RATES.DRAW_FOUR.draw();
            default -> PACK_RARITY_RATES.DRAW_FIVE.draw();
        };

        return CARDS.get(RARITY).get(RAND.nextInt(cardsOf(RARITY)));
    }
}
