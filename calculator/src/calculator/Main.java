package calculator;

public class Main {

    public static void printEverythingAboutIt(Component component) {
        System.out.println(component.getClass());
        System.out.println("Resistance = " + component.getResistance());
        System.out.println("Voltage = " + component.getVoltage());
        System.out.println("Current = " + component.getCurrent());
        System.out.println();
    }

    public static void main(String[] args) {
        /* Current limitations:
         * Battery can only be in top level circuit
         * Battery cannot be in parallel
         */

	    // Series circuit test
        Circuit series = new SeriesCircuit();
        Battery battery = series.addBattery(10);
        Resistor resistor1 = series.addResistor(2);
        Resistor resistor2 = series.addResistor(3);

        //printEverythingAboutIt(series);
        printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);

        //Parallel circuit test
        Circuit parallelWrapper = new SeriesCircuit(); //top level circuit has to be a series circuit
        battery = parallelWrapper.addBattery(10);
        Circuit parallel = parallelWrapper.addCircuit();
        resistor1 = parallel.addResistor(4);
        resistor2 = parallel.addResistor(4);

        //printEverythingAboutIt(parallelWrapper);
        //printEverythingAboutIt(parallel);
        printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);

        //Series-Parallel circuit test (http://www.allaboutcircuits.com/textbook/direct-current/chpt-7/analysis-technique/)
        Circuit main = new SeriesCircuit();
        battery = main.addBattery(24);
        Circuit topParallel = main.addCircuit();
        resistor1 = topParallel.addResistor(100);
        resistor2 = topParallel.addResistor(250);
        Circuit botParallel = main.addCircuit();
        Resistor resistor3 = botParallel.addResistor(350);
        Resistor resistor4 = botParallel.addResistor(200);

        printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);
        printEverythingAboutIt(resistor3);
        printEverythingAboutIt(resistor4);

        //Parallel-Series circuit test
        main = new SeriesCircuit();
        battery = main.addBattery(10);
        parallel = main.addCircuit();
        Circuit leftSeries = parallel.addCircuit();
        Circuit rightSeries = parallel.addCircuit();
        resistor1 = leftSeries.addResistor(1);
        resistor2 = rightSeries.addResistor(2);
        resistor3 = rightSeries.addResistor(3);
        resistor4 = leftSeries.addResistor(4);

        printEverythingAboutIt(battery);
        printEverythingAboutIt(resistor1);
        printEverythingAboutIt(resistor2);
        printEverythingAboutIt(resistor3);
        printEverythingAboutIt(resistor4);
    }
}
