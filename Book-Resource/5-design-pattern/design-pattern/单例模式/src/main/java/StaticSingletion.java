public class StaticSingletion {
    private StaticSingletion(){

        System.out.println("只生成一次");
    }

    private static class StaticSingletionInstance{
        private static final StaticSingletion intsance=new StaticSingletion();
    }

    public static StaticSingletion getInstance(){
        return StaticSingletionInstance.intsance;
    }
}
