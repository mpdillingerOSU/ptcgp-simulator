package sim;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An instance of this class is used to represent a set of cards.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class CardSet {
    public final SetName SET_NAME;

    //package-private
    final Card[] CARDS;
    private final int STANDARD_COUNT;
    private final int RARE_COUNT;
    private final HashMap<Rarity, Integer> RARITY_TOTAL = new HashMap<>(){{
        for(Rarity rarity : Rarity.values()){
            put(rarity, 0);
        }
    }};

    private final HashMap<PackName, Pack> PACKS = new HashMap<>();

    private final HashMap<Card, SpecialUnlock> SPECIAL_UNLOCKS = new HashMap<>();

    /**
     * Instantiates an instance of {@code CardSet}, with the provided set
     * name, pack names, cards, and special unlocks.
     * @param setName the name of the set.
     * @param packNames the names of the packs within the set.
     * @param packRarityRates the rarity rates of the packs within the set.
     * @param cards the cards within the set.
     * @param specialUnlocks the special unlocks for the set.
     */
    public CardSet(SetName setName, PackName[] packNames, PackRarityRates packRarityRates,
                   Card[] cards, HashMap<Card, SpecialUnlock> specialUnlocks) {
        this.SET_NAME = setName;

        int standardCount = 0;
        this.CARDS = new Card[cards.length];
        for(int i = 0; i < this.CARDS.length; i++){
            this.CARDS[i] = cards[i];
            if(this.CARDS[i].RARITY.ordinal() < Rarity.S.ordinal()){
                standardCount++;
            }
            RARITY_TOTAL.replace(this.CARDS[i].RARITY, RARITY_TOTAL.get(this.CARDS[i].RARITY) + 1);
        }
        this.STANDARD_COUNT = standardCount;
        this.RARE_COUNT = this.CARDS.length - this.STANDARD_COUNT;

        for(PackName packName : packNames){
            this.PACKS.put(
                    packName,
                    new Pack(
                            packName,
                            this.SET_NAME,
                            packRarityRates,
                            new ArrayList<>(){{
                                for (Card card : CARDS) {
                                    if (card.isIn(packName)) {
                                        add(card);
                                    }
                                }
                            }}
                    )
            );
        }

        for(Card card : specialUnlocks.keySet()){
            if(card.isSpecialUnlock()){
                this.SPECIAL_UNLOCKS.put(card, specialUnlocks.get(card));
            } else {
                throw new SetConstructionError("set cannot have a special unlock for a card that is not a special unlock: " + card.CARD_NUM + " " + card.NAME);
            }
        }
    }

    /**
     * Returns the total number of cards within the complete set.
     * @return the total number of cards within the complete set.
     */
    public int completeTotal(){
        return this.CARDS.length;
    }

    /**
     * Returns the total number of cards within the standard set.
     * @return the total number of cards within the standard set.
     */
    public int standardTotal(){
        return this.STANDARD_COUNT;
    }

    /**
     * Returns the total number of cards within the rare set.
     * @return the total number of cards within the rare set.
     */
    public int rareTotal(){
        return this.RARE_COUNT;
    }

    /**
     * Returns the total number of cards of the provided rarity.
     * @param rarity the rarity to be checked.
     * @return the total number of cards of the provided rarity.
     */
    public int rarityTotal(Rarity rarity){
        return this.RARITY_TOTAL.get(rarity);
    }

    /**
     * Returns the pack with the provided pack name.
     * @param packName the name of the pack to be obtained.
     * @return the pack with the provided pack name.
     */
    public Pack getPack(PackName packName){
        return this.PACKS.get(packName);
    }

    /**
     * Returns an {@code ArrayList} containing all packs within the set.
     * @return all packs within the set.
     */
    public ArrayList<Pack> packs(){
        return new ArrayList<>(PACKS.values());
    }

    /**
     * Returns the total number of packs within the set.
     * @return the total number of packs within the set.
     */
    public int packCount(){
        return PACKS.size();
    }

    /**
     * Returns a copy of all special unlocks for the set.
     * @return a copy of all special unlocks for the set.
     */
    public HashMap<Card, SpecialUnlock> specialUnlocks(){
        return new HashMap<>(this.SPECIAL_UNLOCKS);
    }
}
