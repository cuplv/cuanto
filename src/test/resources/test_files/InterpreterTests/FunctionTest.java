class FunctionTest{
    public static void main(String[] args){
        test1();
        test2();
    }
    public static int test1(){
        return inner();

    }
    public static int inner(){
        return 3;
    }
    public static int test2(){
        return fact(3);
    }
    public static int fact(int in){
        if(in == 0)
            return 1;
        else
            return fact(in-1);
    }
}