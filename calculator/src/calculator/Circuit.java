package calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 11/01/2016.
 */
public abstract class Circuit extends Component {
    protected List<Component> components;
    //private List<Wire> wires;

    public Circuit(Circuit parent) {
        super(0.0, parent);
        components = new ArrayList<>();
        //wires = new ArrayList<>();
    }

    public Battery addBattery(double voltage) {
        Battery battery = new Battery(voltage, 0.0, this);
        components.add(battery);
        return battery;
    }

    public Battery addBattery(double voltage, double resistance) {
        Battery battery = new Battery(voltage, resistance, this);
        components.add(battery);
        return battery;
    }

    public Resistor addResistor(double resistance) {
        Resistor resistor = new Resistor(resistance, this);
        components.add(resistor);
        return resistor;
    }

    public boolean removeComponent(Component component) {
        if(components.contains(component)) {
            components.remove(component);
            return true;
        } else {
            for(Circuit circuit : getSubCircuits()) {
                if(circuit.removeComponent(component)) { return true; }
            }
        }
        return false;
    }

    private List<Circuit> getSubCircuits() {
        List<Circuit> circuits = new ArrayList<>();
        for(Component component : components){
            if(component.getClass() == Circuit.class) {
                circuits.add((Circuit) component);
            }
        }
        return circuits;
    }

    protected List<Battery> getBatteries() {
        List<Battery> batteries = new ArrayList<>();
        for(Component component : components){
            if(component.getClass() == Battery.class) {
                batteries.add((Battery) component);
            }
        }
        return batteries;
    }

    public abstract Circuit addCircuit();
}
