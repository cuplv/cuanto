public class Test1 {
	public static void main(String[] args) {
		int a = test01();
		int b = test02();
	}

	static int test01() {
		int a = 10;
		int b = 1;
		boolean c = true;
		for (int i = 0; i < a && c; i++) {
			b = b * (a - 2) / 2 ;
		}
		return b;
	}

	static int test02() {
		int a = fibonacci(7) + fibonacci(8);
		return fibonacci(10);
	}

	static int fibonacci(int i) {
		if (i == 0)
			return 0;
		if (i == 1)
			return 1;
		return fibonacci(i - 1) + fibonacci(i - 2);
	}
}
