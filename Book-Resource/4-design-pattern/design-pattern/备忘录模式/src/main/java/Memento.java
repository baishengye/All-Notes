import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Memento {//备忘录类
    private String State;

    public Memento(String state){
        this.State=state;
    }
}
