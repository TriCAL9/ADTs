package fraction;

import fraction.Fraction;

public class FractionPartsUtil {

    private FractionPartsUtil(){
        throw new AssertionError();
    }
    
    public static long lowestCommonMultiple(long a, long b) {
        return (long) Math.abs(a * b) / highestCommonFactor(a, b);
    } 

    public static long highestCommonFactor(long a, long b) {
        while(b !=0) {
            long r = b;
            b = mod(a, b);
            a = r;
        }    
        return a;
    }

    public static long add(long a, long b) {
        return a + b;
    }    
    
    public static long mod(long a, long b) {
        return a % b;
    }
    public static long multiply(long a, long b) {
        return a * b;
    }
    public static long[][] seperateIntoParts(Fraction a, Fraction b) {
        return {{a.getNumerator(), a.getDenominator()}, {b.getNumerator(), b.getDenominator()}};
        
    }
    public static long subtract(long a, long b) {
        return a - b;
    }
}