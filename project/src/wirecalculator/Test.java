package wirecalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Test {
    private static int id = 0;

    private static void assertEverythingAboutIt(Component component, double resistance, double voltage, double current) {
        boolean checkR = component.getResistance() == resistance;
        boolean checkV = component.getVoltage() == voltage;
        boolean checkI = component.getCurrent() == current;
        if(checkR && checkV && checkI) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }
    }

    private static int getID() {
        id++;
        return id;
    }

    public static void main(String[] args) {
        //Current limitations:
        //Can only have one battery
        //Cannot remove components once added (has to re-parse everything every time user checks voltage/current)

        //Series circuit test
        Battery battery = new Battery(10);
        Resistor resistor1 = new Resistor(2);
        Resistor resistor2 = new Resistor(3);
        Wire wire1 = new Wire(battery, resistor1);
        Wire wire2 = new Wire(resistor1, resistor2);
        Wire wire3 = new Wire(resistor2, battery);
        System.out.println("Series circuit test");
        assertEverythingAboutIt(battery, 0.0, 10.0, 2.0);
        assertEverythingAboutIt(resistor1, 2.0, 4.0, 2.0);
        assertEverythingAboutIt(resistor2, 3.0, 6.0, 2.0);
        System.out.println();

        //Parallel circuit test
        battery = new Battery(10);
        ParallelNodeStart sns = new ParallelNodeStart();
        ParallelNodeEnd sne = new ParallelNodeEnd();
        resistor1 = new Resistor(4);
        resistor2 = new Resistor(4);
        wire1 = new Wire(battery, sns);
        wire2 = new Wire(sns, resistor1);
        wire3 = new Wire(sns, resistor2);
        Wire wire4 = new Wire(resistor1, sne);
        Wire wire5 = new Wire(resistor2, sne);
        Wire wire6 = new Wire(sne, battery);
        System.out.println("Parallel circuit test");
        assertEverythingAboutIt(battery, 0.0, 10.0, 5.0);
        assertEverythingAboutIt(resistor1, 4.0, 10.0, 2.5);
        assertEverythingAboutIt(resistor2, 4.0, 10.0, 2.5);
        System.out.println();

        //Series-Parallel circuit test
        battery = new Battery(10);
        ParallelNodeStart sns1 = new ParallelNodeStart();
        ParallelNodeEnd sne1 = new ParallelNodeEnd();
        resistor1 = new Resistor(2);
        resistor2 = new Resistor(2);
        ParallelNodeStart sns2 = new ParallelNodeStart();
        ParallelNodeEnd sne2 = new ParallelNodeEnd();
        Resistor resistor3 = new Resistor(6);
        Resistor resistor4 = new Resistor(6);
        wire1 = new Wire(battery, sns1);
        wire2 = new Wire(sns1, resistor1);
        wire3 = new Wire(sns1, resistor2);
        wire4 = new Wire(resistor1, sne1);
        wire5 = new Wire(resistor2, sne1);
        wire6 = new Wire(sne1, sns2);
        Wire wire7 = new Wire(sns2, resistor3);
        Wire wire8 = new Wire(sns2, resistor4);
        Wire wire9 = new Wire(resistor3, sne2);
        Wire wire10 = new Wire(resistor4, sne2);
        Wire wire11 = new Wire(sne2, battery);
        System.out.println("Series-Parallel circuit test");
        assertEverythingAboutIt(battery, 0.0, 10.0, 2.5);
        assertEverythingAboutIt(resistor1, 2.0, 2.5, 1.25);
        assertEverythingAboutIt(resistor2, 2.0, 2.5, 1.25);
        assertEverythingAboutIt(resistor3, 6.0, 7.5, 1.25);
        assertEverythingAboutIt(resistor4, 6.0, 7.5, 1.25);
        System.out.println();

        //Parallel-Series circuit test
        battery = new Battery(10);
        sns = new ParallelNodeStart();
        sne = new ParallelNodeEnd();
        resistor1 = new Resistor(1);
        resistor2 = new Resistor(2);
        resistor3 = new Resistor(3);
        resistor4 = new Resistor(4);
        wire1 = new Wire(battery, sns);
        wire2 = new Wire(sns, resistor1);
        wire3 = new Wire(sns, resistor2);
        wire4 = new Wire(resistor2, resistor3);
        wire5 = new Wire(resistor1, resistor4);
        wire6 = new Wire(resistor3, sne);
        wire7 = new Wire(resistor4, sne);
        wire8 = new Wire(sne, battery);
        System.out.println("Parallel-Series circuit test");
        assertEverythingAboutIt(battery, 0.0, 10.0, 4.0);
        assertEverythingAboutIt(resistor1, 1.0, 2.0, 2.0);
        assertEverythingAboutIt(resistor2, 2.0, 4.0, 2.0);
        assertEverythingAboutIt(resistor3, 3.0, 6.0, 2.0);
        assertEverythingAboutIt(resistor4, 4.0, 8.0, 2.0);
        System.out.println();
    }
}