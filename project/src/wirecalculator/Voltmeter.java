package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Voltmeter extends Component {

    public Voltmeter() {
        super(Double.POSITIVE_INFINITY);
    }

    @Override
    public double getVoltage() {
        return getPreviousComponent().getVoltage();
    }
}
