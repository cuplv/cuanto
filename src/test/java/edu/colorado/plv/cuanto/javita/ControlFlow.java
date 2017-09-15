package edu.colorado.plv.cuanto.javita;

/**
 * @author Sergio Mover
 *
 * Test the different control flow supported in Javita subset.
 *
 * By convention, the execution of all the public static method must
 * return true.
 */
public class ControlFlow {
  private static int classField1 = 0;
  private static boolean classField2 = true;

  public static boolean sequence1() {
    int a = 2;
    int b = 3;
    int c = 0;

    c = a + b;
    a += b;

    return c == a;
  }

  public static boolean if1() {
    int a = 2;
    int b = 2;
    boolean result = false;

    if (a == b) {
      result = true;
    }

    return result;
  }

  public static boolean if2() {
    int a = 2;
    int b = 2;
    boolean result = false;

    if (a != b) {
      result = false;
    }
    else {
      if (a != b) {
        result = false;
      } else {
        result = true;
      }
    }

    return result;
  }
  
  public static boolean switch1() {
    // Test the table switch
    int var = 2;
    boolean result = false;

    switch (var) {
    case 0: break;
    case 1: break;
    case 2:
      result = true;
      break;
    default:
      result = false;
      break;
    }

    return result;
  }

  public static boolean switch2() {
    // Test the lookup switch
    int var = 100;
    boolean result = false;

    switch (var) {
    case 1: break;
    case 10: break;
    case 100:
      result = true;
      break;
    case 1000: break;
    default:
      result = false;
      break;
    }
    return result;
  }

  public static boolean switch3() {
    // Test break
    int var = 2;
    boolean result = false;

    switch (var) {
    case 0: break;
    case 1: break;
    case 2:
    case 3:
      result = true;
      break;
    default:
      result = false;
      break;
    }
    return result;
  }

  public static boolean switch4() {
    // Test default
    int var = 65;
    boolean result = false;

    switch (var) {
    case 0: break;
    case 1: break;
    case 2: break;
    default:
      result = true;
      break;
    }
    return result;
  }

  public static boolean testWhile() {
    int var = 0;
    while (var < 100) {
      var += 1;
    }

    return var == 100;
  }

  public static boolean testDoWhile() {
    int var = 0;

    do {
      var += 1;
    } while (var < 100);

    return var == 100;
  }

  public static boolean testFor() {
    int var = 200;

    for (var = 0; var < 100; var++) {
    }

    return var == 100;
  }

  public static boolean testReadClassField() {
    return classField1 == 0;
  }

  public static boolean testWriteClassField() {
    classField1 = 25;
    return classField1 == 25;
  }

  public static int sum(int a, int b) {
    return a + b;
  }

  public static boolean testMethodCall() {
    int a = 3;
    int b = 2;
    int result;

    result = ControlFlow.sum(a, b);

    return result == 5;
  }
}
