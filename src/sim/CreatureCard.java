package sim;

/**
 * An instance of this class is used to represent a single creature card.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class CreatureCard extends Card {
    public final Creature CREATURE;
    public final CreatureCardType TYPE;

    /**
     * Instantiates an instance of {@code CreatureCard}, with the provided
     * card number, creature, creature card type, rarity, and the names of the
     * packs within which it can be found.
     * @param CARD_NUM the ordered number of the card within the set.
     * @param CREATURE the creature represented by the card.
     * @param TYPE the creature card type of the card.
     * @param RARITY the rarity of the card.
     * @param PACK_NAMES the names of the packs within which the card can be
     * found.
     */
    public CreatureCard(final int CARD_NUM, final Creature CREATURE,
                        final CreatureCardType TYPE, final Rarity RARITY,
                        final PackName[] PACK_NAMES) {
        super(CARD_NUM, CardType.CREATURE, CREATURE.NAME + (TYPE.equals(CreatureCardType.EX) ? " EX" : ""), RARITY, PACK_NAMES);

        this.CREATURE = CREATURE;
        this.TYPE = TYPE;
    }
}
