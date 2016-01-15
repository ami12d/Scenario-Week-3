package wirecalculator;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class BatteryTest extends TestCase {
    private Battery battery;

    public void setUp() throws Exception {
        super.setUp();
        battery = new Battery(5.0);
    }

    public void testGetVoltage() throws Exception {
        assertEquals(5.0, battery.getVoltage());
    }

    public void testGetCurrent() throws Exception {
        Resistor resistor = new Resistor(10.0);
        new Wire(battery, resistor);
        new Wire(resistor, battery);

        assertEquals(0.5, battery.getCurrent());
    }
}