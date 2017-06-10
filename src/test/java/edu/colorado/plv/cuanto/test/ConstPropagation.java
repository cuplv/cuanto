package edu.colorado.plv.cuanto.test;

/**
 * Created by lumber on 6/10/17.
 */
public class ConstPropagation {
    public static void main(String[] args) {
        func01();
        func02(Integer.valueOf(args[0]));
    }

    // No method invocation
    static void func01() {
        int a = (7 * 8 + 3) / 4; // const
        int b = a + 2; // const
        int c = a * b; // const
        int d = 0;
        int k = 0;
        for (int i = 0; i < 10; i++) {
            d = c + c; // const
            k = k + i; // non-const
        }
    }

    static void func02(int a) {
        int x = 0;
        int y = 0;
        int z = 0;
        if (a > 5) {
            x = 1; // non-const
            y = 2; // non-const
        } else {
            x = 2; // non-const
            y = 1; // non-const
        }
        z = x + y; // const
    }
}
