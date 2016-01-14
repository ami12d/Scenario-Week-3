package wirecalculator;

import java.util.*;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Calculate {

    public static void calculate(List<ParsedComponent> parsedComponents, List<ParsedWire> parsedWires) {
        HashMap<Integer, Component> components = new HashMap<>();
        List<Wire> wires = new ArrayList<>();

        //create components and map them to their id
        for (ParsedComponent component : parsedComponents) {
            switch (component.getStyle()) {
                case ("Battery"):
                    double voltage = Double.valueOf(component.getValue()); //calculate the value
                    components.put(component.getID(), new Battery(voltage));
                    break;
                case ("Resistor"):
                    double resistance1 = Double.valueOf(component.getValue()); //calculate the value
                    components.put(component.getID(), new Resistor(resistance1));
                    break;
                case ("ParallelNode"):
                    switch (component.getValue()) {
                        case ("Start"):
                            components.put(component.getID(), new ParallelNodeStart());
                            break;
                        case ("End"):
                            components.put(component.getID(), new ParallelNodeEnd());
                            break;
                    }
                    break;
                case ("Ammeter"):
                    components.put(component.getID(), new Ammeter());
                    break;
                case ("Voltmeter"):
                    components.put(component.getID(), new Voltmeter());
                    break;
                case ("Lamp"):
                    Double resistance2 = Objects.equals(component.getValue(), "On") ? 12.0 : 0.0;
                    components.put(component.getID(), new Resistor(resistance2));
                    break;
                case ("Motor"):
                    Double resistance3 = Objects.equals(component.getValue(), "On") ? 50.0 : 0.0;
                    components.put(component.getID(), new Resistor(resistance3));
                    break;
                //etc.
            }
        }

        //connect wires in order

        /*for (ParsedWire wire : parsedWires) {
            wires.add(new Wire(components.get(wire.getSource()), components.get(wire.getTarget())));
        } if no need for directed graph*/

        List<Integer> connected = new ArrayList<>();
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        connected.add(getBatteryID(components));
        stack1.push(getBatteryID(components));

        while(stack1.size() > 0 || stack2.size() > 0) {
            int id = stack1.size() > 0 ? stack1.pop() : stack2.pop();
            List<ParsedWire> addedWires = new ArrayList<>();
            for (ParsedWire wire : parsedWires) {
                if (wire.getSource() == id) {
                    wires.add(new Wire(components.get(id), components.get(wire.getTarget())));
                    if(!connected.contains(wire.getTarget())){
                        connected.add(wire.getTarget());
                        if (components.get(wire.getTarget()).getClass() != ParallelNodeEnd.class) {
                            stack1.push(wire.getTarget());
                        } else {
                            stack2.push(wire.getTarget());
                        }
                    }
                    addedWires.add(wire);
                    if(components.get(id).getClass() == Battery.class) { break; }
                }
                else if (wire.getTarget() == id) {
                    wires.add(new Wire(components.get(id), components.get(wire.getSource())));
                    if(!connected.contains(wire.getSource())) {
                        connected.add(wire.getSource());
                        if (components.get(wire.getSource()).getClass() != ParallelNodeEnd.class) {
                            stack1.push(wire.getSource());
                        } else {
                            stack2.push(wire.getSource());
                        }
                    }
                    addedWires.add(wire);
                    if(components.get(id).getClass() == Battery.class) { break; }
                }
            }
            for(ParsedWire wire : addedWires) {
                parsedWires.remove(wire);
            }
        }

        //print current/voltage info
        for (int componentID : connected) {
            printEverythingAboutIt(componentID, components.get(componentID));
        }
    }

    private static int getBatteryID(HashMap<Integer, Component> components) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (components.get(i) != null && components.get(i).getClass() == Battery.class) {
                return i;
            }
        }
        return -1; //should be unreachable
    }

    private static void printEverythingAboutIt(int componentID, Component component) {
        System.out.println("ID = " + componentID);
        System.out.println(component.getClass());
        System.out.println("Resistance = " + component.getResistance());
        System.out.println("Voltage = " + component.getVoltage());
        System.out.println("Current = " + component.getCurrent());
        System.out.println();
    }

    //test method

    public static void main(String[] args) {
        //series
        List<ParsedComponent> components = new ArrayList<>();
        List<ParsedWire> wires = new ArrayList<>();
        components.add(new ParsedComponent("Battery", "10.0", 1));
        components.add(new ParsedComponent("Resistor", "2.0", 2));
        components.add(new ParsedComponent("Resistor", "3.0", 3));
        wires.add(new ParsedWire(1, 3));
        wires.add(new ParsedWire(1, 2));
        wires.add(new ParsedWire(3, 2));
        calculate(components, wires);

        //parallel
        components = new ArrayList<>();
        wires = new ArrayList<>();
        components.add(new ParsedComponent("Battery", "10.0", 1));
        components.add(new ParsedComponent("Resistor", "4.0", 2));
        components.add(new ParsedComponent("Resistor", "4.0", 3));
        components.add(new ParsedComponent("ParallelNode", "Start", 4));
        components.add(new ParsedComponent("ParallelNode", "End", 5));
        wires.add(new ParsedWire(2, 5));
        wires.add(new ParsedWire(5, 3));
        wires.add(new ParsedWire(4, 1));
        wires.add(new ParsedWire(4, 2));
        wires.add(new ParsedWire(3, 4));
        wires.add(new ParsedWire(5, 1));
        calculate(components, wires);

        //series-parallel
        components = new ArrayList<>();
        wires = new ArrayList<>();
        components.add(new ParsedComponent("Battery", "10.0", 1));
        components.add(new ParsedComponent("Resistor", "2.0", 2));
        components.add(new ParsedComponent("Resistor", "2.0", 3));
        components.add(new ParsedComponent("Resistor", "6.0", 4));
        components.add(new ParsedComponent("Resistor", "6.0", 5));
        components.add(new ParsedComponent("ParallelNode", "Start", 6));
        components.add(new ParsedComponent("ParallelNode", "End", 7));
        components.add(new ParsedComponent("ParallelNode", "Start", 8));
        components.add(new ParsedComponent("ParallelNode", "End", 9));
        wires.add(new ParsedWire(6, 1));
        wires.add(new ParsedWire(9, 1));
        wires.add(new ParsedWire(6, 2));
        wires.add(new ParsedWire(2, 7));
        wires.add(new ParsedWire(7, 3));
        wires.add(new ParsedWire(7, 8));
        wires.add(new ParsedWire(4, 8));
        wires.add(new ParsedWire(8, 5));
        wires.add(new ParsedWire(4, 9));
        wires.add(new ParsedWire(9, 5));
        wires.add(new ParsedWire(3, 6));
        calculate(components, wires);
    }
}
