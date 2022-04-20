package com.whiteboard.number.fraction;

public class FractionPartsUtil {

    private FractionPartsUtil(){
        throw new AssertionError();
    }
    
    public static long findLowestCommonMultiple(long a, long b) {
        return Math.abs(a * b) / findHighestCommonFactor(a, b);
    } 

    public static long findHighestCommonFactor(long a, long b) {
        while(b !=0) {
            long r = b;
            b = mod(a, b);
            a = r;
        }    
        return a;
    }

    public static long add(long a, long b) {
        return StrictMath.addExact(a, b);
    }    
    
    public static long mod(long a, long b) {
        return a % b;
    }
    public static long multiply(long a, long b) {
        return StrictMath.multiplyExact(a, b);
    }
    public static long[][] separateIntoParts(Fraction a, Fraction b) {
        long[][] result = {{a.getNumerator(), a.getDenominator()}, {b.getNumerator(), b.getDenominator()}};
        assert(result[0][0]  == a.getNumerator());
        assert(result[1][1] == b.getDenominator());
        return result;
        
    }
    public static long subtract(long a, long b) {
        return StrictMath.subtractExact(a, b);
    }
}