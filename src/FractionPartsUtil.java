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
        long[][] result = {{a.getNumerator(), a.getDenominator()}, {b.getNumerator(), b.getDenominator()}};
        assert(result[0][0]  == a.getNumerator());
        assert(result[1][1] == b.getDenominator());
        return result;
        
    }
    public static long subtract(long a, long b) {
        return a - b;
    }
}