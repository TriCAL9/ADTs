package fraction;

import fraction.Denominator;
import fraction.Numerator;

import java.util.Objects;

import static fraction.FractionPartsUtil.lowestCommonMultiple;

/**
* Fraction class is an ADT that holds two values a numerator and a denominator.
* Fraction can throw an IllegalArgumentException when the denominator is zero or if either the 
* numerator or denominator gets larger than an integer after performing an operation.
* The Fraction can be added to another Fraction or subtracted from another Fraction. 
* A multiply operation is multiplies Fractions together. 
*/

public class Fraction implements Comparable<Fraction> {
  public static final Fraction ONE = Fraction.valueOf(Numerator.ONE, Denominator.ONE);
  public static final Fraction ONE_HALF = Fraction.valueOf(Numerator.ONE, Denominator.TWO);
  public static final Fraction ONE_THIRD = Fraction.valueOf(Numerator.ONE, Denominator.THREE);
  public static final Fraction ONE_QUARTER = Fraction.valueOf(Numerator.ONE, Denominator.FOUR);
  private final long numerator;
  private final long denominator;
  
  /**
  * The constructor creates the Fraction object and initializes the numerator and denominator instance fields. 
  * The constructor is private and can only be instantiated through the static factory method valueOf. 
  * Arithmetic operations require that the size of the Fraction passed as an arguments to its methods have their
  * parts (numerator and denominator be the size of an int primitive) but the constructor and static factory can take a numerator
  * and denominator that are the size of a long primitive.
  * Pre-condition:  denominator != 0
  * Post-condition: denominator != 0
  * @param numerator of fraction
  * @param denominator of fraction
  * @throws UndefinedFractionException when denominator equals zero
  */
    private Fraction(long numerator, long denominator) throws UndefinedFractionException {    
      if(denominator != 0) {
      // initialize variables in the constructor
        this.numerator = numerator;
        this.denominator =  denominator;
      }
      else {
        throw new UndefinedFractionException("Fraction " + this + " is a undefined fraction");
      }
    }

  public long getNumerator() {
    return this.numerator;
  }
  
  public long getDenominator() 

    return this.denominator;
  }

  /**
  * The valueOf static factory method creates a Fraction with the values for the 
  * fraction's numerator and denominator. 
  * Pre-condition:  The calling code provides two integer as parameters (numerator, denominator), denominator !=0 
  * Post-condition: A new Fraction is returned from to the calling statement that is of the form 
  * numerator equals numerator and denominator equals denominator
  * @param numerator of the fraction
  * @param denominator of the fraction 
  * @return Fraction 
  */
    public static Fraction newInstance(int numerator, int denominator) throws UndefinedFractionException {
      return new Fraction(numerator, denominator); 
    }
  
  /**
  * Plus adds this Fraction to another fraction.
  * Pre-condition: addend != null && (-2^31 < this.numerator < 2^32  &&
  * -2^31 < this.denominator < 2^32)
  * Post-condition: (-2^<= 63 result.numerator <= 2^64) &&
  * (-2^63 <= result.denominator <= 2^64) && result.denominator != 0 and the result is the sum of this 
  * fraction and the addend
  * @param addend is the numeric operand to be added
  * @throws ArithmeticException if the result overflows a long value for either denominator or numerator
  * @return Fraction 
  */
    public Fraction plus(Fraction addend) throws ArithmeticException {
      Objects.requireNonNull(addend, "This fraction cannot be added to a null Fraction");
      long lcm = lowestCommonMultiple(this.getDenominator(), addend.getDenominator());
      System.out.println(lcm);
      Fraction result;
      if (denominator == addend.getDenominator()) {
        long sum  = FractionPartsUtil.add(this.numerator, addend.getNumerator());
        result = new Fraction(sum, denominator);
      }
      else {
        result = this.times(identity(lcm/this.getDenominator()))
          .plus(addend.times(identity(lcm/addend.getDenominator())));
      }
      return result;
    }

  /** 
  * Minus subtracts another fraction from this fraction.  
  * Pre-condition: subtrahend != null && (-2^63 <= this.numerator <= 2^64 -1 && -2^*63 <=
  * this.denominator <= 2^64) 
  * Post-condition: (-2^<= 63 result.numerator <= 2^64) && (-2^63 <= result.denominator <=
  * 2^64) && result.denominator != 0 and the result is the difference between this fraction and subtrahend
  * @param subtrahend of the minus operation
  * @throws ArithmeticException if the numerator or denominator of the difference is greater than the  
  * long value max size
  * @return Fraction the difference between this and the subtrahend
  */
    public Fraction minus(Fraction subtrahend) throws ArithmeticException {
      Objects.requireNonNull(subtrahend, "fraction must be non-null");
        
       return this.plus(subtrahend.times(new Fraction(-1,1)));
    }
    
  /**
  * Finds the identity Fraction of a number.
  * Pre-condition: The calling method provides a number, that is a long
  * Post-condition: The method returns a fraction that if multiplied by another Fraction would be 
  * simplified into 
  * the original fraction
  * @param aNumber that is used as the multiple of the identity fraction
  * @return fraction identity
  */
  private static Fraction identity(long aNumber) {
    Fraction identity = new Fraction(aNumber, aNumber);
    return identity;
  }
  
  /**
  *
  */
  public Fraction simplify() {
     long hcf = FractionPartsUtil.highestCommonFactor(numerator, denominator);
     return new Fraction(this.getNumerator()/hcf, this.getDenominator()/hcf);
  }
    
  /**
  * This Fraction multiplies itself to another fraction when this method is
  * called with a non null fraction otherwise a NullPointerException is thrown.
  * Pre-condition: fraction != null && operand != null && (-2^31 <= this.numerator <= 2^31
  * -1 *&& -2^*31 <= this.denominator <= 2^31 -1)
  * Post-condition: (-2^<= 63 result.numerator <= 2^63 -1) && (-2^63 <= result.denominator <=
  * 2^63 *-1) && result.denominator != 0
  * @param  operand to be multiplied by this fraction
  * @throws ArithmeticException if the result overflows a long value for either denominator or numerator
  * @return Fraction the product of it and its operand
  */
    public Fraction times(Fraction operand) {
      Objects.requireNonNull(operand, "operand is a should not be a null Fraction");
      Fraction product = new Fraction(FractionPartsUtil.multiply(this.numerator, operand.numerator), 
      FractionPartsUtil.multiply(this.denominator, operand.denominator));
     return product;
    }  
    
    /**
    *This Fraction is divided by a divisor by calling this method divide. 
    *pre-condition: Fraction divisor is non - null.
    *post-condition: The result is a fraction
    *@param divisor
    *@throws ArithematicException if the result is overflow the size of a
    *long 
    *@return
    */
    public Fraction divide(Fraction divisor) {
      return new Fraction(FractionPartsUtil.multipy(this.getNumerator(), divisor.getDenominator()), FractionPartsUtil.multiply(this.getDenominator(), divisor.getNumerator());
    }
  /**
  * Compares a Fraction with another Fraction checking whether they are equal Fractions
  * @param other fraction used in comparison
  * @return int 0 if true and 1 if false
  */
    @Override
    public int compareTo(Fraction other){
      long numerator = other.getNumerator();
      long denominator = other.getDenominator();
      return (this.numerator == numerator &&
              this.denominator == denominator) ? 0 : 1;
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
