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
    private Fraction(long numerator, long denominator) throws UndefinedFractionException {    
      if(denominator != 0) {
      // initialize variables in the constructor
        this.numerator = numerator;
        this.denominator =  denominator;
      }
      else {
        throw new UndefinedFractionException("Fraction " + this + " is a undefined fraction");
      }
      // check postcondition
      assert(this.denominator != 0);
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
  * precondition:  The calling code provides two integer as parameters (num, denom), denom !=0 
  * postcondition: A new Fraction is returned from to the calling statement that is of the form 
  * numerator equals num and denominator equals denom
  * @param int the numerator of the fraction
  * @param int denominator of the fraction 
  * @return Fraction 
  */
    public static Fraction valueOf(int num, int denom) throws UndefinedFractionException {
      Fraction fraction = new Fraction(num, denom);
      return fraction; 
    }
  
  /**
  * Plus adds this Fraction to another fraction.
  * precondition: addend != null && (-2^31 < this.num < 2^32  &&
  * -2^31 < this.denom < 2^32)
  * postcondition: (-2^<= 63 result.num <= 2^64) &&
  * (-2^63 <= result.denom <= 2^64) && result.denom != 0 and the result is the sum of this 
  * fraction and the addend
  * @param Fraction addend is the numeric operand to be added
  * @throws ArithmeticException if the result overflows a long value for either denominator or numerator
  * @return Fraction 
  */
    public Fraction plus(Fraction addend) throws ArithmeticException{
      Objects.requireNonNull(addend, "This fraction cannot be added to a null Fraction");
      long lcm = lowestCommonMultiple(this.getDenominator(), addend.getDenominator());
      System.out.println(lcm);
      Fraction result;
      if (denominator == addend.getDenominator()) {
        long sum  = FractionPartsUtil.add(numerator, addend.getNumerator());
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
  * precondtion: subtranend != null && (-2^63 <= this.numerator <= 2^64 -1 && -2^*63 <=
  * this.denominator <= 2^64) 
  * postcondition: (-2^<= 63 result.numerator <= 2^64) && (-2^63 <= result.denominator <=
  * 2^64) && result.denom != 0 and the result is the difference between this fraction and subtranend
  * @param Fraction the operand of the minus operation
  * @throws ArithmeticException if the numerator or denominator of the difference is greater than the  
  * long value max size
  * @return Fraction the difference between this and the subtranend
  */
    public Fraction minus(Fraction subtranend) throws ArithmeticException, NullPointerException {
      Objects.requireNonNull(subtranend, "fraction must be non-null");
        
       return this.plus(subtranend.times(new Fraction(-1,1)));
    }
    
  /**
  * Finds the identity Fraction of a number.
  * precondition: The calling method provides a number, that is a long
  * postcondition: The method returns a fraction that if multiplied by another Fraction would be 
  * simplified into 
  * the original fraction
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
  public Fraction simplify() {
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
