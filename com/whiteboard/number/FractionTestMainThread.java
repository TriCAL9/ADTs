package com.whiteboard.number;

import com.whiteboard.number.fraction.Fraction;
  
public class FractionTestMainThread {
  public static void main(String[] args) {
    Fraction approximationOfPI= Fraction.newInstance(22, 7);
    Fraction radius = Fraction.newInstance(3, 1);
    Fraction areaInsideCircle = radius.times(radius).times(approximationOfPI);
    System.out.println(areaInsideCircle);
    System.out.println("Approximated area: " + areaInsideCircle.getNumerator() 
                       / areaInsideCircle.getDenominator());
  }
}