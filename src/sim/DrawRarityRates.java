package sim;

import java.util.HashMap;
import java.util.Random;

/**
 * An instance of this class is used to represent the draw rate of the
 * all possible {@code Rarity} for a single draw within a single pack of
 * cards.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class DrawRarityRates {
    private final HashMap<Rarity, Fraction> RATES = new HashMap<>();
    private final long TOTAL_WEIGHT;
    private final HashMap<Rarity, Long> CUTOFFS = new HashMap<>();

    /**
     * Instantiates an instance of {@code DrawRarityRates}, with the provided
     * draw rates for each rarity.
     * @param D draw rate for a {@code Rarity} of {@code D}.
     * @param DD draw rate for a {@code Rarity} of {@code DD}.
     * @param DDD draw rate for a {@code Rarity} of {@code DDD}.
     * @param DDDD draw rate for a {@code Rarity} of {@code DDDD}.
     * @param S draw rate for a {@code Rarity} of {@code S}.
     * @param SS draw rate for a {@code Rarity} of {@code SS}.
     * @param SSS draw rate for a {@code Rarity} of {@code SSS}.
     * @param R draw rate for a {@code Rarity} of {@code R}.
     * @param RR draw rate for a {@code Rarity} of {@code RR}.
     * @param C draw rate for a {@code Rarity} of {@code C}.
     */
    public DrawRarityRates(final Fraction D, final Fraction DD,
                           final Fraction DDD, final Fraction DDDD,
                           final Fraction S, final Fraction SS,
                           final Fraction SSS, final Fraction R,
                           final Fraction RR, final Fraction C){
        this.RATES.put(Rarity.D, D);
        this.RATES.put(Rarity.DD, DD);
        this.RATES.put(Rarity.DDD, DDD);
        this.RATES.put(Rarity.DDDD, DDDD);
        this.RATES.put(Rarity.S, S);
        this.RATES.put(Rarity.SS, SS);
        this.RATES.put(Rarity.SSS, SSS);
        this.RATES.put(Rarity.R, R);
        this.RATES.put(Rarity.RR, RR);
        this.RATES.put(Rarity.C, C);

        this.TOTAL_WEIGHT = Fraction.lcm(
            RATES.get(Rarity.D),
            RATES.get(Rarity.DD),
            RATES.get(Rarity.DDD),
            RATES.get(Rarity.DDDD),
            RATES.get(Rarity.S),
            RATES.get(Rarity.SS),
            RATES.get(Rarity.SSS),
            RATES.get(Rarity.R),
            RATES.get(Rarity.RR),
            RATES.get(Rarity.C)
        );

        this.CUTOFFS.put(Rarity.D, D.NUM * (this.TOTAL_WEIGHT / D.DEN));
        this.CUTOFFS.put(Rarity.DD, this.CUTOFFS.get(Rarity.D) + (DD.NUM * (this.TOTAL_WEIGHT / DD.DEN)));
        this.CUTOFFS.put(Rarity.DDD, this.CUTOFFS.get(Rarity.DD) + (DDD.NUM * (this.TOTAL_WEIGHT / DDD.DEN)));
        this.CUTOFFS.put(Rarity.DDDD, this.CUTOFFS.get(Rarity.DDD) + (DDDD.NUM * (this.TOTAL_WEIGHT / DDDD.DEN)));
        this.CUTOFFS.put(Rarity.S, this.CUTOFFS.get(Rarity.DDDD) + (S.NUM * (this.TOTAL_WEIGHT / S.DEN)));
        this.CUTOFFS.put(Rarity.SS, this.CUTOFFS.get(Rarity.S) + (SS.NUM * (this.TOTAL_WEIGHT / SS.DEN)));
        this.CUTOFFS.put(Rarity.SSS, this.CUTOFFS.get(Rarity.SS) + (SSS.NUM * (this.TOTAL_WEIGHT / SSS.DEN)));
        this.CUTOFFS.put(Rarity.R, this.CUTOFFS.get(Rarity.SSS) + (R.NUM * (this.TOTAL_WEIGHT / R.DEN)));
        this.CUTOFFS.put(Rarity.RR, this.CUTOFFS.get(Rarity.R) + (RR.NUM * (this.TOTAL_WEIGHT / RR.DEN)));
        this.CUTOFFS.put(Rarity.C, this.CUTOFFS.get(Rarity.RR) + (C.NUM * (this.TOTAL_WEIGHT / C.DEN)));
    }

    /**
     * Returns the draw rate for the provided rarity.
     * @param rarity the rarity for which to obtain the draw rate of.
     * @return the draw rate of the provided rarity.
     */
    public Fraction ofRarity(Rarity rarity){
        return RATES.get(rarity);
    }

    /**
     * Returns a random {@code Rarity} based on the weights of the draw rates
     * of the instance.
     * @return a random {@code Rarity}.
     */
    public Rarity draw(){
        final long ROLL = new Random().nextLong(TOTAL_WEIGHT);

        final Rarity[] RARITIES = Rarity.values();
        for(int i = 0; i < RARITIES.length; i++){
            if(ROLL < CUTOFFS.get(RARITIES[i]) && this.RATES.get(RARITIES[i]).NUM > 0){
                return RARITIES[i];
            }
        }

        return Rarity.C;
    }
}
