package sim;

import java.util.Arrays;

/**
 * An instance of this class is used to represent a single playing card.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public abstract class Card {
    public final int CARD_NUM;
    public final CardType CARD_TYPE;
    public final String NAME;
    public final PackName[] PACK_NAMES;
    public final Rarity RARITY;
    private final boolean IS_PACK_EXCLUSIVE;

    /**
     * Instantiates an instance of {@code Card}, with the provided card
     * number, card type, name, rarity, and the names of the packs within
     * which it can be found.
     * @param CARD_NUM the ordered number of the card within the set.
     * @param CARD_TYPE the type of card.
     * @param NAME the name of the card.
     * @param RARITY the rarity of the card.
     * @param PACK_NAMES the names of the packs within which the card can be
     * found.
     */
    public Card(final int CARD_NUM, final CardType CARD_TYPE, final String NAME, final Rarity RARITY, final PackName[] PACK_NAMES){
        this.CARD_NUM = CARD_NUM;
        this.CARD_TYPE = CARD_TYPE;
        this.NAME = NAME;
        this.RARITY = RARITY;
        this.PACK_NAMES = PACK_NAMES != null && PACK_NAMES.length > 0 ? Arrays.copyOf(PACK_NAMES, PACK_NAMES.length) : null;
        this.IS_PACK_EXCLUSIVE = this.PACK_NAMES != null && this.PACK_NAMES.length == 1;
    }

    /**
     * Returns whether the instance can be found within the pack of the
     * provided name.
     * @param packName the name within which to check if the instance is a
     * member of.
     * @return {@code true}, if the instance is within the provided pack.
     * Else, {@code false}.
     */
    public boolean isIn(PackName packName){
        if(this.PACK_NAMES != null){
            for(int i = 0; i < PACK_NAMES.length; i++){
                if(PACK_NAMES[i].equals(packName)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns whether the instance is an exclusive for the pack of the
     * provided name.
     * @param packName the name of the pack with which to check the
     * exclusivity of the instance.
     * @return {@code true}, if the instance is exclusively within the pack of
     * the provided name. Else, {@code false}.
     */
    public boolean isExclusivelyIn(PackName packName){
        return IS_PACK_EXCLUSIVE && isIn(packName);
    }

    /**
     * Returns whether the instance is of standard rarity.
     * @return {@code true}, if the instance is of standard rarity. Else,
     * {@code false}.
     */
    public boolean isStandardRarity(){
        return RARITY.ordinal() <= Rarity.DDDD.ordinal();
    }

    /**
     * Returns whether the instance is of rare rarity.
     * @return {@code true}, if the instance is of rare rarity. Else, {@code
     * false}.
     */
    public boolean isRareRarity(){
        return RARITY.ordinal() >= Rarity.S.ordinal();
    }

    /**
     * Returns whether the instance is only obtainable through special unlock.
     * @return {@code true}, if the instance is only obtainable through
     * special unlock. Else, {@code false}.
     */
    public boolean isSpecialUnlock(){
        return this.PACK_NAMES == null;
    }

    /**
     * Returns the pack points needed to purchase the instance.
     * @return the pack points needed to purchase the instance, if it is
     * purchasable. Else, {@code -1}. (Note that a card is only unpurchasable
     * if requires a special unlock.
     */
    public int packPoints(){
        return isSpecialUnlock() ? -1 : RARITY.PACK_POINT_COST;
    }
}
