package calculator;

/**
 * Created by bagus maulana on 12/01/2016.
 */
public class Battery extends Component {
    private double voltage;

    public Battery(double voltage, double resistance, Circuit parent) {
        super(resistance, parent);
        this.voltage = voltage;
    }

    @Override
    public double getVoltage() {
        return voltage;
    }

    public double getCurrent() {
        return parent.getCurrent();
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }
}
