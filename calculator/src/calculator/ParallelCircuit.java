package calculator;

/**
 * Created by bagus maulana on 12/01/2016.
 */
public class ParallelCircuit extends Circuit {

    public ParallelCircuit(Circuit parent) {
        super(parent);
    }

    @Override
    public Circuit addCircuit() {
        Circuit parallel = new SeriesCircuit(this);
        components.add(parallel);
        return parallel;
    }

    @Override
    public double getResistance() {
        double sum = 0;
        for(Component component : components) {
            sum += (1 / component.getResistance());
        }
        return 1 / sum;
    }

    @Override
    public double getCurrent() {
        return getVoltage() / getResistance();
    }
}
