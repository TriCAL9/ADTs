package com.whiteboard.number;

import com.whiteboard.number.fraction.Fraction;
  
public class FractionTestMainThread {
  public static void main(String[] args) {
    Fraction approximationOfPI= Fraction.newInstance(22, 7);
    Fraction radius = Fraction.newInstance(3, 1);
    Fraction areaInsideCircle = radius.times(radius).times(approximationOfPI);
    int a = 1;
    int b = 2; 
    int c = 3;
    Fraction quadraticSolutionForX = Fraction.newInstance(-b, 1)
    .plus(Fraction.newInstance((int) (StrictMath.pow(b,2) - 4*a*c), 2*a));
    Fraction negativQuadraticSolutionForX = Fraction.newInstance(-b, 1)
    .minus(Fraction.newInstance((int) (StrictMath.pow(b, 2) - 4*a*c), 2*a));
    System.out.println(areaInsideCircle);
    System.out.println("Approximated area: " + areaInsideCircle.getNumerator() 
    / areaInsideCircle.getDenominator());
    System.out.println("ax^2+x+3 = 0 x1 = " + quadraticSolutionForX + " x2 = "
     + negativQuadraticSolutionForX);
    System.out.println(Fraction.ONE_HALF.plus(Fraction.ONE_QUARTER).toString());
    System.out.println(Fraction.ONE_HALF.minus(Fraction.ONE_THIRD));
    
  }
}