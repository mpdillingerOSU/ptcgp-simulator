package sim;

/**
 * An instance of this class is used to represent a fraction.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class Fraction {
    public final long NUM;
    public final long DEN;
    public final double VAL;

    public static final Fraction ZERO = new Fraction(0,1);
    public static final Fraction ONE = new Fraction(1, 1);

    /**
     * Instantiates an instance of {@code Fraction}, with the provided
     * numerator and denominator.
     * @param NUMERATOR the numerator of the fraction.
     * @param DEN the denominator of the fraction.
     */
    public Fraction(final long NUMERATOR, final long DEN){
        this.NUM = NUMERATOR;
        this.DEN = DEN;
        VAL = (double)NUMERATOR/ DEN;
    }

    /**
     * Returns whether the instance is less than the value of the provided
     * fraction.
     * @param comp the value against which the instance is to be compared.
     * @return {@code true}, if the instance is less than the comparison.
     * Else, {@code false}.
     */
    public boolean isLessThan(Fraction comp){
        long lcm = lcm(DEN, comp.DEN);
        return (NUM * (lcm / DEN)) < (comp.NUM * (lcm / comp.DEN));
    }

    /**
     * Returns whether the instance is greater than the value of the provided
     * fraction.
     * @param comp the value against which the instance is to be compared.
     * @return {@code true}, if the instance is greater than the comparison.
     * Else, {@code false}.
     */
    public boolean isGreaterThan(Fraction comp){
        long lcm = lcm(DEN, comp.DEN);
        return (NUM * (lcm / DEN)) > (comp.NUM * (lcm / comp.DEN));
    }

    /**
     * Returns whether the instance is equal to the value of the provided
     * fraction.
     * @param comp the value against which the instance is to be compared.
     * @return {@code true}, if the instance is equal to the comparison. Else,
     * {@code false}.
     */
    public boolean isEqualTo(Fraction comp){
        long lcm = lcm(DEN, comp.DEN);
        return (NUM * (lcm / DEN)) == (comp.NUM * (lcm / comp.DEN));
    }

    /**
     * Returns whether the instance is less than or equal to the value of the
     * provided fraction.
     * @param comp the value against which the instance is to be compared.
     * @return {@code true}, if the instance is less than or equal to the
     * comparison. Else, {@code false}.
     */
    public boolean isLessThanOrEqualTo(Fraction comp){
        long lcm = lcm(DEN, comp.DEN);
        return (NUM * (lcm / DEN)) <= (comp.NUM * (lcm / comp.DEN));
    }

    /**
     * Returns whether the instance is greater than or equal to the value of
     * the provided fraction.
     * @param comp the value against which the instance is to be compared.
     * @return {@code true}, if the instance is greater than or equal to the
     * comparison. Else, {@code false}.
     */
    public boolean isGreaterThanOrEqualTo(Fraction comp){
        long lcm = lcm(DEN, comp.DEN);
        return (NUM * (lcm / DEN)) <= (comp.NUM * (lcm / comp.DEN));
    }

    /**
     * Returns whether the instance is not equal to the value of the provided
     * fraction.
     * @param comp the value against which the instance is to be compared.
     * @return {@code true}, if the instance is not equal to the comparison.
     * Else, {@code false}.
     */
    public boolean isNotEqual(Fraction comp){
        long lcm = lcm(DEN, comp.DEN);
        return (NUM * (lcm / DEN)) != (comp.NUM * (lcm / comp.DEN));
    }

    /**
     * Returns a fraction representing the sum of all of the provided values.
     * @param fractions all of the values to be added together.
     * @return a fraction representing the sum of all of the provided values.
     */
    public static Fraction add(Fraction... fractions){
        long lcm = lcm(fractions);
        long num = 0;
        for(Fraction fraction : fractions){
            num += fraction.NUM * (lcm / fraction.DEN);
        }
        return reduce(new Fraction(num, lcm));
    }

    /**
     * Returns a fraction representing the product of all of the provided
     * values.
     * @param fractions all of the values to be multiplied together.
     * @return a fraction representing the product of all of the provided
     * values.
     */
    public static Fraction multiply(Fraction... fractions){
        Fraction product = fractions[0];
        for(int i = 1; i < fractions.length; i++){
            product = reduce(new Fraction(product.NUM * fractions[i].NUM, product.DEN * fractions[i].DEN));
        }
        return product;
    }

    /**
     * Returns a fraction representing the difference of the subtrahend being
     * subtracted from the minuend.
     * @param minuend the minuend.
     * @param subtrahend the subtrahend.
     * @return a fraction representing the difference of the subtrahend being
     * subtracted from the minuend.
     */
    public static Fraction subtract(Fraction minuend, Fraction subtrahend){
        long lcm = lcm(minuend.DEN, subtrahend.DEN);
        return reduce(new Fraction((minuend.NUM * (lcm / minuend.DEN)) - (subtrahend.NUM * (lcm / subtrahend.DEN)), lcm));
    }

    /**
     * Returns a fraction representing the provided fraction being taken to
     * the provided power.
     * @param base the base.
     * @param power the power.
     * @return a fraction representing the provided fraction being taken to
     * the provided power.
     */
    public static Fraction pow(Fraction base, long power){
        if(power > -1){
            return Fraction.reduce(new Fraction((long) Math.pow(base.NUM, power), (long) Math.pow(base.DEN, power)));
        }
        return Fraction.reduce(new Fraction((long) Math.pow(base.DEN, -power), (long) Math.pow(base.NUM, -power)));
    }

    /**
     * Returns the least common multiple for the denominators of the provided
     * values.
     * @param fractions the values from which to find the lcm.
     * @return the least common multiple for the denominators of the provided
     * values.
     */
    public static long lcm(Fraction... fractions){
        long result = 1;
        for(Fraction fraction : fractions){
            result = lcm(result, fraction.DEN);
        }
        return result;
    }

    /**
     * Helper function that returns the least common multiple for two values.
     * @param num1 the first value to be compared.
     * @param num2 the second value to be compared.
     * @return the least common multiple for two values.
     */
    private static long lcm(long num1, long num2){
        return Math.abs(num1 * num2) / gcd(num1, num2);
    }

    /**
     * Helper function that returns the greatest common divisor for two
     * values.
     * @param num1 the first value to be compared.
     * @param num2 the second value to be compared.
     * @return the greatest common divisor for two values.
     */
    private static long gcd(long num1, long num2){
        if(num1 == 0){
            return num2;
        } else if(num2 == 0){
            return num1;
        } else if(num1 == num2){
            return num1;
        }

        long cut = num1 > num2 ? num2/2 : num1/2;
        for(long i = cut; i > 1 ; i--){
            if(num1 % i == 0 && num2 % i == 0){
                return i;
            }
        }

        return 1;
    }

    /**
     * Returns the value of a fraction having been reduced.
     * @param fraction the fraction to be reduced.
     * @return the value of a fraction having been reduced.
     */
    public static Fraction reduce(Fraction fraction){
        return reduce(fraction.NUM, fraction.DEN);
    }

    /**
     * Helper function that returns the value of a fraction having been
     * reduced.
     * @param num the numerator of the fraction.
     * @param den the denominator of the fraction.
     * @return the value of a fraction having been reduced.
     */
    private static Fraction reduce(long num, long den){
        if(den == 0){
            num = 0;
        } else if(num == 0){
            den = 1;
        } else if(num % den == 0){
            num /= den;
            den = 1;
        } else if(den % num == 0){
            den /= num;
            num = 1;
        } else {
            long gcd = gcd(num, den);
            num /= gcd;
            den /= gcd;
        }
        return new Fraction(num,den);
    }

    /**
     * Returns the value of the instance having been cast to a truncated long.
     * @return the value of the instance having been cast to a truncated long.
     */
    public long toLong(){
        return NUM / DEN;
    }

    /**
     * Returns the string value representing the instance.
     * @return the string value representing the instance.
     */
    @Override
    public String toString(){
        return NUM + "/" + DEN;
    }
}