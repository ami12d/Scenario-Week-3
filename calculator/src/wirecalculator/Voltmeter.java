package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Voltmeter extends Component {

    public Voltmeter(int id) {
        super(Double.POSITIVE_INFINITY, id);
    }

    @Override
    public double getVoltage() {
        return getPreviousComponent().getVoltage();
    }
}
