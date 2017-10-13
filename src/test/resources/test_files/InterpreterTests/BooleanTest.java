class BooleanTest{
    public static void main(String[] args){
        test1();
        test2();
        test3();
    }

    private static boolean test1() {
        return true;
    }
    private static boolean test2(){
        //TODO: I didn't need to implement "AndExpr" for this, is there a way we can get an And in jimple?
        //The and is compiled away into conditional goto stmts
//        private static boolean test2()
//        {
//            boolean z0, z1, $z2;
//
//            z0 = 1;
//
//            z1 = 1;
//
//            if z0 == 0 goto label1;
//
//            if z1 == 0 goto label1;
//
//            $z2 = 1;
//
//        goto label2;
//
//            label1:
//            $z2 = 0;
//
//            label2:
//            return $z2;
//        }

        boolean foo = true;
        boolean bar = true;
        return foo && bar;
    }
    private static boolean test3(){
//        private static boolean test3()
//        {
//            boolean z0, $z1;
//
//            z0 = 1;
//
//            if z0 != 0 goto label1;
//
//            $z1 = 1;
//
//        goto label2;
//
//            label1:
//            $z1 = 0;
//
//            label2:
//            return $z1;
//        }

        boolean foo = true;
        return !foo;
    }

}