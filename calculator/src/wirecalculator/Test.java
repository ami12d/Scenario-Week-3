package wirecalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Test {
    private static int id = 0;

    private static void printEverythingAboutIt(Component component) {
        System.out.println(component.getClass());
        System.out.println("Resistance = " + component.getResistance());
        System.out.println("Voltage = " + component.getVoltage());
        System.out.println("Current = " + component.getCurrent());
        System.out.println();
    }

    private static int getID() {
        id++;
        return id;
    }

    public static void main(String[] args) {
        //TODO change to testing classes (assertThat etc.)

        //Limitations:
        //Can only have one battery
        //Cannot remove components once added


        //Series test
        Battery battery = new Battery(10, getID());
        Resistor resistor1 = new Resistor(2, getID());
        Resistor resistor2 = new Resistor(3, getID());
        Wire wire1 = new Wire(battery, resistor1);
        Wire wire2 = new Wire(resistor1, resistor2);
        Wire wire3 = new Wire(resistor2, battery);
        /*printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);*/

        //Parallel test
        battery = new Battery(10, getID());
        ParallelNodeStart sns = new ParallelNodeStart(getID());
        ParallelNodeEnd sne = new ParallelNodeEnd(getID());
        resistor1 = new Resistor(4, getID());
        resistor2 = new Resistor(4, getID());
        wire1 = new Wire(battery, sns);
        wire2 = new Wire(sns, resistor1);
        wire3 = new Wire(sns, resistor2);
        Wire wire4 = new Wire(resistor1, sne);
        Wire wire5 = new Wire(resistor2, sne);
        Wire wire6 = new Wire(sne, battery);
        /*printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);*/

        //Series-Parallel circuit test (http://www.allaboutcircuits.com/textbook/direct-current/chpt-7/analysis-technique/)
        battery = new Battery(24, getID());
        ParallelNodeStart sns1 = new ParallelNodeStart(getID());
        ParallelNodeEnd sne1 = new ParallelNodeEnd(getID());
        resistor1 = new Resistor(100, getID());
        resistor2 = new Resistor(250, getID());
        ParallelNodeStart sns2 = new ParallelNodeStart(getID());
        ParallelNodeEnd sne2 = new ParallelNodeEnd(getID());
        Resistor resistor3 = new Resistor(350, getID());
        Resistor resistor4 = new Resistor(200, getID());
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
        printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);
        printEverythingAboutIt(resistor3);
        printEverythingAboutIt(resistor4);

        //Parallel-Series circuit test
        battery = new Battery(10, getID());
        sns = new ParallelNodeStart(getID());
        sne = new ParallelNodeEnd(getID());
        resistor1 = new Resistor(1, getID());
        resistor2 = new Resistor(2, getID());
        resistor3 = new Resistor(3, getID());
        resistor4 = new Resistor(4, getID());
        wire1 = new Wire(battery, sns);
        wire2 = new Wire(sns, resistor1);
        wire3 = new Wire(sns, resistor2);
        wire4 = new Wire(resistor2, resistor3);
        wire5 = new Wire(resistor1, resistor4);
        wire6 = new Wire(resistor3, sne);
        wire7 = new Wire(resistor4, sne);
        wire8 = new Wire(sne, battery);
        /*printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);
        printEverythingAboutIt(resistor3);
        printEverythingAboutIt(resistor4);*/
    }
}