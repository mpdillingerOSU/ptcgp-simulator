package sim;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An instance of this class is used to represent a card collection made up
 * of multiple {@code SetCollection} instances.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class CardCollection {
    private final HashMap<SetName, SetCollection> SET_COLLECTIONS = new HashMap<>();

    /**
     * Instantiates and instance of {@code CardCollection}.
     */
    public CardCollection(){
        this.SET_COLLECTIONS.put(SetName.GENETIC_APEX, new SetCollection(CardSets.GENETIC_APEX));
        this.SET_COLLECTIONS.put(SetName.MYTHICAL_ISLAND, new SetCollection(CardSets.MYTHICAL_ISLAND));
        this.SET_COLLECTIONS.put(SetName.SPACE_TIME_SMACKDOWN, new SetCollection(CardSets.SPACE_TIME_SMACKDOWN));
        this.SET_COLLECTIONS.put(SetName.TRIUMPHANT_LIGHT, new SetCollection(CardSets.TRIUMPHANT_LIGHT));
        this.SET_COLLECTIONS.put(SetName.SHINING_REVELRY, new SetCollection(CardSets.SHINING_REVELRY));
        this.SET_COLLECTIONS.put(SetName.CELESTIAL_GUARDIANS, new SetCollection(CardSets.CELESTIAL_GUARDIANS));
        this.SET_COLLECTIONS.put(SetName.EXTRADIMENSIONAL_CRISIS, new SetCollection(CardSets.EXTRADIMENSIONAL_CRISIS));
    }

    /**
     * Adds the opened pack to the instance.
     * @param openedPack the opened pack to add to the instance.
     * @return {@code true}, as the opened pack is always successfully added
     * to the instance.
     */
    public boolean add(OpenedPack openedPack){
        SET_COLLECTIONS.get(openedPack.PACK.SET_NAME).add(openedPack);

        return true;
    }

    /**
     * Returns the total pack points accumulated for the set with the provided
     * name.
     * @param setName the name of the set for which to obtain the accumulated
     * pack point total of.
     * @return the total points accumulated for the set with the provided
     * name.
     */
    public int packPointsFor(SetName setName){
        return SET_COLLECTIONS.get(setName).packPoints();
    }

    /**
     * Attempts to purchase a given card for the set with the provided name.
     * This will deduct the necessary pack points from the provided set.
     * @param setName the same of the set for which to buy the card from.
     * @param card the card to be purchased.
     * @return {@code true}, if the card was successfully purchased. Else,
     * {@code false} - possibly due to either insufficient pack points, or the
     * card not being purchasable.
     */
    public boolean buyCard(SetName setName, Card card){
        return SET_COLLECTIONS.get(setName).buyCard(card);
    }

    /**
     * Selects the provided wonder pick, and places the obtained card into the
     * card collection.
     * @param wonderPick the wonder pick from which to obtain a card from.
     * @return the card obtained from the wonder pick, if being able to select
     * the wonder pick is possible. Else, {@code null} - possibly due to
     * insufficient wonder pick stamina.
     */
    public Card selectWonderPick(WonderPick wonderPick){
        return this.SET_COLLECTIONS.get(wonderPick.PACK.SET_NAME).selectWonderPick(wonderPick);
    }

    /**
     * Returns the percentage of the obtained cards for the complete set with
     * the provided name.
     * @param setName the name of the set to obtain the completion percentage
     * of.
     * @return the percentage of the obtained cards for the complete set with
     * the provided name.
     */
    public double completeObtainedPercentage(SetName setName){
        return this.SET_COLLECTIONS.get(setName).completeObtainedPercentage();
    }

    public double completeObtainedPercentage(ArrayList<SetName> setNames){
        int obtained = 0;
        int total = 0;

        for(SetName setName : setNames){
            obtained += this.SET_COLLECTIONS.get(setName).completeObtained();
            total += this.SET_COLLECTIONS.get(setName).completeTotal();
        }

        return obtained * 100d / total;
    }

    /**
     * Returns the percentage of the obtained cards for the standard set with
     * the provided name.
     * @param setName the name of the set to obtain the completion percentage
     * of.
     * @return the percentage of the obtained cards for the standard set with
     * the provided name.
     */
    public double standardObtainedPercentage(SetName setName){
        return this.SET_COLLECTIONS.get(setName).standardObtainedPercentage();
    }

    public double standardObtainedPercentage(ArrayList<SetName> setNames){
        int obtained = 0;
        int total = 0;

        for(SetName setName : setNames){
            obtained += this.SET_COLLECTIONS.get(setName).standardObtained();
            total += this.SET_COLLECTIONS.get(setName).standardTotal();
        }

        return obtained * 100d / total;
    }

    /**
     * Returns the percentage of the obtained cards for the rare set with the
     * provided name.
     * @param setName the name of the set to obtain the completion percentage
     * of.
     * @return the percentage of the obtained cards for the rare set with the
     * provided name.
     */
    public double rareObtainedPercentage(SetName setName){
        return this.SET_COLLECTIONS.get(setName).rareObtainedPercentage();
    }

    public double rareObtainedPercentage(ArrayList<SetName> setNames){
        int obtained = 0;
        int total = 0;

        for(SetName setName : setNames){
            obtained += this.SET_COLLECTIONS.get(setName).rareObtained();
            total += this.SET_COLLECTIONS.get(setName).rareTotal();
        }

        return obtained * 100d / total;
    }

    /**
     * Returns the percentage of the obtained cards of the provided rarity
     * within the set with the provided name.
     * @param setName the name of the set to obtain the completion percentage
     * of.
     * @param rarity the rarity of the cards.
     * @return the percentage of the obtained cards of the provided rarity
     * within the set with the provided name.
     */
    public double rarityObtainedPercentage(SetName setName, Rarity rarity){
        return this.SET_COLLECTIONS.get(setName).rarityObtained(rarity);
    }

    public double rarityObtainedPercentage(ArrayList<SetName> setNames, Rarity rarity){
        int obtained = 0;
        int total = 0;

        for(SetName setName : setNames){
            obtained += this.SET_COLLECTIONS.get(setName).rarityObtained(rarity);
            total += this.SET_COLLECTIONS.get(setName).rarityTotal(rarity);
        }

        return obtained * 100d / total;
    }

    /**
     * Returns whether the collection contains all cards of the set with the
     * provided name.
     * @param setName the name of the set to be checked.
     * @return {@code true}, if all cards have been obtained in the provided
     * set. Else, {@code false}.
     */
    public boolean hasAllCards(SetName setName){
        return this.SET_COLLECTIONS.get(setName).hasAllCards();
    }

    public boolean hasAllCards(ArrayList<SetName> setNames){
        for(SetName setName : setNames){
            if(!hasAllCards(setName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether the collection contains all standard cards of the set
     * with the provided name.
     * @param setName the name of the set to be checked.
     * @return {@code true}, if all standard cards have been obtained in the
     * provided set. Else, {@code false}.
     */
    public boolean hasAllStandardCards(SetName setName){
        return this.SET_COLLECTIONS.get(setName).hasAllStandardCards();
    }

    public boolean hasAllStandardCards(ArrayList<SetName> setNames){
        for(SetName setName : setNames){
            if(!hasAllStandardCards(setName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether the collection contains all rare cards of the set with
     * the provided name.
     * @param setName the name of the set to be checked.
     * @return {@code true}, if all rare cards have been obtained in the
     * provided set. Else, {@code false}.
     */
    public boolean hasAllRareCards(SetName setName){
        return this.SET_COLLECTIONS.get(setName).hasAllRareCards();
    }

    public boolean hasAllRareCards(ArrayList<SetName> setNames){
        for(SetName setName : setNames){
            if(!hasAllRareCards(setName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether the collection contains all cards of the provided
     * rarity for the set with the provided name.
     * @param setName the name of the set to be checked.
     * @param rarity the rarity of the cards.
     * @return {@code true}, if all cards of the provided rarity have been
     * obtained in the provided set. Else, {@code false}.
     */
    public boolean hasAllCardsOfRarity(SetName setName, Rarity rarity){
        return this.SET_COLLECTIONS.get(setName).hasAllCardsOfRarity(rarity);
    }

    public boolean hasAllCardsOfRarity(ArrayList<SetName> setNames, Rarity rarity){
        for(SetName setName : setNames){
            if(!hasAllCardsOfRarity(setName, rarity)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether the collection contains all cards of the pack with the
     * provided name.
     * @param pack the pack to be checked.
     * @return {@code true}, if all cards have been obtained in the provided
     * pack. Else, {@code false}.
     */
    public boolean hasAllCards(Pack pack){
        return this.SET_COLLECTIONS.get(pack.SET_NAME).hasAllCards(pack);
    }

    /**
     * Returns whether the collection contains all standard cards of the
     * provided pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all standard cards have been obtained in the
     * provided pack. Else, {@code false}.
     */
    public boolean hasAllStandardCards(Pack pack){
        return this.SET_COLLECTIONS.get(pack.SET_NAME).hasAllStandardCards(pack);
    }

    /**
     * Returns whether the collection contains all rare cards of the provided
     * pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all rare cards have been obtained in the
     * provided pack. Else, {@code false}.
     */
    public boolean hasAllRareCards(Pack pack){
        return this.SET_COLLECTIONS.get(pack.SET_NAME).hasAllRareCards(pack);
    }

    /**
     * Returns whether the collection contains all cards of the provided
     * rarity for the provided pack.
     * @param pack the pack to be checked.
     * @param rarity the rarity of the cards.
     * @return {@code true}, if all cards of the provided rarity have been
     * obtained in the provided pack. Else, {@code false}.
     */
    public boolean hasAllCardsOfRarity(Pack pack, Rarity rarity){
        return this.SET_COLLECTIONS.get(pack.SET_NAME).hasAllCardsOfRarity(pack, rarity);
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the highest
     * chance of pulling a new card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the highest chance of pulling a new card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewCard(SetName setName){
        return this.SET_COLLECTIONS.get(setName).packsHighestChanceOfPullingNewCard();
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewCard(ArrayList<SetName> setNames){
        return ProbabilityAnalysis.reduceToHighestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsCompletePullChances());
                }
            }}
        );
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the highest
     * chance of pulling a new standard card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the highest chance of pulling a new
     * standard card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewStandardCard(SetName setName){
        return this.SET_COLLECTIONS.get(setName).packsHighestChanceOfPullingNewStandardCard();
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewStandardCard(ArrayList<SetName> setNames){
        return ProbabilityAnalysis.reduceToHighestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsStandardPullChances());
                }
            }}
        );
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the highest
     * chance of pulling a new rare card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the highest chance of pulling a new rare
     * card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewRareCard(SetName setName){
        return this.SET_COLLECTIONS.get(setName).packsHighestChanceOfPullingNewRareCard();
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewRareCard(ArrayList<SetName> setNames){
        return ProbabilityAnalysis.reduceToHighestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsRarePullChances());
                }
            }}
        );
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the highest
     * chance of pulling a new card of the provided rarity.
     * @param setName the name of the set to be checked.
     * @param rarity the rarity of the cards.
     * @return the packs that have the highest chance of pulling a new card of
     * the provided rarity.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewCardOfRarity(SetName setName, Rarity rarity){
        return this.SET_COLLECTIONS.get(setName).packsHighestChanceOfPullingNewCardOfRarity(rarity);
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewCardOfRarity(ArrayList<SetName> setNames, Rarity rarity){
        return ProbabilityAnalysis.reduceToHighestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsRarityPullChances(rarity));
                }
            }}
        );
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the lowest chance
     * of pulling a new card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the lowest chance of pulling a new card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewCard(SetName setName){
        return this.SET_COLLECTIONS.get(setName).packsLowestChanceOfPullingNewCard();
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewCard(ArrayList<SetName> setNames){
        return ProbabilityAnalysis.reduceToLowestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsCompletePullChances());
                }
            }}
        );
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the lowest chance
     * of pulling a new standard card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the lowest chance of pulling a new standard
     * card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewStandardCard(SetName setName){
        return this.SET_COLLECTIONS.get(setName).packsLowestChanceOfPullingNewStandardCard();
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewStandardCard(ArrayList<SetName> setNames){
        return ProbabilityAnalysis.reduceToLowestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsStandardPullChances());
                }
            }}
        );
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the lowest chance
     * of pulling a new rare card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the lowest chance of pulling a new rare
     * card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewRareCard(SetName setName){
        return this.SET_COLLECTIONS.get(setName).packsLowestChanceOfPullingNewRareCard();
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewRareCard(ArrayList<SetName> setNames){
        return ProbabilityAnalysis.reduceToLowestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsRarePullChances());
                }
            }}
        );
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the lowest chance
     * of pulling a new card of the provided rarity.
     * @param setName the name of the set to be checked.
     * @param rarity the rarity of the cards.
     * @return the packs that have the lowest chance of pulling a new card of
     * the provided rarity.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewCardOfRarity(SetName setName, Rarity rarity){
        return this.SET_COLLECTIONS.get(setName).packsLowestChanceOfPullingNewCardOfRarity(rarity);
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewCardOfRarity(ArrayList<SetName> setNames, Rarity rarity){
        return ProbabilityAnalysis.reduceToLowestChance(
            new ArrayList<>(){{
                for(SetName setName : setNames){
                    addAll(SET_COLLECTIONS.get(setName).packsRarityPullChances(rarity));
                }
            }}
        );
    }

    /**
     * Returns whether the set with the provided name has obtained the
     * provided card.
     * @param setName the name of the set to be checked.
     * @param card the card to be checked.
     * @return {@code true}, if the card has been obtained. Else, {@code
     * false}.
     */
    public boolean hasCard(SetName setName, Card card){
        return SET_COLLECTIONS.get(setName).hasCard(card);
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
        return SET_COLLECTIONS.get(pack.SET_NAME).chanceOfPullingNewCardOfRarity(pack, rarity);
    }

    /**
     * Returns as {@code ArrayList} of all cards within the set of the
     * provided name that have been obtained.
     * @param setName the name of the set to be checked.
     * @return all cards within the set of the provided name that have been
     * obtained.
     */
    public ArrayList<Card> obtainedCards(SetName setName){
        return SET_COLLECTIONS.get(setName).obtainedCards();
    }

    /**
     * Returns as {@code ArrayList} of all standard cards within the set of
     * the provided name that have been obtained.
     * @param setName the name of the set to be checked.
     * @return all standard cards within the set of the provided name that
     * have been obtained.
     */
    public ArrayList<Card> obtainedStandardCards(SetName setName){
        return SET_COLLECTIONS.get(setName).obtainedStandardCards();
    }

    /**
     * Returns as {@code ArrayList} of all rare cards within the set of the
     * provided name that have been obtained.
     * @param setName the name of the set to be checked.
     * @return all rare cards within the set of the provided name that have
     * been obtained.
     */
    public ArrayList<Card> obtainedRareCards(SetName setName){
        return SET_COLLECTIONS.get(setName).obtainedRareCards();
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
        return SET_COLLECTIONS.get(pack.SET_NAME).obtainedCardsOfRarity(pack, rarity);
    }

    /**
     * Returns as {@code ArrayList} of all cards within the set of the
     * provided name that have not yet been obtained.
     * @param setName the name of the set to be checked.
     * @return all cards within the set of the provided name that have not yet
     * been obtained.
     */
    public ArrayList<Card> unobtainedCards(SetName setName){
        return SET_COLLECTIONS.get(setName).unobtainedCards();
    }

    /**
     * Returns as {@code ArrayList} of all standard cards within the set of
     * the provided name that have not yet been obtained.
     * @param setName the name of the set to be checked.
     * @return all standard cards within the set of the provided name that
     * have not yet been obtained.
     */
    public ArrayList<Card> unobtainedStandardCards(SetName setName){
        return SET_COLLECTIONS.get(setName).unobtainedStandardCards();
    }

    /**
     * Returns as {@code ArrayList} of all rare cards within the set of the
     * provided name that have not yet been obtained.
     * @param setName the name of the set to be checked.
     * @return all rare cards within the set of the provided name that have
     * not yet been obtained.
     */
    public ArrayList<Card> unobtainedRareCards(SetName setName){
        return SET_COLLECTIONS.get(setName).unobtainedRareCards();
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
        return SET_COLLECTIONS.get(pack.SET_NAME).unobtainedCardsOfRarity(pack, rarity);
    }
}
