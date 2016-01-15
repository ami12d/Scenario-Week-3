package wirecalculator;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class ParallelNodeEndTest extends TestCase {
    ParallelNodeEnd end;
    Component prev;

    public void setUp() throws Exception {
        super.setUp();
        ParallelNodeStart start = new ParallelNodeStart();
        Component component1 = new Resistor(10.0);
        Component component2 = new Resistor(10.0);
        end = new ParallelNodeEnd();
        prev = new Resistor(6.0);

        new Wire(component1, end);
        new Wire(component2, end);
        new Wire(start, component1);
        new Wire(start, component2);
        new Wire(prev, start);
    }

    public void testGetPreviousComponent() throws Exception {
        assertSame(prev, end.getPreviousComponent());
    }

    public void testGetResistance() throws Exception {
        new Wire(end, new Resistor(4.0));

        assertEquals(5.0, end.getResistance());
    }
}