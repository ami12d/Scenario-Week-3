package wirecalculator;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class ComponentTest extends TestCase {
    private Component component;

    public void setUp() throws Exception {
        super.setUp();
        component = new Component(10.0) {};
    }

    public void testGetVoltage() throws Exception {
        Battery battery = new Battery(5.0);
        new Wire(battery, component);
        new Wire(component, battery);

        assertEquals(5.0, component.getVoltage());
    }

    public void testGetResistance() throws Exception {
        assertEquals(10.0, component.getResistance());
    }

    public void testGetCurrent() throws Exception {
        Battery battery = new Battery(5.0);
        new Wire(battery, component);
        new Wire(component, battery);

        assertEquals(0.5, component.getCurrent());
    }

    public void testGetTotalResistance() throws Exception {
        Battery battery = new Battery(5.0);
        Component component2 = new Component(5.0) {};
        new Wire(battery, component);
        new Wire(component, component2);
        new Wire(component2, battery);

        assertEquals(15.0, component.getTotalResistance());
    }

    public void testGetPreviousComponent() throws Exception {
        Component component2 = new Component(5.0) {};
        new Wire(component2, component);

        assertSame(component2, component.getPreviousComponent());
    }

    public void testGetNextComponent() throws Exception {
        Component component2 = new Component(5.0) {};
        new Wire(component, component2);

        assertSame(component2, component.getNextComponent());
    }
}