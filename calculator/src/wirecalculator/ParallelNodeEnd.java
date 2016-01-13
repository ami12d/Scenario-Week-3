package wirecalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class ParallelNodeEnd extends Component {

    private List<Wire> prevs;

    public ParallelNodeEnd(int id) {
        super(0.0, id);
        prevs = new ArrayList<>();
    }

    @Override
    public Component getPreviousComponent() {
        //get the component before the start of the parallel circuit
        Component component = prevs.get(0).getConnectedComponent(this);
        while(component.getClass() != ParallelNodeStart.class) {
            component = component.getPreviousComponent();
        }
        return component.getPreviousComponent();
    }

    @Override
    public double getResistance() {
        double sum = 0.0;
        for(Wire prev: prevs){
            sum += (1 / prev.getConnectedComponent(this).getTotalResistance());
        }
        return (1 / sum);
    }

    @Override
    public void setPrev(Wire prev) {
        prevs.add(prev);
    }
}
