package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Battery extends Component {
    private double voltage;

    public Battery(double voltage) {
        super(0.0);
        this.voltage = voltage;
    }

    public Battery(double voltage, double resistance) {
        super(resistance);
        this.voltage = voltage;
    }

    @Override
    public double getVoltage() {
        return voltage;
    }

    @Override
    public double getCurrent() {
        double totalResistance = 0.0;
        Component current = this;
        do {
            totalResistance += current.getResistance();
            current = current.getNextComponent();
        } while(current != this);
        return voltage / totalResistance;
    }
}
