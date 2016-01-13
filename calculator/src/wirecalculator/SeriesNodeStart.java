package wirecalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class SeriesNodeStart extends Component {

    private List<Wire> nexts;

    public SeriesNodeStart() {
        super(0.0);
        nexts = new ArrayList<>();
    }

    @Override
    public Component getNextComponent() {
        //get the component after the end of the series circuit
        Component component = nexts.get(0).getNext();
        while(component.getClass() != SeriesNodeEnd.class) {
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
