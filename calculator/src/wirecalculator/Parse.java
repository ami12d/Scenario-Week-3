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
                    int voltage = ParsedComponent.getValue(); //parse the value
                    components.add(new Battery(voltage, ParsedComponent.getID()));
                    break;
                case("Resistor"):
                    int resistance = ParsedComponent.getValue(); //parse the value
                    components.add(new Battery(resistance, ParsedComponent.getID()));
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
