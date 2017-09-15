package edu.colorado.plv.cuanto.javita;

/**
 * @author Sergio Mover
 *
 * 
 */
public class Expressions {

  // TODO: do we want the bitwise operators for integers?
  public static boolean testBool1() {return true && true;}
  public static boolean testBool2() {return true && false;}
  public static boolean testBool3() {return false && true;}
  public static boolean testBool4() {return false && false;}

  public static boolean testBool5() {return true || true;}
  public static boolean testBool6() {return true || false;}
  public static boolean testBool7() {return false || true;}
  public static boolean testBool8() {return false || false;}

  public static boolean testBool9() {return ! true;}
  public static boolean testBool10() {return ! false;}

  public static int testExpr1() {return 1 + 2;} // 3
  public static int testExpr2() {return 1 + 2 + 10;} // 13
  public static int testExpr3() {return 2 - 1;} // 1
  public static int testExpr4() {return 2 * 3;} // 6
  public static int testExpr5() {return 10 / 2;} // 5
  public static int testExpr6() {return 10 % 4;} // 2
  public static int testExpr7() {return 3 * 1 + 2;} // 5
  public static int testExpr8() {return 3 * (1 + 2);} // 9
  public static int testExpr9() {return 6 / 3 + 2;} // 4
  public static int testExpr10() {return 6 / (3 + 2);} // 1
  public static int testExpr11() {return 3 * 2 + 2 / 3;} // 6
  public static int testExpr12() {return 4 * 3 / 4;} // 3
  public static int testExpr13() {return (3 / 4);} // 0

  /**
   * Test comparison operators: <=, >=, <, >, !=, == 
   */ 
  public static boolean testComparisons1() {return 0 <= 0;}  // true
  public static boolean testComparisons2() {return 0 <= 1;}  // true
  public static boolean testComparisons3() {return 2 <= 0;}  // faslse
  public static boolean testComparisons4() {return 0 >= 0;}  // true
  public static boolean testComparisons5() {return 1 >= 0;}  // true
  public static boolean testComparisons6() {return 0 >= 2;}  // faslse
  public static boolean testComparisons7() {return 0 < 0;}   // false
  public static boolean testComparisons8() {return 0 < 1;}   // true
  public static boolean testComparisons9() {return 2 < 0;}   // faslse
  public static boolean testComparisons10() {return 0 > 0;}  // false
  public static boolean testComparisons11() {return 1 > 0;}  // true
  public static boolean testComparisons12() {return 0 > 2;}  // faslse
  public static boolean testComparisons13() {return 0 == 0;} // true
  public static boolean testComparisons14() {return 0 == 1;} // false
  public static boolean testComparisons15() {return 0 != 0;} // false
  public static boolean testComparisons16() {return 0 != 1;} // true

}
