package factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private double numberA;
    private double numberB;
    private boolean flag=false;

    public double result(){
        return 0;
    }
}
