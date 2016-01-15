package wirecalculator;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class AmmeterTest extends TestCase {
    private Ammeter ammeter;

    public void setUp() throws Exception {
        super.setUp();
        ammeter = new Ammeter();
    }

    public void testGetCurrent() throws Exception {
        Battery battery = new Battery(5.0);
        Resistor resistor = new Resistor(10.0);
        new Wire(battery, ammeter);
        new Wire(ammeter, resistor);
        new Wire(resistor, battery);

        assertEquals(0.5, ammeter.getCurrent());
    }
}