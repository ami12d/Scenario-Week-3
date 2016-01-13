package wirecalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class ParallelNodeStart extends Component {

    private List<Wire> nexts;

    public ParallelNodeStart(int id) {
        super(0.0, id);
        nexts = new ArrayList<>();
    }

    @Override
    public Component getNextComponent() {
        //get the component after the end of the parallel circuit
        Component component = nexts.get(0).getNext();
        while(component.getClass() != ParallelNodeEnd.class) {
            component = component.getNextComponent();
        }
        return component.getNextComponent();
    }

    @Override
    public double getResistance() {
        double sum = 0.0;
        for(Wire next: nexts){
            sum += (1 / next.getNext().getTotalResistance());
        }
        return (1 / sum);
    }

    @Override
    public void setNext(Wire next) {
        nexts.add(next);
    }
}
