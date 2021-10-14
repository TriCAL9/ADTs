public class Fraction implements Comparable<Fraction> {
  public enum FractionPart {NUMERATOR, DENOMINATOR}
  private long numerator
  private long denominator
  
  /**
  *The constructor creates the Fraction object and initializes the num and denom
  *instance fields. 
  *The constructor is private and can only be instantiated through the static
  *factory method valueOf. Arithmetic operations require that the size of the
  *Fraction's parts (num and denom be the size of an int primitive) but the 
  *constructor and static factory can take a num and denom that are the size of a
  *primitive long.
  *Precondition: -2^<= 63 num <= 2^63 -1 && -2^63 <= denom <= 2^63 -1 && denom != 0 
  *Postcondition: -2^<= 63 this.num <= 2^62 -1 && -2^63 <= this.denom <= 2^63 -1 &&
  *this.denom != 0
  *@param long numerator of fraction
  *@param long denominator of fraction
  *@throws IllegalArgumentException
  */
    private Fraction(long numerator, long denominator) {
      // check precondition
      if(denominator != 0) {
      // initialize variables in the constructor
        this.numerator = numerator;
        this.denominator = denominator;
      }
      else {
        throw new IllegalArgumentException("Fraction undefined");
      }
      // check postcondition
      assert(this.denominator != 0);
    }

  public int getNumerator() {
    return this.numerator;
  }
  
  public int getDenominator() {
    return this.denominator;
  }

  /**The valueOf static factory method creates a Fraction with the values for the 
  *fraction's numerator and denominator. 
  *precondition:  -2^31 <= num <= 2^31 -1 && -2^31 <= denom <= 2^31 -1 && denom !=
  *0 
  *postcondition: 
  *@param int the numerator of the fraction
  *@param int denominator of the fraction 
  *@return Fraction 
  */
    public static Fraction valueOf(int num, int denom) {
      if(denom != 0) {
        return new Fraction(num, denom);
      }
      else {
         throw new IllegalArgumentException();
      }
    }
  
  /**This Fraction adds to itself to another fraction.
  *precondition: addend != null && (-2^31 < this.num < 2^31 -1 &&
  * -2^31 < this.denom < 2^31-1)
  *postcondition: (-2^<= 63 result.num <= 2^63 -1) &&
  *(-2^63 <= result.denom <= 2^63 -1) && result.denom != 0
  *@param Fraction addend is the numeric operand 
  *@return Fraction 
  */
    public Fraction plus(Fraction addend) {
      //check precondition
      Objects.requireNonNull(addend, "null Fraction can't be be added;
      precondition failed");
      int[][] aPartOfFractions =
        FractionPartsUtil.seperateIntoParts(this,addend);
      int numerator = FractionPart.NUMERATOR.ordinal();
      int denominator = FractionPart.DENOMINATOR.ordinal();
      int sizeOfNumerator = Long.valueOf(aPartOfFractions[0]
        [numerator.value()]).bitCount();
      int sizeOfDenominator = Long.valueOf(aPartOfFractions[0]
        [denominator.value()]).bitCount();
      
      System.out.printf("numberator's size %d and denominator's size %d  before
        addition \n" ,  sizeOfNumerator, sizeOfDenominator);
      
      if((sizeOfNumerator < Integer.SIZE) && (sizeOfDenominator < Integer.SIZE)) {

        long sumONumerators = add(aPartOfFractions[0][numerator],
          aPartOfFractions[1][numerator]);
      }
      else {    
      
        throw new IllegalArgumentException(); 
        
      }
      
      //assign to sumOfNums add(myNum, otherNum)
      assert(Long.SIZE > Long.valueOf(sumOfNums).bitCount());
      //assign to lcm the return value from   FractionPair method lcm(this.num,
      //otherNum)/gcd(this.denom, otherDenom)
      long lcm = FractionPartsUtil.lowestCommonMultiple(aPartOfFractions[0]
        [numerator], aPartOfFractions[1][numerator]) / 
          FractionPartsUtil.highestCommonDivisor(aPartOfFractions[0]
            [denominator], aPartOfFractions[1][denominator]);
      
    
      Fraction result = new Fraction( sumOfNumerators, lcm );
      
      
      //create a new function that has as the num sumOfNums and as denom lcm 
      //and return this new fraction
      return result;
    }


  /** Minus subtracts another fraction from this fraction.  
  *precondtion: subtranend != null && (-2^31 <= this.num <= 2^31 -1 && -2^*31 <=
  *this.denom <= 2^31-1) 
  *postcondition: (-2^<= 63 result.num <= 2^63 -1) && (-2^63 <= result.denom <=
  *2^63 -1) && result.denom != 0
  *@param Fraction the operand of the minus operation
  *@throws IllegalArgumentException for an argument that is bigger than an integer's
  *max size or smaller than an integer's min size. 
  *@throws NullPointerException for the operand of value type null
  *@return Fraction the difference between this and the subtranend
  */
    public Fraction minus(Fraction subtranend) {
      Objects.requireNonNull(subtranend, "null Fraction can't be be added;
      precondition failed");
      int[][] aPartOfFractions = FractionPartsUtil.seperateIntoParts(this,
        subtranend);
      int numerator = FractionPart.NUMERATOR.ordinal();
      int denominator = FractionPart.DENOMINATOR.ordinal();
      int sizeOfNumerator = Long.valueOf(aPartOfFractions[0]
        [numerator]).bitCount();
      int sizeOfDenominator = Long.valueOf(aPartOfFractions[0]
      [denominator]).bitCount();
      
      System.out.printf("numberator's size %d and denominator's size %d  before
      subtraction \n" ,  sizeOfNumerator, sizeOfDenominator);
      if((sizeOfNumerator < Integer.SIZE) && (sizeOfDenominator < Integer.SIZE))
      {

        long differenceONumerators = subtract(aPartOfFractions[0][numerator],
        aPartOfFractions[1][numerator]);
        
      }
      else {    
      
        throw new IllegalArgumentException(); 
        
      }
      
      //assign to sumOfNums add(myNum, otherNum)
      assert(Long.SIZE > Long.valueOf(sumOfNums).bitCount());
      
      //assign to lcm the return value from   FractionPair method lcm(this.num,
      //otherNum)/gcd(this.denom, otherDenom)
      long lcm = FractionPartsUtil.lowestCommonMultiple(aPartOfFractions[0
        [numerator], aPartOfFractions[1][numerator]) /
        FractionPartsUtil.highestCommonDivisor(aPartOfFractions[0][denominator],
            aPartOfFractions[1][denominator]);
      //assign to difference the result from subtract(this.num, otherNum)
      Fraction result = new Fraction( differenceOfNumerators, lcm );
      //create a new fraction with the difference between numerators and the lcm
      //of the denominator
      return result;
    }
    
    
  /**This Fraction multiplies itself to another fraction when this method is
  *called with a non null fraction otherwise a NullPointerException is thrown.
  *precondition: fraction != null && operand != null && (-2^31 <= this.num <= 2^31
  *-1 *&& -2^*31 <= this.denom <= 2^31 -1)
  *postcondition: (-2^<= 63 result.num <= 2^63 -1) && (-2^63 <= result.denom <=
  *2^63 *-1) && result.denom != 0
  *@param Fraction operand to be multiplied by this fraction
  *@returns Fraction the product of it and its operand
  */
    public Fraction times(Fraction operand) {
      Objects.requireNonNull(operand, "operand is a should not be a null Fraction")
      Fraction product = new Fraction(FractionPartsUtil.multiply(this.num,
      fraction.num), FractionPartsUtil.multiply(this.denom, fraction.denom));
     //check postcondition
     assert(product.getDenom() != 0);
     //assert that product.denom > Long.asLong(this.denom()).bitCount() && product.num > Long.asLong(this.num).bitCount()
     assert (product.getDenominator() > Long.asLong(this.getDenominator()).bitCount() && product.getNumerator() >
     Long.asLong(this.getnNumerator()).bitCount());
     return product;
    }  

    public int compareTo(Fraction f){
      long numerator = f.getNum();
      long denominator = f.getDenom();
      return (Long.compare(this.num, numerator) == 0 &&
      Long.compare(this.denom, denominator) == 0) ? 0 : 1;
    }
    
  /**
  * This is a String representation of a Fraction with no concrete specified
  *format.
  * The current String representation of Fraction reflects this pattern, as of
  *current version:
  * Class name : Fraction, long num = [aNumerator], long denom = [aDenominator].
  */
    @Override
    public String toString() {
      return "Class name : " + this.getClass().getName() + ", long num = [" +
      getNum() + "], long denom = [" + getDenom() + "].";
    }
}
