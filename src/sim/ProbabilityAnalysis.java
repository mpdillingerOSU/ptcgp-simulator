package sim;

import java.util.ArrayList;
import java.util.Comparator;

public final class ProbabilityAnalysis {
    private ProbabilityAnalysis() {}

    /**
     * Helper method that returns a list of the packs with the highest chance.
     * @param packChancePairs the pack-chance pairs to check.
     * @return a list of the packs with the highest chance.
     */
    public static ArrayList<Pack> reduceToHighestChance(ArrayList<Pair<Pack, Double>> packChancePairs){
        packChancePairs.sort((a, b) -> Double.compare(b.VAL, a.VAL));

        final ArrayList<Pack> RESULT = new ArrayList<>();
        RESULT.add(packChancePairs.get(0).KEY);
        for(int i = 1; i < packChancePairs.size(); i++){
            if(!packChancePairs.get(i).VAL.equals(packChancePairs.get(0).VAL)){
                break;
            }
            RESULT.add(packChancePairs.get(i).KEY);
        }

        return RESULT;
    }

    /**
     * Helper method that returns a list of the packs with the lowest chance.
     * @param packChancePairs the pack-chance pairs to check.
     * @return a list of the packs with the lowest chance.
     */
    public static ArrayList<Pack> reduceToLowestChance(ArrayList<Pair<Pack, Double>> packChancePairs){
        packChancePairs.sort(Comparator.comparingDouble(a -> a.VAL));

        final ArrayList<Pack> RESULT = new ArrayList<>();
        RESULT.add(packChancePairs.get(0).KEY);
        for(int i = 1; i < packChancePairs.size(); i++){
            if(!packChancePairs.get(i).VAL.equals(packChancePairs.get(0).VAL)){
                break;
            }
            RESULT.add(packChancePairs.get(i).KEY);
        }

        return RESULT;
    }
}
