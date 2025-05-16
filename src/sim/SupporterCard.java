package sim;

/**
 * An instance of this class is used to represent a single supporter card.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class SupporterCard extends Card {
    /**
     * Instantiates an instance of {@code SupporterCard}, with the provided
     * card number, name, rarity, and the names of the packs within which it
     * can be found.
     * @param CARD_NUM the ordered number of the card within the set.
     * @param NAME the name of the card.
     * @param RARITY the rarity of the card.
     * @param PACK_NAMES the names of the packs within which the card can be
     * found.
     */
    public SupporterCard(final int CARD_NUM, final String NAME, final Rarity RARITY,
                    final PackName[] PACK_NAMES) {
        super(CARD_NUM, CardType.SUPPORTER, NAME, RARITY, PACK_NAMES);
    }
}
