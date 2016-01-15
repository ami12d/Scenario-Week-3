package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Ammeter extends Component {

    public Ammeter() {
        super(0.0);
    }

    @Override
    public double getCurrent() {
        Component prev = getPreviousComponent();
        while(prev.getClass() != Battery.class && prev.getClass() != ParallelNodeStart.class) {
            prev.getPreviousComponent();
        }
        return prev.getVoltage() / getTotalResistance();
    }
}
