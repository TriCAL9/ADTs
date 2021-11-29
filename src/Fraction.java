import java.util.Objects;

public class Fraction implements Comparable<Fraction> {
    private final long numerator;
    private final long denominator;

    /**
     * The constructor creates the Fraction object and initializes the num and denom
     * instance fields.
     * The constructor is private and can only be instantiated through the static
     * factory method valueOf. Arithmetic operations require that the size of the
     * Fraction's parts (num and denom be the size of an int primitive) but the
     * constructor and static factory can take a num and denom that are the size of a
     * primitive long.
     * Precondition: -2^<= 63 num <= 2^63 -1 && -2^63 <= denom <= 2^63 -1 && denom != 0
     * Postcondition: -2^<= 63 this.num <= 2^62 -1 && -2^63 <= this.denom <= 2^63 -1 &&
     * this.denom != 0
     *
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     * @throws IllegalArgumentException if the denominator is zero
     */
    private Fraction(long numerator, long denominator) {
        // check precondition
        if (denominator != 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        } else {
            throw new IllegalArgumentException("Fraction undefined");
        }
    }

    /**
     * The valueOf static factory method creates a Fraction with the values for the
     * fraction's numerator and denominator.
     * precondition:  -2^31 <= num <= 2^31 -1 && -2^31 <= denom <= 2^31 -1 && denom != 0
     * postcondition:
     *
     * @param num   the numerator of the fraction
     * @param denom denominator of the fraction
     * @return Fraction
     */
    public static Fraction valueOf(int num, int denom) {
        if (denom != 0) {
            return new Fraction(num, denom);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public long getNumerator() {
        return this.numerator;
    }

    public long getDenominator() {
        return this.denominator;
    }

    /**
     * This Fraction adds to itself to another fraction.
     * precondition: addend != null && (-2^31 < this.num < 2^31 -1 &&
     * -2^31 < this.denom < 2^31-1)
     * postcondition: (-2^<= 63 result.num <= 2^63 -1) &&
     * (-2^63 <= result.denom <= 2^63 -1) && result.denom != 0
     *
     * @param addend addend is the numeric operand
     * @return Fraction
     */
    public Fraction plus(Fraction addend) {
        //check precondition
        Objects.requireNonNull(addend, "null Fraction can't be be added; precondition failed");
        long[][] aPartOfFractions = FractionPartsUtil.seperateIntoParts(this, addend);
        int numerator = FractionPart.NUMERATOR.ordinal();
        int denominator = FractionPart.DENOMINATOR.ordinal();
        int sizeOfNumerator = Long.bitCount(aPartOfFractions[0][numerator]);
        int sizeOfDenominator = Long.bitCount(aPartOfFractions[0][denominator]);

        System.out.printf("numberator's size %d and denominator's size %d  before addition \n", sizeOfNumerator, sizeOfDenominator);

        if ((sizeOfNumerator < Integer.SIZE) && (sizeOfDenominator < Integer.SIZE)) {
            long sumOfNumerators = FractionPartsUtil.add(aPartOfFractions[0][numerator],
                    aPartOfFractions[1][numerator]);
            assert (Long.SIZE > Long.bitCount(sumOfNumerators));
            System.out.println(aPartOfFractions[1][denominator]);
            long lcm = FractionPartsUtil.lowestCommonMultiple(aPartOfFractions[0]
                    [numerator], aPartOfFractions[1][numerator]) /
                    FractionPartsUtil.highestCommonFactor(aPartOfFractions[0]
                            [denominator], aPartOfFractions[1][denominator]);

            //create a new function that has as the num sumOfNums and as denom lcm
            return new Fraction(sumOfNumerators, lcm);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Minus subtracts another fraction from this fraction.
     * precondtion: subtranend != null && (-2^31 <= this.num <= 2^31 -1 && -2^*31 <=
     * this.denom <= 2^31-1)
     * postcondition: (-2^<= 63 result.num <= 2^63 -1) && (-2^63 <= result.denom <=
     * 2^63 -1) && result.denom != 0
     *
     * @param subtranend the operand of the minus operation
     * @return Fraction the difference between this and the subtranend
     * @throws IllegalArgumentException for an argument that is bigger than an integer's
     *                                  max size or smaller than an integer's min size.
     * @throws NullPointerException     for the operand of value type null
     */
    public Fraction minus(Fraction subtranend) {
        Objects.requireNonNull(subtranend, "null Fraction can't be be added; precondition failed");
        long[][] aPartOfFractions = FractionPartsUtil.seperateIntoParts(this,
                subtranend);
        int numerator = FractionPart.NUMERATOR.ordinal();
        int denominator = FractionPart.DENOMINATOR.ordinal();
        int sizeOfNumerator = Long.bitCount(aPartOfFractions[0]
                [numerator]);
        int sizeOfDenominator = Long.bitCount(aPartOfFractions[0]
                [denominator]);

        System.out.printf("numberator's size %d and denominator's size %d  before subtraction \n", sizeOfNumerator,
                sizeOfDenominator);
        if ((sizeOfNumerator < Integer.SIZE) && (sizeOfDenominator < Integer.SIZE)) {
            long differenceOfNumerators = FractionPartsUtil.subtract(aPartOfFractions[0][numerator], aPartOfFractions[1][numerator]);
            assert (Long.SIZE > Long.bitCount(differenceOfNumerators));
            long lcm = FractionPartsUtil.lowestCommonMultiple(aPartOfFractions[0]
                    [numerator], aPartOfFractions[1][numerator]) /
                    FractionPartsUtil.highestCommonFactor(aPartOfFractions[0][denominator],
                            aPartOfFractions[1][denominator]);
            return new Fraction(differenceOfNumerators, lcm);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This Fraction multiplies itself to another fraction when this method is
     * called with a non null fraction otherwise a NullPointerException is thrown.
     * precondition: fraction != null && operand != null && (-2^31 <= this.num <= 2^31
     * -1 *&& -2^*31 <= this.denom <= 2^31 -1)
     * postcondition: (-2^<= 63 result.num <= 2^63 -1) && (-2^63 <= result.denom <=
     * 2^63 *-1) && result.denom != 0
     *
     * @param operand operand to be multiplied by this fraction
     * @return Fraction the product of it and its operand
     */
    public Fraction times(Fraction operand) {
        Objects.requireNonNull(operand, "operand is a should not be a null Fraction");
        Fraction product = new Fraction(FractionPartsUtil.multiply(this.numerator, operand.numerator),
                FractionPartsUtil.multiply(this.denominator, operand.denominator));
        //check postcondition
        assert product.getDenominator() != 0;
        assert product.getDenominator() > Long.bitCount(this.getDenominator()) && product.getNumerator() >
                Long.bitCount(this.getNumerator());
        return product;
    }

    public int compareTo(Fraction f) {
        long numerator = f.getNumerator();
        long denominator = f.getDenominator();
        return this.numerator == numerator &&
                this.denominator == denominator ? 0 : 1;
    }

    /**
     * This is a String representation of a Fraction with no concrete specified
     * format.
     * The current String representation of Fraction reflects this pattern, as of
     * current version:
     * Class name : Fraction, long num = [aNumerator], long denom = [aDenominator].
     */
    @Override
    public String toString() {
        return "Class name : " + this.getClass().getName() + ", long num = [" +
                getNumerator() + "], long denom = [" + getDenominator() + "].";
    }

    public enum FractionPart {NUMERATOR, DENOMINATOR}
}