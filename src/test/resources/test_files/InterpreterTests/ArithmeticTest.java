class ArithmeticTest{
	public static void main(String[] args){
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
		test8();
		test9();
		test10();
		test11();
		test12();
	}
	public static int test1(){
		return 3;
	}
	public static int test2() {
		int x = 4;
		return x;
	}
	public static int test3() {
		int x = 4;
		x = x+1;
		return x;
	}
	public static int test4() {
		int x = 6;
		x += 1;
		x -= 2;
		return x;
	}
	public static int test5(){
		int x = 2;
		x = x*2;
		return x;
	}
	public static int test6(){
		int x = 4;
		x++;
		return x;
	}
	public static int test7(){
		int x = 4;
		int y = x++;
		return y;
	}
	public static int test8(){
		int x = 4;
		int y = ++x;
		return y;
	}
	public static int test9(){
		int x = 4;
		int y = x--;
		return y;
	}
	public static int test10(){
		int x = 4;
		int y = --x;
		return y;
	}
	public static int test11(){
		int x = 4;
		x = x/2;
		return x;
	}
	public static int test12(){
		int x = 5;
		x = x/2;
		return x;
	}
}
