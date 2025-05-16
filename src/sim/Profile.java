package sim;

import java.util.ArrayList;
import java.time.*;

/**
 * An instance of this class is used to represent a player profile.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class Profile {
    public final String USERNAME;
    private final CardCollection CARD_COLLECTION;
    private LocalDateTime premiumExpiration;
    private boolean isPremium;
    private boolean hasPreviouslyBeenPremium;
    private LocalDateTime freePackAnchor;
    private int freePacksAvailable;
    private LocalDateTime premiumPackAnchor;
    private int premiumPacksAvailable;
    private LocalDateTime wonderStaminaAnchor;
    private int wonderStamina;

    private LocalDateTime simulatedCurrentTime;

    /**
     * Instantiates an instance of {@code Profile}, with the provided username.
     * @param USERNAME the username of the profile.
     */
    public Profile(final String USERNAME){
        this.simulatedCurrentTime = LocalDateTime.now(ZoneOffset.UTC).withSecond(0).withNano(0);

        this.USERNAME = USERNAME;

        this.CARD_COLLECTION = new CardCollection();

        this.isPremium = false;
        this.hasPreviouslyBeenPremium = false;

        this.freePackAnchor = null;
        this.freePacksAvailable = 2;

        this.premiumPackAnchor = null;
        this.premiumPacksAvailable = 0;

        this.wonderStaminaAnchor = null;
        this.wonderStamina = 5;
    }

    /**
     * Returns whether the profile represents a premium user.
     * @return {@code true}, if the profile represents a premium user.
     */
    public boolean isPremium(){
        return isPremium;
    }

    /**
     * Helper method that recalcs all consumables before any important checks.
     */
    private void recalcConsumables(){
        while(this.freePacksAvailable < 2 && !this.freePackAnchor.plusHours(12).isAfter(currentTime())){
            this.freePacksAvailable++;
            this.freePackAnchor = this.freePacksAvailable == 2 ? null : this.freePackAnchor.plusHours(12);
        }
        if(isPremium){
            while(this.premiumPacksAvailable < 2 && !this.premiumPackAnchor.plusDays(1).isAfter(currentTime())){
                this.premiumPacksAvailable++;
                this.premiumPackAnchor = this.premiumPacksAvailable == 2 ? null : this.premiumPackAnchor.plusDays(1);
            }
        }
        while(this.wonderStamina < 5 && !this.wonderStaminaAnchor.plusHours(12).isAfter(currentTime())){
            this.wonderStamina++;
            this.wonderStaminaAnchor = this.wonderStamina == 5 ? null : this.wonderStaminaAnchor.plusHours(12);
        }
    }

    /**
     * Returns the total number of available packs to open.
     * @return the total number of available packs to open.
     */
    public int packsAvailable(){
        recalcConsumables();

        return freePacksAvailable + premiumPacksAvailable;
    }

    /**
     * Returns whether there are available packs to open.
     * @return {@code true}, if there are available packs to open. Else,
     * {@code false}.
     */
    public boolean hasPacksAvailable(){
        //The call to the function ensures that we also call recalcConsumables()
        return packsAvailable() != 0;
    }

    /**
     * Returns the total number of available free packs to open.
     * @return the total number of available free packs to open.
     */
    public int freePacksAvailable(){
        recalcConsumables();

        return freePacksAvailable;
    }

    /**
     * Returns whether there are available free packs to open.
     * @return {@code true}, if there are available free packs to open. Else,
     * {@code false}.
     */
    public boolean hasFreePacksAvailable(){
        //The call to the function ensures that we also call recalcConsumables()
        return freePacksAvailable() != 0;
    }

    /**
     * Returns the total number of available premium packs to open.
     * @return the total number of available premium packs to open.
     */
    public int premiumPacksAvailable(){
        recalcConsumables();

        return premiumPacksAvailable;
    }

    /**
     * Returns whether there are available premium packs to open.
     * @return {@code true}, if there are available premium packs to open.
     * Else, {@code false}.
     */
    public boolean hasPremiumPacksAvailable(){
        //The call to the function ensures that we also call recalcConsumables()
        return premiumPacksAvailable() != 0;
    }

    /**
     * Subscribes the user to the premium pass.
     */
    public void subscribeToPremium(){
        this.premiumExpiration = this.premiumExpiration != null
                ? this.premiumExpiration.plusMonths(1)
                : currentTime().withSecond(0).withNano(0).plusMonths(1);

        this.isPremium = true;

        if(!hasPreviouslyBeenPremium){
            this.premiumPacksAvailable = 2;
            this.hasPreviouslyBeenPremium = true;
        }
    }

    /**
     * Opens an available pack, if possible.
     * @param pack the pack to be opened.
     * @return {@code true}, if a pack was opened. Else, {@code false}.
     */
    public boolean openPack(Pack pack){
        if(hasFreePacksAvailable()){
            return openFreePack(pack);
        } else if(hasPremiumPacksAvailable()){
            return openPremiumPack(pack);
        }

        return false;
    }

    /**
     * Helper method to open an available free pack, if possible.
     * @param pack the pack to be opened.
     * @return {@code true}, if a free pack was opened. Else, {@code false}.
     */
    private boolean openFreePack(Pack pack){
        recalcConsumables();

        if(this.freePacksAvailable == 0){
            return false;
        }

        if(this.freePacksAvailable == 2){
            this.freePackAnchor = currentTime();
        }

        this.freePacksAvailable--;

        return CARD_COLLECTION.add(pack.open());
    }

    /**
     * Helper method to open an available premium pack, if possible.
     * @param pack the pack to be opened.
     * @return {@code true}, if a premium pack was opened. Else, {@code
     * false}.
     */
    private boolean openPremiumPack(Pack pack){
        recalcConsumables();

        if(this.premiumPacksAvailable == 0){
            return false;
        }

        if(this.premiumPacksAvailable == 2){
            this.premiumPackAnchor = currentTime();
        }

        this.premiumPacksAvailable--;

        return CARD_COLLECTION.add(pack.open());
    }

    /**
     * Returns the pack points of the set with the provided name.
     * @param setName the name of the set for which to obtain the pack points
     * of.
     * @return the pack points of the set with the provided name.
     */
    public int packPoints(SetName setName){
        return CARD_COLLECTION.packPointsFor(setName);
    }

    /**
     * Returns the wonder stamina of the instance.
     * @return the wonder stamina of the instance.
     */
    public int wonderStamina(){
        recalcConsumables();

        return wonderStamina;
    }

    /**
     * Purchases a card within the set of the provided name.
     * @param setName the name of the set from which to purchase the card.
     * @param card the card to be purchased.
     * @return {@code true}, if the card was successfully purchased. Else,
     * {@code false}.
     */
    public boolean buyCard(SetName setName, Card card){
        return CARD_COLLECTION.buyCard(setName, card);
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
        recalcConsumables();

        if(this.wonderStamina < wonderPick.STAMINA){
            return null;
        }

        if(this.wonderStamina == 5){
            this.wonderStaminaAnchor = currentTime();
        }

        this.wonderStamina -= wonderPick.STAMINA;

        return this.CARD_COLLECTION.selectWonderPick(wonderPick);
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
        return this.CARD_COLLECTION.completeObtainedPercentage(setName);
    }

    public double completeObtainedPercentage(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.completeObtainedPercentage(setNames);
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
        return this.CARD_COLLECTION.standardObtainedPercentage(setName);
    }

    public double standardObtainedPercentage(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.standardObtainedPercentage(setNames);
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
        return this.CARD_COLLECTION.rareObtainedPercentage(setName);
    }

    public double rareObtainedPercentage(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.rareObtainedPercentage(setNames);
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
        return this.CARD_COLLECTION.rarityObtainedPercentage(setName, rarity);
    }

    public double rarityObtainedPercentage(ArrayList<SetName> setNames, Rarity rarity){
        return this.CARD_COLLECTION.rarityObtainedPercentage(setNames, rarity);
    }

    /**
     * Returns whether the collection contains all cards of the set with the
     * provided name.
     * @param setName the name of the set to be checked.
     * @return {@code true}, if all cards have been obtained in the provided
     * set. Else, {@code false}.
     */
    public boolean hasAllCards(SetName setName){
        return this.CARD_COLLECTION.hasAllCards(setName);
    }

    public boolean hasAllCards(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.hasAllCards(setNames);
    }

    /**
     * Returns whether the collection contains all standard cards of the set
     * with the provided name.
     * @param setName the name of the set to be checked.
     * @return {@code true}, if all standard cards have been obtained in the
     * provided set. Else, {@code false}.
     */
    public boolean hasAllStandardCards(SetName setName){
        return this.CARD_COLLECTION.hasAllStandardCards(setName);
    }

    public boolean hasAllStandardCards(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.hasAllStandardCards(setNames);
    }

    /**
     * Returns whether the collection contains all rare cards of the set with
     * the provided name.
     * @param setName the name of the set to be checked.
     * @return {@code true}, if all rare cards have been obtained in the
     * provided set. Else, {@code false}.
     */
    public boolean hasAllRareCards(SetName setName){
        return this.CARD_COLLECTION.hasAllRareCards(setName);
    }

    public boolean hasAllRareCards(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.hasAllRareCards(setNames);
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
        return this.CARD_COLLECTION.hasAllCardsOfRarity(setName, rarity);
    }

    public boolean hasAllCardsOfRarity(ArrayList<SetName> setNames, Rarity rarity){
        return this.CARD_COLLECTION.hasAllCardsOfRarity(setNames, rarity);
    }

    /**
     * Returns whether the collection contains all cards of the pack with the
     * provided name.
     * @param pack the pack to be checked.
     * @return {@code true}, if all cards have been obtained in the provided
     * pack. Else, {@code false}.
     */
    public boolean hasAllCards(Pack pack){
        return this.CARD_COLLECTION.hasAllCards(pack);
    }

    /**
     * Returns whether the collection contains all standard cards of the
     * provided pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all standard cards have been obtained in the
     * provided pack. Else, {@code false}.
     */
    public boolean hasAllStandardCards(Pack pack){
        return this.CARD_COLLECTION.hasAllStandardCards(pack);
    }

    /**
     * Returns whether the collection contains all rare cards of the provided
     * pack.
     * @param pack the pack to be checked.
     * @return {@code true}, if all rare cards have been obtained in the
     * provided pack. Else, {@code false}.
     */
    public boolean hasAllRareCards(Pack pack){
        return this.CARD_COLLECTION.hasAllRareCards(pack);
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
        return this.CARD_COLLECTION.hasAllCardsOfRarity(pack, rarity);
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the highest
     * chance of pulling a new card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the highest chance of pulling a new card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewCard(SetName setName){
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewCard(setName);
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewCard(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewCard(setNames);
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the highest
     * chance of pulling a new standard card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the highest chance of pulling a new
     * standard card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewStandardCard(SetName setName){
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewStandardCard(setName);
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewStandardCard(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewStandardCard(setNames);
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the highest
     * chance of pulling a new rare card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the highest chance of pulling a new rare
     * card.
     */
    public ArrayList<Pack> packsHighestChanceOfPullingNewRareCard(SetName setName){
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewRareCard(setName);
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewRareCard(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewRareCard(setNames);
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
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewCardOfRarity(setName, rarity);
    }

    public ArrayList<Pack> packsHighestChanceOfPullingNewCardOfRarity(ArrayList<SetName> setNames, Rarity rarity){
        return this.CARD_COLLECTION.packsHighestChanceOfPullingNewCardOfRarity(setNames, rarity);
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the lowest chance
     * of pulling a new card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the lowest chance of pulling a new card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewCard(SetName setName){
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewCard(setName);
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewCard(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewCard(setNames);
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the lowest chance
     * of pulling a new standard card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the lowest chance of pulling a new standard
     * card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewStandardCard(SetName setName){
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewStandardCard(setName);
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewStandardCard(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewStandardCard(setNames);
    }

    /**
     * Returns an {@code ArrayList} with the packs that have the lowest chance
     * of pulling a new rare card.
     * @param setName the name of the set to be checked.
     * @return the packs that have the lowest chance of pulling a new rare
     * card.
     */
    public ArrayList<Pack> packsLowestChanceOfPullingNewRareCard(SetName setName){
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewRareCard(setName);
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewRareCard(ArrayList<SetName> setNames){
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewRareCard(setNames);
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
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewCardOfRarity(setName, rarity);
    }

    public ArrayList<Pack> packsLowestChanceOfPullingNewCardOfRarity(ArrayList<SetName> setNames, Rarity rarity){
        return this.CARD_COLLECTION.packsLowestChanceOfPullingNewCardOfRarity(setNames, rarity);
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
        return CARD_COLLECTION.hasCard(setName, card);
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
        return CARD_COLLECTION.chanceOfPullingNewCardOfRarity(pack, rarity);
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
        return CARD_COLLECTION.unobtainedCardsOfRarity(pack, rarity);
    }

    /**
     * Returns as {@code ArrayList} of all standard cards within the set of
     * the provided name that have not yet been obtained.
     * @param setName the name of the set to be checked.
     * @return all standard cards within the set of the provided name that
     * have not yet been obtained.
     */
    public ArrayList<Card> unobtainedStandardCards(SetName setName){
        return CARD_COLLECTION.unobtainedStandardCards(setName);
    }

    /**
     * Simulates time having passed for the instance by the provided number
     * of hours.
     * @param hours the simulated hours that have passed.
     */
    public void simulateHourJump(long hours){
        this.simulatedCurrentTime = this.simulatedCurrentTime.plusHours(hours);
    }

    /**
     * Returns the simulated current time of the instance.
     * @return the simulated current time of the instance.
     */
    private LocalDateTime currentTime(){
        return simulatedCurrentTime;
    }

    /**
     * Returns the expiration of the premium pass.
     * @return the expiration of the premium pass.
     */
    public LocalDateTime premiumExpiration(){
        return this.premiumExpiration;
    }
}
