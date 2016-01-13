package wirecalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Parse {

    private static Component getComponentByID(List<Component> components, int id) {
        for(Component component: components) {
            if(component.getId() == id) {
                return component;
            }
        }
        return null;
    }
/*
    public static void parse(List<ParsedComponent> parsedComponents, List<ParsedWire> parsedWires) {
        List<Component> components = new ArrayList<>();
        List<Wire> wires = new ArrayList<>();
        for(ParsedComponent component : parsedComponents) {
            switch(component.getStyle()) {
                case("Battery"):
                    double voltage = Double.valueOf(component.getValue()); //parse the value
                    components.add(new Battery(voltage, component.getID()));
                    break;
                case("Resistor"):
                    double resistance1 = Double.valueOf(component.getValue()); //parse the value
                    components.add(new Resistor(resistance1, component.getID()));
                    break;
                case("ParallelNode"):
                    switch (component.getValue()) {
                        case("Start"):
                            components.add(new ParallelNodeStart(component.getID()));
                            break;
                        case("End"):
                            components.add(new ParallelNodeEnd(component.getID()));
                            break;
                    }
                    break;
                case("Ammeter"):
                    components.add(new Ammeter(component.getID()));
                    break;
                case("Voltmeter"):
                    components.add(new Voltmeter(component.getID()));
                    break;
                case("Lamp"):
                    Double resistance2 = component.getValue() == "On" ? 12.0 : 0.0;
                    components.add(new Resistor(resistance2, component.getID()));
                    break;
                case("Motor"):
                    Double resistance3 = component.getValue() == "On" ? 50.0 : 0.0;
                    components.add(new Resistor(resistance3, component.getID()));
                    break;
                //etc.
            }
        }
        for(ParsedWire wire : parsedWires) {
            wires.add(new Wire(getComponentByID(wire.getSource()), getComponentByID(wire.getTarget())));
        }
        //print current/voltage info
    }
*/
}
