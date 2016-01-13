package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Ammeter extends Component {

    public Ammeter() {
        super(0);
    }

    @Override
    public double getVoltage() {
        double totalResistance = 0;
        Component prev = getPreviousComponent();
        while(prev.getClass() != Battery.class && prev.getClass() != ParallelNodeStart.class ) {
            totalResistance += prev.getResistance();
            prev = prev.getPreviousComponent();
        }
        Component next = getNextComponent();
        if(prev.getClass() == Battery.class) {
            totalResistance += prev.getResistance();
            while(next.getClass() != Battery.class) {
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        } else if(prev.getClass() == ParallelNodeStart.class) {
            while(next != prev.getNextComponent().getPreviousComponent()) { //gets prev's pair
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        }
        return prev.getVoltage() / totalResistance;
    }

    @Override
    public double getCurrent() {
        return getVoltage() / getTotalResistance();
    }
}
