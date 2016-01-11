package breadboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 11/01/2016.
 */
public abstract class Circuit extends Component {
    private List<Component> components;
    private List<Wire> wires;

    public Circuit() {
        super(0.0);
        components = new ArrayList<>();
        wires = new ArrayList<>();
    }

    public Component addComponent(Class type, double resistance) {
        //TODO make factory for component
    }

    public Component getComponent(){
        //use id?
    }

    public Wire addWire() {
        Wire wire = new Wire();
        wires.add(wire);
        return wire;
    }

    public Wire addWire(Component c, int direction) {
        Wire wire = new Wire(c, direction);
        wires.add(wire);
        return wire;
    }

    public Wire addWire(Component prev, Component next) {
        Wire wire = new Wire(prev, next);
        wires.add(wire);
        return wire;
    }

    public Wire getWire() {
        //use id?
    }

    public abstract double getTotalVoltage();

    public abstract double getTotalResistance();

    public abstract double getTotalCurrent();

    @Override
    public double getResistance() {
        return getTotalResistance();
    }
}
