import org.junit.Test;

import java.util.Scanner;

public class Main {
    @Test
    public void main(){
        Scanner sc=new Scanner(System.in);
        Operation operation = OperationFactory.createOperation("+");
        double A=sc.nextDouble();
        operation.setNumberA(A);
        operation.setNumberB(4);
        System.out.println(operation.result());
    }
}
