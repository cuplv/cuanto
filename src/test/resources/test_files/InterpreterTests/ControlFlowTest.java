class ControlFlowTest{
    public static void main(String[] args){
        test1();
    }
    public static int test1(){
        int foo = 3;
        if(foo < 4){
            return foo+5;
        }else{
            return 0;
        }
    }
}