package calculator;

/**
 * Created by bagus maulana on 12/01/2016.
 */
public class SeriesCircuit extends Circuit {

    public SeriesCircuit() {
        super(null);
    }

    public SeriesCircuit(Circuit parent) {
        super(parent);
    }

    @Override
    public Circuit addCircuit() {
        Circuit series = new ParallelCircuit(this);
        components.add(series);
        return series;
    }

    @Override
    public double getVoltage() {
        if(parent == null) {
            double sum = 0;
            for (Battery battery : getBatteries()) {
                sum += battery.getVoltage();
            }
            return sum;
        } else {
            return super.getVoltage();
        }
    }

    @Override
    public double getResistance() {
        double sum = 0;
        for(Component component : components) {
            sum += component.getResistance();
        }
        return sum;
    }

    @Override
    public double getCurrent() {
        //may be changed to make series parallel circuits work
        return getVoltage() / getResistance();
    }
}
