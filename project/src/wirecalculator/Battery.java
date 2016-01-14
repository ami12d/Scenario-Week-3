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

    @Override
    public double getVoltage() {
        return voltage;
    }

    @Override
    public double getCurrent() {
        double totalResistance = 0.0;
        Component component = this;
        do {
            totalResistance += component.getResistance();
            component = component.getNextComponent();
        } while(component != this);
        return voltage / totalResistance;
    }
}
