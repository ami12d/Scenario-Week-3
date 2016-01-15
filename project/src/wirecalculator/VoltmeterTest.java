package wirecalculator;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class VoltmeterTest extends TestCase {
    private Voltmeter voltmeter;

    public void setUp() throws Exception {
        super.setUp();
        voltmeter = new Voltmeter();
    }

    public void testGetVoltage() throws Exception {
        new Wire(new Battery(5.0), voltmeter);

        assertEquals(5.0, voltmeter.getVoltage());
    }
}