package wirecalculator;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class WireTest extends TestCase {

    public void testGetConnectedComponent() throws Exception {
        Component component1 = new Resistor(10.0);
        Component component2 = new Resistor(10.0);
        Wire wire = new Wire(component1, component2);

        assertSame(component2, wire.getConnectedComponent(component1));
        assertSame(component1, wire.getConnectedComponent(component2));
    }
}