package wirecalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class SeriesNodeEnd extends Component {

    private List<Wire> prevs;

    public SeriesNodeEnd() {
        super(0.0);
        prevs = new ArrayList<>();
    }

    @Override
    public Component getPreviousComponent() {
        //get the component before the start of the series circuit
        Component component = prevs.get(0).getPrev();
        while(component.getClass() != SeriesNodeStart.class) {
            component = component.getPreviousComponent();
        }
        return component.getPreviousComponent();
    }

    @Override
    public double getResistance() {
        double sum = 0.0;
        for(Wire prev: prevs){
            sum += (1 / prev.getPrev().getTotalResistance());
        }
        return (1 / sum);
    }

    @Override
    public void setPrev(Wire prev) {
        prevs.add(prev);
    }
}
