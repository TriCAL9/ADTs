package fraction;

import java.util.Objects;
import static fraction.FractionPartsUtil.lowestCommonMultiple;

/**
* Fraction class is an ADT that holds two values a numerator and a denominator.
* Fraction can throw a an IllegalArgumentException when the denominator is zero or if either the 
* numerator or denominator gets larger than an integer after performing an operation.
* The Fraction can be added to another Fraction or subtracted from another Fraction. 
* A multiply operation is multiplies Fractions together. 
*/

public class Fraction implements Comparable<Fraction> {
  public enum FractionPart {NUMERATOR, DENOMINATOR}
  public static final Fraction ONE = Fraction.valueOf(1,1);
  public static final Fraction ONE_HALF = Fraction.valueOf(1,2);
  public static final Fraction ONE_QUARTER = Fraction.valueOf(1,4);
  public static final Fraction ONE_THIRD = Fraction.valueOf(1,3);
  private long numerator;
  private long denominator;
  
  /**
  * The constructor creates the Fraction object and initializes the num and denom instance fields. 
  * The constructor is private and can only be instantiated through the static factory method valueOf. 
  * Arithmetic operations require that the size of the Fraction passed as an arguments to its methods have thier
  * parts (num and denom be the size of an int primitive) but the constructor and static factory can take a num
  * and denom that are the size of a long primitive.
  * Precondition:  denominator != 0
  * Postcondition: denominator != 0
  * @param long numerator of fraction
  * @param long denominator of fraction
  * @throws IllegalArgumentException
  */
    private Fraction(long numerator, long denominator) {    

      if(denominator != 0) {
      // initialize variables in the constructor
        this.numerator = numerator;
        this.denominator =  denominator;
      }
      else {
        throw new IllegalArgumentException("Fraction undefined");
      }
    }

  public long getNumerator() {
    return this.numerator;
  }
  
  public long getDenominator() {
    return this.denominator;
  }

  /**
  * The valueOf static factory method creates a Fraction with the values for the 
  * fraction's numerator and denominator. 
  * precondition:  -2^31 <= num <= 2^31 -1 && -2^31 <= denom <= 2^31 -1 && denom !=0 
  * postcondition: 
  * @param int the numerator of the fraction
  * @param int denominator of the fraction 
  * @return Fraction 
  */
    public static Fraction valueOf(int num, int denom) {
      if(denom != 0) {
        return new Fraction(num, denom);
      }
      else {
         throw new IllegalArgumentException();
      }
    }
  
  /**
  * Plus adds this Fraction to another fraction.
  * precondition: addend != null && (-2^31 < this.num < 2^32 &&
  * -2^31 < this.denom < 2^32)
  * postcondition: (-2^<= 63 result.num <= 2^63 -1) &&
  * (-2^63 <= result.denom <= 2^63 -1) && result.denom != 0
  * @param Fraction addend is the numeric operand to be added
  * @return Fraction 
  */
    public Fraction plus(Fraction addend) {
      long[][] fracPair = FractionPartsUtil.seperateIntoParts(this, addend);
      
      int sizeOfNumerator = Long.bitCount(fracPair[0][0]);
      int sizeOfDenominator = Long.bitCount(fracPair[0][1]);
      
      
      if((sizeOfNumerator > Integer.SIZE) && (sizeOfDenominator > Integer.SIZE))
        throw new IllegalArgumentException(); 
      long lcm = lowestCommonMultiple(this.getDenominator(), addend.getDenominator());
      return new Fraction(fracPair[0][0]*(lcm/fracPair[0][1]) + fracPair[1][0]*(lcm/fracPair[1][1]) ,lcm).simplify();
    }


  /** 
  * Minus subtracts another fraction from this fraction.  
  * precondtion: subtranend != null && (-2^63 <= this.num <= 2^64 -1 && -2^*63 <=
  * this.denom <= 2^64) 
  * postcondition: (-2^<= 63 result.num <= 2^63 -1) && (-2^63 <= result.denom <=
  * 2^64) && result.denom != 0
  * @param Fraction the operand of the minus operation
  * @throws IllegalArgumentException for an argument that is bigger than an integer's
  * max size or smaller than an integer's min size. 
  * @throws NullPointerExceptionfor the operand of value type null
  * @return Fraction the difference between this and the subtranend
  */
    public Fraction minus(Fraction subtranend) {
      Objects.requireNonNull(subtranend, "fraction must be non-null")
      int sizeOfNumerator = Long.bitCount(this.getNumerator());
      int sizeOfDenominator = Long.bitCount(this.getDenominator());
      
      if((sizeOfNumerator > Integer.SIZE) && (sizeOfDenominator > Integer.SIZE))
        throw new IllegalArgumentException(); 
        
        return this.plus(subtranend.times(new Fraction(-1,1))).simplify();
    }
    
    /**
    * Finds the identity Fraction of a number.
    * precondition:
    * postcondition:
    * @param long aNumber
    * @return Fraction identity
    */
    private static Fraction identity(long aNumber) {
      Fraction identity = new Fraction(aNumber, aNumber);
      return identity;
    }
    
    /**
    *
    */
    private Fraction simplify() {
       long hcf = FractionPartsUtil.highestCommonFactor(numerator, denominator);
       return new Fraction(this.getNumerator()/hcf, this.getDenominator()/hcf);
    }
    
  /**
  * This Fraction multiplies itself to another fraction when this method is
  * called with a non null fraction otherwise a NullPointerException is thrown.
  * precondition: fraction != null && operand != null && (-2^31 <= this.num <= 2^31
  * -1 *&& -2^*31 <= this.denom <= 2^31 -1)
  * postcondition: (-2^<= 63 result.num <= 2^63 -1) && (-2^63 <= result.denom <=
  * 2^63 *-1) && result.denom != 0
  * @param Fraction operand to be multiplied by this fraction
  * @return Fraction the product of it and its operand
  */
    public Fraction times(Fraction operand) {
      Objects.requireNonNull(operand, "operand is a should not be a null Fraction");
      Fraction product = new Fraction(FractionPartsUtil.multiply(this.numerator, operand.numerator), 
      FractionPartsUtil.multiply(this.denominator, operand.denominator));
     return product;
    }  
    
  /**
  * Compares a Fraction with another Fraction checking whether they are equal Fractions
  * @param Fraction other
  * @return int 0 if true and 1 if false
  */
    @Override
    public int compareTo(Fraction other){
      long numerator = other.getNumerator();
      long denominator = other.getDenominator();
      return (Long.compare(this.numerator, numerator) == 0 &&
      Long.compare(this.denominator, denominator) == 0) ? 0 : 1;
    }
    
  /**
  * The toString string representation of a Fraction with no concrete specified format.
  * The current String representation of Fraction reflects this pattern, as of current version:
  * Class name : Fraction, long numerator = [aNumerator], long denominator = [aDenominator].
  */
    @Override
    public String toString() {
      return "Class name : " + this.getClass().getName() + ", long numerator = [" +
      getNumerator() + "], long denominator = [" + getDenominator() + "].";
    }
}
