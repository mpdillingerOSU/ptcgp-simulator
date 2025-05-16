package sim;

import java.util.*;

/**
 * An instance of this class is used to represent a card set collection.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class SetCollection {
    public final CardSet CARD_SET;
    private final HashMap<Card, Integer> CARD_COUNT = new HashMap<>();
    private int completeObtained = 0;
    private int standardObtained = 0;
    private int rareObtained = 0;
    private final HashMap<Rarity, Integer> RARITY_OBTAINED = new HashMap<>(){{
        for(Rarity rarity : Rarity.values()){
            put(rarity, 0);
        }
    }};

    private int packPoints;

    private final HashMap<Card, SpecialUnlock> SPECIAL_UNLOCKS;

    /**
     * Instantiates an instance of {@code SetCollection} for the provided card
     * set.
     * @param CARD_SET the card set for which to create a {@code
     * SetCollection} for.
     */
    public SetCollection(CardSet CARD_SET){
        this.CARD_SET = CARD_SET;

        for(int i = 0; i < completeTotal(); i++){
            CARD_COUNT.put(this.CARD_SET.CARDS[i], 0);
        }

        this.packPoints = 0;

        this.SPECIAL_UNLOCKS = this.CARD_SET.specialUnlocks();
    }

    /**
     * Adds the provided card to the collection.
     * @param card the card to be added to the collection.
     */
    private void add(Card card){
        if(CARD_COUNT.containsKey(card)){
            final int PREV = CARD_COUNT.get(card);
            CARD_COUNT.replace(card, PREV + 1);
            if(PREV == 0){
                completeObtained++;
                if(card.isStandardRarity()){
                    standardObtained++;
                } else {
                    rareObtained++;
                }
                RARITY_OBTAINED.replace(card.RARITY, RARITY_OBTAINED.get(card.RARITY) + 1);
            }
        }
    }

    /**
     * Helper method that performs the check for special unlocks.
     */
    private void unlockSpecials(){
        for(Card card : this.SPECIAL_UNLOCKS.keySet()){
            if(this.SPECIAL_UNLOCKS.get(card).check(this)){
                this.SPECIAL_UNLOCKS.remove(card);
                add(card);
            }
        }
    }

    /**
     * Adds the cards of the opened pack to the collection.
     * @param openedPack the opened pack whose cards are to be added to the
     * collection.
     */
    public void add(OpenedPack openedPack){
        for(Card card : openedPack.cards()){
            add(card);
        }

        addPackPoints(5);

        unlockSpecials();
    }

    /**
     * Purchases the provided card for the collection.
     * @param card the card to purchase for the collection.
     * @return {@code true}, if the card was successfully bought. Else, {@code
     * false}.
     */
    public boolean buyCard(Card card){
        if(card.isSpecialUnlock() || !CARD_COUNT.containsKey(card) || packPoints() < card.RARITY.PACK_POINT_COST){
            return false;
        }

        removePackPoints(card.packPoints());
        add(card);

        unlockSpecials();

        return true;
    }

    /**
     * Selects the provided wonder pick, and places the obtained card into the
     * collection.
     * @param wonderPick the wonder pick from which to obtain a card from.
     * @return the card obtained from the wonder pick, if being able to select
     * the wonder pick is possible. Else, {@code null} - possibly due to
     * insufficient wonder pick stamina.
     */
    public Card selectWonderPick(WonderPick wonderPick){
        if(!wonderPick.PACK.SET_NAME.equals(CARD_SET.SET_NAME)){
            return null;
        }

        Card selection = wonderPick.select();
        add(selection);

        unlockSpecials();

        return selection;
    }

    /**
     * Helper method to add pack points to the collection.
     * @param val the number of pack points to be added to the collection.
     */
    private void addPackPoints(int val){
        packPoints += val;
        if(packPoints > 2500){
            packPoints = 2500;
        }
    }

    /**
     * Helper method to remove pack points from the collection.
     * @param val the number of pack points to be removed from the collection.
     */
    private void removePackPoints(int val){
        packPoints -= val;
        if(packPoints < 0){
            packPoints = 0;
        }
    }

    /**
     * The total available pack points of the collection.
     * @return the total available pack points of the collection.
     */
    public int packPoints(){
        return packPoints;
    }

    /**
     * Returns whether all cards have been obtained for the complete
     * collection.
     * @return {@code true}, if all cards have been obtained for the complete
     * collection. Else, {@code false}.
     */
    public boolean hasAllCards(){
        return completeObtained == completeTotal();
    }

    /**
     * Returns whether all cards have been obtained for the provided pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all cards have been obtained for the pack.
     * Else, {@code false}.
     */
    public boolean hasAllCards(Pack pack){
        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && hasCard(this.CARD_SET.CARDS[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether all cards of the provided rarity have been obtained for
     * the collection.
     * @param rarity the rarity to be checked.
     * @return {@code true}, if all cards of the provided rarity have been
     * obtained for the collection. Else, {@code false}.
     */
    public boolean hasAllCardsOfRarity(Rarity rarity){
        return Objects.equals(RARITY_OBTAINED.get(rarity), rarityTotal(rarity));
    }

    /**
     * Returns whether all cards of the provided rarity have been obtained for
     * the provided pack.
     * @param rarity the rarity to be checked.
     * @return {@code true}, if all cards of the provided rarity have been
     * obtained for the provided pack. Else, {@code false}.
     */
    public boolean hasAllCardsOfRarity(Pack pack, Rarity rarity){
        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].RARITY.equals(rarity) && hasCard(this.CARD_SET.CARDS[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether all standard cards have been obtained for the
     * collection.
     * @return {@code true}, if all standard cards have been obtained for the
     * collection. Else, {@code false}.
     */
    public boolean hasAllStandardCards(){
        return standardObtained == standardTotal();
    }

    /**
     * Returns whether all standard cards have been obtained for the provided
     * pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all standard cards have been obtained for the
     * provided pack. Else, {@code false}.
     */
    public boolean hasAllStandardCards(Pack pack){
        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isStandardRarity() && hasCard(this.CARD_SET.CARDS[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether all rare cards have been obtained for the collection.
     * @return {@code true}, if all rare cards have been obtained for the
     * collection. Else, {@code false}.
     */
    public boolean hasAllRareCards(){
        return rareObtained == rareTotal();
    }

    /**
     * Returns whether all rare cards have been obtained for the provided
     * pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all rare cards have been obtained for the
     * provided pack. Else, {@code false}.
     */
    public boolean hasAllRareCards(Pack pack){
        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isRareRarity() && hasCard(this.CARD_SET.CARDS[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether all exclusive cards have been obtained for the provided
     * pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all exclusive cards have been obtained for the
     * provided pack. Else, {@code false}.
     */
    public boolean hasAllExclusives(Pack pack){
        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isExclusivelyIn(pack.NAME) && hasCard(this.CARD_SET.CARDS[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether all exclusive cards of the provided rarity have been
     * obtained for the provided pack.
     * @param pack the pack to be checked.
     * @param rarity the rarity to be checked.
     * @return {@code true}, if all exclusive cards of the provided rarity
     * have been obtained for the provided pack. Else, {@code false}.
     */
    public boolean hasAllExclusivesOfRarity(Pack pack, Rarity rarity){
        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].RARITY == rarity && this.CARD_SET.CARDS[i].isExclusivelyIn(pack.NAME) && hasCard(this.CARD_SET.CARDS[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * Helper method that returns the probability of a card occurrence for the
     * provided pack.
     * @param pack the pack to be checked.
     * @param checker the card checker used to inspect the cards.
     * @return the probability of the occurrence in the form of a double.
     */
    private double chanceOf(Pack pack, CardChecker checker) {
        //Returns and calculates based on double, as it would otherwise take too long to calculate the gcd due to the iterative process

        double firstPull = 0;
        double secondPull = 0;
        double thirdPull = 0;
        double fourthPull = 0;
        double fifthPull = 0;

        for(int i = 0; i < completeTotal(); i++) {
            if (checker.inspect(this.CARD_SET.CARDS[i])) {
                firstPull += pack.PACK_RARITY_RATES.DRAW_ONE.ofRarity(this.CARD_SET.CARDS[i].RARITY).VAL / pack.cardsOf(this.CARD_SET.CARDS[i].RARITY);
                secondPull += pack.PACK_RARITY_RATES.DRAW_TWO.ofRarity(this.CARD_SET.CARDS[i].RARITY).VAL / pack.cardsOf(this.CARD_SET.CARDS[i].RARITY);
                thirdPull += pack.PACK_RARITY_RATES.DRAW_THREE.ofRarity(this.CARD_SET.CARDS[i].RARITY).VAL / pack.cardsOf(this.CARD_SET.CARDS[i].RARITY);
                fourthPull += pack.PACK_RARITY_RATES.DRAW_FOUR.ofRarity(this.CARD_SET.CARDS[i].RARITY).VAL / pack.cardsOf(this.CARD_SET.CARDS[i].RARITY);
                fifthPull += pack.PACK_RARITY_RATES.DRAW_FIVE.ofRarity(this.CARD_SET.CARDS[i].RARITY).VAL / pack.cardsOf(this.CARD_SET.CARDS[i].RARITY);
            }
        }

        return 1 - (1 - firstPull) * (1 - secondPull) * (1 - thirdPull) * (1 - fourthPull) * (1 - fifthPull);
    }

    /**
     * Returns the probability of pulling a new card from the provided pack.
     * @param pack the pack to be checked.
     * @return a double representing the probability of pulling a new card
     * from the provided pack.
     */
    public double chanceOfPullingNewCard(Pack pack){
        return chanceOf(pack, (card) -> card.isIn(pack.NAME) && !hasCard(card));
    }

    /**
     * Returns the probability of pulling a new card of the provided rarity
     * from the provided pack.
     * @param pack the pack to be checked.
     * @param rarity the rarity to be checked.
     * @return a double representing the probability of pulling a new card of
     * the provided rarity from the provided pack.
     */
    public double chanceOfPullingNewCardOfRarity(Pack pack, Rarity rarity){
        return chanceOf(pack, (card) -> card.RARITY.equals(rarity) && card.isIn(pack.NAME) && !hasCard(card));
    }

    /**
     * Returns the probability of pulling a new standard card from the
     * provided pack.
     * @param pack the pack to be checked.
     * @return a double representing the probability of pulling a new standard
     * card from the provided pack.
     */
    public double chanceOfPullingNewStandardCard(Pack pack){
        return chanceOf(pack, (card) -> card.isStandardRarity() && card.isIn(pack.NAME) && !hasCard(card));
    }

    /**
     * Returns the probability of pulling a new rare card from the provided
     * pack.
     * @param pack the pack to be checked.
     * @return a double representing the probability of pulling a new rare
     * card from the provided pack.
     */
    public double chanceOfPullingNewRareCard(Pack pack){
        return chanceOf(pack, (card) -> card.isRareRarity() && card.isIn(pack.NAME) && !hasCard(card));
    }

    public ArrayList<Pair<Pack, Double>> packsCompletePullChances(){
        return new ArrayList<>(){{
            for(Pack pack : CARD_SET.packs()){
                add(new Pair<>(pack, chanceOfPullingNewCard(pack)));
            }
        }};
    }

    public ArrayList<Pair<Pack, Double>> packsStandardPullChances(){
        return new ArrayList<>(){{
            for(Pack pack : CARD_SET.packs()){
                add(new Pair<>(pack, chanceOfPullingNewStandardCard(pack)));
            }
        }};
    }

    public ArrayList<Pair<Pack, Double>> packsRarePullChances(){
        return new ArrayList<>(){{
            for(Pack pack : CARD_SET.packs()){
                add(new Pair<>(pack, chanceOfPullingNewRareCard(pack)));
            }
        }};
    }

    public ArrayList<Pair<Pack, Double>> packsRarityPullChances(Rarity rarity){
        return new ArrayList<>(){{
            for(Pack pack : CARD_SET.packs()){
                add(new Pair<>(pack, chanceOfPullingNewCardOfRarity(pack, rarity)));
            }
        }};
    }

    /**
     * Helper method that returns a list of the packs with the highest chance
     * of pulling a new card.
     * @return a list of the packs with the highest chance of pulling a new
     * card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewCard(){
        return ProbabilityAnalysis.reduceToHighestChance(packsCompletePullChances());
    }

    /**
     * Helper method that returns a list of the packs with the highest chance
     * of pulling a new standard card.
     * @return a list of the packs with the highest chance of pulling a new
     * standard card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewStandardCard(){
        return ProbabilityAnalysis.reduceToHighestChance(packsStandardPullChances());
    }

    /**
     * Helper method that returns a list of the packs with the highest chance
     * of pulling a new rare card.
     * @return a list of the packs with the highest chance of pulling a new
     * rare card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewRareCard(){
        return ProbabilityAnalysis.reduceToHighestChance(packsRarePullChances());
    }

    /**
     * Helper method that returns a list of the packs with the highest chance
     * of pulling a new card of the provided rarity.
     * @param rarity the rarity to be checked.
     * @return a list of the packs with the highest chance of pulling a new
     * card of the provided rarity.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewCardOfRarity(Rarity rarity){
        return ProbabilityAnalysis.reduceToHighestChance(packsRarityPullChances(rarity));
    }

    /**
     * Helper method that returns a list of the packs with the lowest chance
     * of pulling a new card.
     * @return a list of the packs with the lowest chance of pulling a new
     * card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewCard(){
        return ProbabilityAnalysis.reduceToLowestChance(packsCompletePullChances());
    }

    /**
     * Helper method that returns a list of the packs with the lowest chance
     * of pulling a new standard card.
     * @return a list of the packs with the lowest chance of pulling a new
     * standard card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewStandardCard(){
        return ProbabilityAnalysis.reduceToLowestChance(packsStandardPullChances());
    }

    /**
     * Helper method that returns a list of the packs with the lowest chance
     * of pulling a new rare card.
     * @return a list of the packs with the lowest chance of pulling a new
     * rare card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewRareCard(){
        return ProbabilityAnalysis.reduceToLowestChance(packsRarePullChances());
    }

    /**
     * Helper method that returns a list of the packs with the lowest chance
     * of pulling a new card of the provided rarity.
     * @param rarity the rarity to be checked.
     * @return a list of the packs with the lowest chance of pulling a new
     * card of the provided rarity.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewCardOfRarity(Rarity rarity){
        return ProbabilityAnalysis.reduceToLowestChance(packsRarityPullChances(rarity));
    }

    /**
     * Returns the number of cards collected for the complete set.
     * @return the number of cards collected for the complete set.
     */
    public int completeObtained(){
        return completeObtained;
    }

    /**
     * Returns the number of cards collected for the provided pack.
     * @param pack the pack to be checked.
     * @return the number of cards collected for the provided pack.
     */
    public int completeObtained(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the number of cards not yet collected for the complete set.
     * @return the number of cards not yet collected for the complete set.
     */
    public int completeUnobtained(){
        return completeTotal() - completeObtained();
    }

    /**
     * Returns the number of cards not yet collected for the provided pack.
     * @param pack the pack to be checked.
     * @return the number of cards not yet collected for the provided pack.
     */
    public int completeUnobtained(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && !hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the total number of cards in the complete set.
     * @return the total number of cards in the complete set.
     */
    public int completeTotal(){
        return CARD_SET.CARDS.length;
    }

    /**
     * Returns the total number of cards in the pack.
     * @param pack the pack to be checked.
     * @return the total number of cards in the pack.
     */
    public int completeTotal(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME)){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the number of cards collected for the standard set.
     * @return the number of cards collected for the standard set.
     */
    public int standardObtained(){
        return standardObtained;
    }

    /**
     * Returns the number of standard cards collected for the provided pack.
     * @param pack the pack to be checked.
     * @return the number of standard cards collected for the provided pack.
     */
    public int standardObtained(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isStandardRarity() && hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the number of cards not yet collected for the standard set.
     * @return the number of cards not yet collected for the standard set.
     */
    public int standardUnobtained(){
        return standardTotal() - standardObtained();
    }

    /**
     * Returns the number of standard cards not yet collected for the provided
     * pack.
     * @param pack the pack to be checked.
     * @return the number of standard cards not yet collected for the provided
     * pack.
     */
    public int standardUnobtained(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isStandardRarity() && !hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the total number of cards in the standard set.
     * @return the total number of cards in the standard set.
     */
    public int standardTotal(){
        return CARD_SET.standardTotal();
    }

    /**
     * Returns the total number of standard cards in the pack.
     * @param pack the pack to be checked.
     * @return the total number of standard cards in the pack.
     */
    public int standardTotal(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isStandardRarity()){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the number of cards collected for the rare set.
     * @return the number of cards collected for the rare set.
     */
    public int rareObtained(){
        return rareObtained;
    }

    /**
     * Returns the number of rare cards collected for the provided pack.
     * @param pack the pack to be checked.
     * @return the number of rare cards collected for the provided pack.
     */
    public int rareObtained(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isRareRarity() && hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the number of cards not yet collected for the rare set.
     * @return the number of cards not yet collected for the rare set.
     */
    public int rareUnobtained(){
        return rareTotal() - rareObtained();
    }

    /**
     * Returns the number of rare cards not yet collected for the provided
     * pack.
     * @param pack the pack to be checked.
     * @return the number of rare cards not yet collected for the provided
     * pack.
     */
    public int rareUnobtained(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isRareRarity() && !hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the total number of cards in the rare set.
     * @return the total number of cards in the rare set.
     */
    public int rareTotal(){
        return CARD_SET.rareTotal();
    }

    /**
     * Returns the total number of rare cards in the pack.
     * @param pack the pack to be checked.
     * @return the total number of rare cards in the pack.
     */
    public int rareTotal(Pack pack){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isRareRarity()){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the number of cards of the provided rarity collected for the
     * complete set.
     * @param rarity the rarity to be checked.
     * @return the number of cards of the provided rarity collected for the
     * complete set.
     */
    public int rarityObtained(Rarity rarity){
        return this.RARITY_OBTAINED.get(rarity);
    }

    /**
     * Returns the number of cards of the provided rarity collected for the
     * provided pack.
     * @param pack the pack to be checked.
     * @param rarity the rarity to be checked.
     * @return the number of cards of the provided rarity collected for the
     * provided pack.
     */
    public int rarityObtained(Pack pack, Rarity rarity){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].RARITY.equals(rarity) && hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the number of cards of the provided rarity not yet collected
     * for the complete set.
     * @param rarity the rarity to be checked.
     * @return the number of cards of the provided rarity not yet collected
     * for the complete set.
     */
    public int rarityUnobtained(Rarity rarity){
        return rarityTotal(rarity) - rarityObtained(rarity);
    }

    /**
     * Returns the number of cards of the provided rarity not yet collected
     * for the provided pack.
     * @param pack the pack to be checked.
     * @param rarity the rarity to be checked.
     * @return the number of cards of the provided rarity not yet collected
     * for the provided pack.
     */
    public int rarityUnobtained(Pack pack, Rarity rarity){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].RARITY.equals(rarity) && !hasCard(this.CARD_SET.CARDS[i])){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns the total number of cards of the provided rarity in the
     * complete set.
     * @param rarity the rarity to be checked.
     * @return the total number of cards of the provided rarity in the
     * complete set.
     */
    public int rarityTotal(Rarity rarity){
        return CARD_SET.rarityTotal(rarity);
    }

    /**
     * Returns the total number of cards of the provided rarity in the pack.
     * @param pack the pack to be checked.
     * @param rarity the rarity to be checked.
     * @return the total number of cards of the provided rarity in the pack.
     */
    public int rarityTotal(Pack pack, Rarity rarity){
        int result = 0;

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].RARITY.equals(rarity)){
                result++;
            }
        }

        return result;
    }

    /**
     * Returns as {@code ArrayList} of all cards within the set that have been
     * obtained.
     * @return all cards within the set that have been obtained.
     */
    public ArrayList<Card> obtainedCards(){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all cards within the provided pack that
     * have been obtained.
     * @return all cards within the provided pack that have been obtained.
     */
    public ArrayList<Card> obtainedCards(Pack pack){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all standard cards within the set that
     * have been obtained.
     * @return all standard cards within the set that have been obtained.
     */
    public ArrayList<Card> obtainedStandardCards(){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isStandardRarity() && hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all standard cards within the provided
     * pack that have been obtained.
     * @return all standard cards within the provided pack that have been
     * obtained.
     */
    public ArrayList<Card> obtainedStandardCards(Pack pack){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isStandardRarity() && hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all rare cards within the set that have
     * been obtained.
     * @return all rare cards within the set that have been obtained.
     */
    public ArrayList<Card> obtainedRareCards(){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isRareRarity() && hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all rare cards within the provided pack
     * that have been obtained.
     * @return all rare cards within the provided pack that have been
     * obtained.
     */
    public ArrayList<Card> obtainedRareCards(Pack pack){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isRareRarity() && hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all cards of the provided rarity within
     * the set that have been obtained.
     * @param rarity the rarity to be checked.
     * @return all cards of the provided rarity within the set that have been
     * obtained.
     */
    public ArrayList<Card> obtainedCardsOfRarity(Rarity rarity){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].RARITY.equals(rarity) && hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all cards of the provided rarity within
     * the provided pack that have been obtained.
     * @param pack the pack to be checked.
     * @param rarity the rarity to be checked.
     * @return all cards of the provided rarity within the provided pack that
     * have been obtained.
     */
    public ArrayList<Card> obtainedCardsOfRarity(Pack pack, Rarity rarity){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].RARITY.equals(rarity) && hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all cards within the set that have not
     * yet been obtained.
     * @return all cards within the set that have not yet been obtained.
     */
    public ArrayList<Card> unobtainedCards(){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(!hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all cards within the provided pack that
     * have not yet been obtained.
     * @return all cards within the provided pack that have not yet been
     * obtained.
     */
    public ArrayList<Card> unobtainedCards(Pack pack){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && !hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all standard cards within the set that
     * have not yet been obtained.
     * @return all standard cards within the set that have not yet been
     * obtained.
     */
    public ArrayList<Card> unobtainedStandardCards(){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isStandardRarity() && !hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all standard cards within the provided
     * pack that have not yet been obtained.
     * @return all standard cards within the provided pack that have not yet
     * been obtained.
     */
    public ArrayList<Card> unobtainedStandardCards(Pack pack){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isStandardRarity() && !hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all rare cards within the set that have
     * not yet been obtained.
     * @return all rare cards within the set that have not yet been obtained.
     */
    public ArrayList<Card> unobtainedRareCards(){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isRareRarity() && !hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all rare cards within the provided pack
     * that have not yet been obtained.
     * @return all rare cards within the provided pack that have not yet been
     * obtained.
     */
    public ArrayList<Card> unobtainedRareCards(Pack pack){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].isRareRarity() && !hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all cards of the provided rarity within
     * the set that have not yet been obtained.
     * @param rarity the rarity to be checked.
     * @return all cards of the provided rarity within the set that have not
     * yet been obtained.
     */
    public ArrayList<Card> unobtainedCardsOfRarity(Rarity rarity){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].RARITY.equals(rarity) && !hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns as {@code ArrayList} of all cards of the provided rarity within
     * the provided pack that have not yet been obtained.
     * @param pack the pack to be checked.
     * @param rarity the rarity to be checked.
     * @return all cards of the provided rarity within the provided pack that
     * have not yet been obtained.
     */
    public ArrayList<Card> unobtainedCardsOfRarity(Pack pack, Rarity rarity){
        final ArrayList<Card> RESULT = new ArrayList<>();

        for(int i = 0; i < completeTotal(); i++){
            if(this.CARD_SET.CARDS[i].isIn(pack.NAME) && this.CARD_SET.CARDS[i].RARITY.equals(rarity) && !hasCard(this.CARD_SET.CARDS[i])){
                RESULT.add(this.CARD_SET.CARDS[i]);
            }
        }

        return RESULT;
    }

    /**
     * Returns whether the collection contains the provided card.
     * @param card the card to be checked.
     * @return {@code true}, if the collection contains the provided card.
     * Else, {@code false}.
     */
    public boolean hasCard(Card card){
        return cardCount(card) > 0;
    }

    /**
     * Returns the number of instances of a card within the collection.
     * @param card the card to obtain the count for.
     * @return the number of instances of a card within the collection.
     */
    public int cardCount(Card card){
        return this.CARD_COUNT.get(card);
    }

    /**
     * Returns the percentage of the complete set that has been obtained.
     * @return the percentage of the complete set that has been obtained.
     */
    public double completeObtainedPercentage(){
        return completeObtained() * 100d / completeTotal();
    }

    /**
     * Returns the percentage of the standard set that has been obtained.
     * @return the percentage of the standard set that has been obtained.
     */
    public double standardObtainedPercentage(){
        return standardObtained() * 100d / standardTotal();
    }

    /**
     * Returns the percentage of the rare set that has been obtained.
     * @return the percentage of the rare set that has been obtained.
     */
    public double rareObtainedPercentage(){
        return rareObtained() * 100d / rareTotal();
    }

    /**
     * Returns the percentage of the provided rarity that has been obtained.
     * @return the percentage of the provided rarity that has been obtained.
     */
    public double rarityObtainedPercentage(Rarity rarity){
        return rarityObtained(rarity) * 100d / rarityUnobtained(rarity);
    }
}
