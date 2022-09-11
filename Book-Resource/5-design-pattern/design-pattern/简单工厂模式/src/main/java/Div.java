public class Div extends Operation{
    @Override
    public double result() {
        try{
            if(getNumberB()==0){
                throw new Exception("被除数不能为0");
            }
            else{
                return getNumberA()/getNumberB();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
