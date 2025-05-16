import sim.*;

import java.util.*;

/**
 * The entry-point for the program.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public class Main {
    /**
     * The main method to be used by the program. Allows for minor control by
     * the user for the execution of the simulations.
     * @param args the args used to start the program.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String setNamesPrompt = "Which sets would you like to simulate? (Enter each expansion number separated by spaces.):";
        SetName[] setNames = SetName.values();
        for(int i = 0; i < setNames.length; i++){
            setNamesPrompt += "\n- " + (i + 1) + ") " + setNames[i].VAL;
        }
        setNamesPrompt += "\nEnter: ";

        String simCountPrompt = "How many simulations would you like to perform?\nEnter: ";
        String simPackPointsPrompt = "Use pack points?\nEnter: ";
        String simWonderPicksPrompt = "Use Wonder Picks?\nEnter: ";

        System.out.print(setNamesPrompt);
        SimInputPacket simInputPacket = parseSetNums(sc.nextLine().split(" "));

        while(simInputPacket.setNames.isEmpty()){
            System.out.print("\nERROR: " + simInputPacket.errMsg + "\n" + setNamesPrompt);
            simInputPacket = parseSetNums(sc.nextLine().split(" "));
        }

        System.out.print("\n" + simCountPrompt);
        int totalSims = sc.nextInt();
        sc.nextLine();
        while(totalSims < 1){
            System.out.print("\nInvalid number provided. Enter a positive value.\n" + simCountPrompt);
            totalSims = sc.nextInt();
            sc.nextLine();
        }

        System.out.print("\n" + simPackPointsPrompt);
        String packPointsInput = sc.nextLine().trim().toLowerCase();
        boolean usePackPoints = packPointsInput.equals("y") || packPointsInput.equals("yes");
        boolean hasPackPointsError = !usePackPoints && !(packPointsInput.equals("n") || packPointsInput.equals("no"));
        while(hasPackPointsError){
            System.out.print("\nInvalid input provided. Enter \"y\" or \"n\".\n" + simPackPointsPrompt);
            packPointsInput = sc.nextLine().trim().toLowerCase();
            usePackPoints = packPointsInput.equals("y") || packPointsInput.equals("yes");
            hasPackPointsError = !usePackPoints && !(packPointsInput.equals("n") || packPointsInput.equals("no"));
        }

        System.out.print("\n" + simWonderPicksPrompt);
        String wonderPicksInput = sc.nextLine().trim().toLowerCase();
        boolean useWonderPicks = wonderPicksInput.equals("y") || wonderPicksInput.equals("yes");
        boolean hasWonderPicksError = !useWonderPicks && !(wonderPicksInput.equals("n") || wonderPicksInput.equals("no"));
        while(hasWonderPicksError){
            System.out.print("\nInvalid input provided. Enter \"y\" or \"n\".\n" + simWonderPicksPrompt);
            wonderPicksInput = sc.nextLine().trim().toLowerCase();
            useWonderPicks = wonderPicksInput.equals("y") || wonderPicksInput.equals("yes");
            hasWonderPicksError = !useWonderPicks && !(wonderPicksInput.equals("n") || wonderPicksInput.equals("no"));
        }

        simSet(simInputPacket.setNames, totalSims, usePackPoints, useWonderPicks);
    }

    private static SimInputPacket parseSetNums(String[] input){
        ArrayList<SetName> chosenSets = new ArrayList<>();
        String errMsg = "";

        if(input.length == 0){
            errMsg = "Input did not contain any set numbers.";
        } else {
            SetName[] setNames = SetName.values();

            for(String setNumStr : input){
                try {
                    int setNum = Integer.parseInt(setNumStr) - 1;
                    if(setNum > -1 && setNum < setNames.length){
                        if(!chosenSets.contains(setNames[setNum])) {
                            chosenSets.add(setNames[setNum]);
                        } else {
                            chosenSets.clear();
                            errMsg = "Repeated set number.";
                            break;
                        }
                    } else {
                        chosenSets.clear();
                        errMsg = "Invalid set number.";
                        break;
                    }
                } catch (NumberFormatException e) {
                    chosenSets.clear();
                    errMsg = "Invalid input of non-integer.";
                    break;
                }
            }
        }

        return new SimInputPacket(chosenSets, errMsg);
    }

    /**
     * Runs a number of simulations for the provided sets equal to the
     * requested simulation count.
     * @param setNames the names of the sets to be simulated.
     * @param totalSims the total number of simulations to be run.
     * @param usePackPoints whether or not to use pack points.
     * @param useWonderPicks whether or not to use Wonder Picks.
     */
    private static void simSet(ArrayList<SetName> setNames, int totalSims, boolean usePackPoints, boolean useWonderPicks){
        final Random RAND = new Random();

        final SimulationPacket[] attempts = new SimulationPacket[totalSims];

        Profile profile;
        int forD, forDD, forDDD, forDDDD, for100P, for10P, for20P, for30P, for40P, for50P, for60P, for70P, for80P, for90P;
        ArrayList<Pack> packs;

        int progress = 0;
        int compProgress;
        System.out.println("0.0%");
        for(int i = 0; i < attempts.length; i++){
            profile = new Profile("player");
            forD = forDD = forDDD = forDDDD = for100P = for10P = for20P = for30P = for40P = for50P = for60P = for70P = for80P = for90P = 0;

            while(!profile.hasAllStandardCards(setNames) && for100P != Integer.MAX_VALUE){
                if(for100P != 0){
                    profile.simulateHourJump(12);
                }

                while(profile.hasPacksAvailable() && for100P != Integer.MAX_VALUE){
                    for100P++;
                    if(!profile.hasAllCardsOfRarity(setNames, Rarity.D)){
                        forD++;
                    }
                    if(!profile.hasAllCardsOfRarity(setNames, Rarity.DD)){
                        forDD++;
                    }
                    if(!profile.hasAllCardsOfRarity(setNames, Rarity.DDD)){
                        forDDD++;
                    }
                    if(!profile.hasAllCardsOfRarity(setNames, Rarity.DDDD)){
                        forDDDD++;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 10){
                        for10P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 20){
                        for20P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 30){
                        for30P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 40){
                        for40P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 50){
                        for50P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 60){
                        for60P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 70){
                        for70P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 80){
                        for80P = for100P;
                    }
                    if(profile.standardObtainedPercentage(setNames) < 90){
                        for90P = for100P;
                    }

                    packs = profile.packsHighestChanceOfPullingNewStandardCard(setNames);

                    profile.openPack(packs.get(RAND.nextInt(packs.size())));
                }

                if(useWonderPicks){
                    WonderPick wonderPick;
                    ArrayList<WonderPick> wonderPicks = new ArrayList<>();
                    ArrayList<Double> newDDDDProbFactors = new ArrayList<>();
                    ArrayList<Double> newDDDProbFactors = new ArrayList<>();
                    ArrayList<Double> newDDProbFactors = new ArrayList<>();
                    ArrayList<Double> newDProbFactors = new ArrayList<>();
                    ArrayList<Double> newCardProbFactors = new ArrayList<>();
                    int newD, newDD, newDDD, newDDDD;
                    for(int j = 0; j < 12; j++){
                        SetName wonderPickSetName = setNames.get(RAND.nextInt(setNames.size()));
                        wonderPick = new WonderPick(CardSets.get(wonderPickSetName).packs().get(RAND.nextInt(CardSets.get(wonderPickSetName).packCount())));
                        wonderPicks.add(wonderPick);
                        newD = newDD = newDDD = newDDDD = 0;
                        for(Card card : wonderPick.cards()){
                            if(!profile.hasCard(wonderPickSetName, card)){
                                if(card.RARITY.equals(Rarity.D)){
                                    newD++;
                                } else if(card.RARITY.equals(Rarity.DD)){
                                    newDD++;
                                } else if(card.RARITY.equals(Rarity.DDD)){
                                    newDDD++;
                                } else if(card.RARITY.equals(Rarity.DDDD)){
                                    newDDDD++;
                                }
                            }
                        }
                        newDDDDProbFactors.add(newDDDD * .20 / (1 - Math.pow(1 - profile.chanceOfPullingNewCardOfRarity(wonderPick.PACK, Rarity.DDDD), wonderPick.STAMINA)));
                        newDDDProbFactors.add(newDDD * .20 / (1 - Math.pow(1 - profile.chanceOfPullingNewCardOfRarity(wonderPick.PACK, Rarity.DDD), wonderPick.STAMINA)));
                        newDDProbFactors.add(newDD * .20 / (1 - Math.pow(1 - profile.chanceOfPullingNewCardOfRarity(wonderPick.PACK, Rarity.DD), wonderPick.STAMINA)));
                        newDProbFactors.add(newD * .20 / (1 - Math.pow(1 - profile.chanceOfPullingNewCardOfRarity(wonderPick.PACK, Rarity.D), wonderPick.STAMINA)));
                        newCardProbFactors.add(
                                (newDDDDProbFactors.get(j) * newDDDD
                                        + newDDDProbFactors.get(j) * newDDD
                                        + newDDProbFactors.get(j) * newDD
                                        + newDProbFactors.get(j) * newD
                                ) / 5);
                    }

                    WonderPick wonderPickSelection;
                    if(profile.wonderStamina() >= 5){
                        int indexOfLargest = 0;
                        for(int j = 1; j < wonderPicks.size(); j++){
                            if(newCardProbFactors.get(j) > newCardProbFactors.get(indexOfLargest)){
                                indexOfLargest = j;
                            }
                        }
                        wonderPickSelection = wonderPicks.get(indexOfLargest);
                    } else if(!profile.hasAllCardsOfRarity(setNames, Rarity.DDDD)){
                        if(profile.wonderStamina() >= Rarity.DDDD.WONDER_STAMINA_COST){
                            int indexOfLargest = -1;
                            for(int j = 0; j < wonderPicks.size(); j++){
                                boolean hasDDDD = false;
                                for(Card card : wonderPicks.get(j).cards()){
                                    if(card.RARITY.equals(Rarity.DDDD)){
                                        hasDDDD = true;
                                        break;
                                    }
                                }
                                if(hasDDDD && (indexOfLargest == -1 || newDDDDProbFactors.get(j) > newDDDDProbFactors.get(indexOfLargest))){
                                    indexOfLargest = j;
                                }
                            }
                            wonderPickSelection = indexOfLargest > -1 ? wonderPicks.get(indexOfLargest) : null;
                        } else {
                            wonderPickSelection = null;
                        }
                    } else if(!profile.hasAllCardsOfRarity(setNames, Rarity.DDD)){
                        if(profile.wonderStamina() >= Rarity.DDD.WONDER_STAMINA_COST){
                            int indexOfLargest = -1;
                            for(int j = 0; j < wonderPicks.size(); j++){
                                boolean hasDDD = false;
                                for(Card card : wonderPicks.get(j).cards()){
                                    if(card.RARITY.equals(Rarity.DDD)){
                                        hasDDD = true;
                                        break;
                                    }
                                }
                                if(hasDDD && (indexOfLargest == -1 || newDDDProbFactors.get(j) > newDDDProbFactors.get(indexOfLargest))){
                                    indexOfLargest = j;
                                }
                            }
                            wonderPickSelection = indexOfLargest > -1 ? wonderPicks.get(indexOfLargest) : null;
                        } else {
                            wonderPickSelection = null;
                        }
                    } else if(!profile.hasAllCardsOfRarity(setNames, Rarity.DD)){
                        if(profile.wonderStamina() >= Rarity.DD.WONDER_STAMINA_COST){
                            int indexOfLargest = -1;
                            for(int j = 0; j < wonderPicks.size(); j++){
                                boolean hasDD = false;
                                for(Card card : wonderPicks.get(j).cards()){
                                    if(card.RARITY.equals(Rarity.DD)){
                                        hasDD = true;
                                        break;
                                    }
                                }
                                if(hasDD && (indexOfLargest == -1 || newDDProbFactors.get(j) > newDDProbFactors.get(indexOfLargest))){
                                    indexOfLargest = j;
                                }
                            }
                            wonderPickSelection = indexOfLargest > -1 ? wonderPicks.get(indexOfLargest) : null;
                        } else {
                            wonderPickSelection = null;
                        }
                    } else if(!profile.hasAllCardsOfRarity(setNames, Rarity.D)){
                        if(profile.wonderStamina() >= Rarity.D.WONDER_STAMINA_COST){
                            int indexOfLargest = -1;
                            for(int j = 0; j < wonderPicks.size(); j++){
                                boolean hasD = false;
                                for(Card card : wonderPicks.get(j).cards()){
                                    if(card.RARITY.equals(Rarity.D)){
                                        hasD = true;
                                        break;
                                    }
                                }
                                if(hasD && (indexOfLargest == -1 || newDProbFactors.get(j) > newDProbFactors.get(indexOfLargest))){
                                    indexOfLargest = j;
                                }
                            }
                            wonderPickSelection = indexOfLargest > -1 ? wonderPicks.get(indexOfLargest) : null;
                        } else {
                            wonderPickSelection = null;
                        }
                    } else {
                        wonderPickSelection = null;
                    }

                    if(wonderPickSelection != null){
                        profile.selectWonderPick(wonderPickSelection);
                    }
                }

                if(usePackPoints){
                    for(SetName setName : setNames){
                        if(profile.packPoints(setName) == 2500){
                            // Find the pack with the lowest probability of drawing a new card
                            ArrayList<Pack> possibilities = profile.packsLowestChanceOfPullingNewStandardCard(setName);
                            Pack pack = possibilities.get(RAND.nextInt(possibilities.size()));

                            ArrayList<Pair<Rarity, Double>> rarityChances = new ArrayList<>();
                            rarityChances.add(new Pair<>(Rarity.DDDD, profile.chanceOfPullingNewCardOfRarity(pack, Rarity.DDDD)));
                            rarityChances.add(new Pair<>(Rarity.DDD, profile.chanceOfPullingNewCardOfRarity(pack, Rarity.DDD)));
                            rarityChances.add(new Pair<>(Rarity.DD, profile.chanceOfPullingNewCardOfRarity(pack, Rarity.DD)));
                            rarityChances.add(new Pair<>(Rarity.D, profile.chanceOfPullingNewCardOfRarity(pack, Rarity.D)));

                            rarityChances.sort(Comparator.comparingDouble(a -> a.VAL));

                            ArrayList<Card> cards = profile.unobtainedCardsOfRarity(pack, rarityChances.get(0).KEY);

                            for(int j = 0; j < cards.size();){
                                if(cards.get(j).isSpecialUnlock()){
                                    cards.remove(j);
                                } else {
                                    j++;
                                }
                            }

                            Card card = cards.get(RAND.nextInt(cards.size()));
                            profile.buyCard(setName, card);
                        } else {
                            ArrayList<Card> unobtainedStandardCards = profile.unobtainedStandardCards(setName);

                            int neededPackPoints = 0;
                            for(Card card : unobtainedStandardCards){
                                if(card.packPoints() != -1){
                                    neededPackPoints += card.packPoints();
                                }
                            }

                            if(neededPackPoints <= profile.packPoints(setName)){
                                for(Card card : unobtainedStandardCards){
                                    profile.buyCard(setName, card);
                                }
                            }
                        }
                    }
                }
            }

            attempts[i] = new SimulationPacket(for10P, for20P, for30P, for40P, for50P, for60P, for70P, for80P, for90P, for100P, forD, forDD, forDDD, forDDDD);

            compProgress = (i + 1) * 1000 / totalSims;
            if(compProgress > progress){
                progress = compProgress;
                System.out.println(progress / 10 + "." + progress % 10 + "%");
            }
        }

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 10% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_10_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 20% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_20_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 30% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_30_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 40% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_40_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 50% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_50_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 60% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_60_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 70% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_70_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 80% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_80_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 90% of Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_90_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS));
        System.out.println("\nAttempts for 100% Standard Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_100_PERCENT_OF_STANDARDS);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_ALL_D_RARITIES));
        System.out.println("\nAttempts for D Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_ALL_D_RARITIES + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_ALL_D_RARITIES + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_ALL_D_RARITIES);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_ALL_DD_RARITIES));
        System.out.println("\nAttempts for DD Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_ALL_DD_RARITIES + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_ALL_DD_RARITIES + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_ALL_DD_RARITIES);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_ALL_DDD_RARITIES));
        System.out.println("\nAttempts for DDD Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_ALL_DDD_RARITIES + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_ALL_DDD_RARITIES + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_ALL_DDD_RARITIES);

        Arrays.sort(attempts, Comparator.comparingInt(a -> a.ATTEMPTS_FOR_ALL_DDDD_RARITIES));
        System.out.println("\nAttempts for DDDD Rarities:\n5th Percentile: " + attempts[attempts.length/20-1].ATTEMPTS_FOR_ALL_DDDD_RARITIES + "\n50th Percentile: " + attempts[attempts.length/2-1].ATTEMPTS_FOR_ALL_DDDD_RARITIES + "\n95th Percentile: " + attempts[attempts.length/20*19-1].ATTEMPTS_FOR_ALL_DDDD_RARITIES);
    }

    private static final class SimInputPacket {
        public ArrayList<SetName> setNames;
        public String errMsg;

        public SimInputPacket(ArrayList<SetName> setNames, String errMsg){
            this.setNames = setNames;
            this.errMsg = errMsg;
        }
    }
}