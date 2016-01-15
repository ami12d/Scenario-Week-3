package wirecalculator;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class ParallelNodeStartTest extends TestCase {
    ParallelNodeStart start;
    Component next;

    public void setUp() throws Exception {
        super.setUp();
        start = new ParallelNodeStart();
        Component component1 = new Resistor(10.0);
        Component component2 = new Resistor(10.0);
        ParallelNodeEnd end = new ParallelNodeEnd();
        next = new Resistor(6.0);

        new Wire(start, component1);
        new Wire(start, component2);
        new Wire(component1, end);
        new Wire(component2, end);
        new Wire(end, next);
    }

    public void testGetNextComponent() throws Exception {
        assertSame(next, start.getNextComponent());
    }

    public void testGetResistance() throws Exception {
        assertEquals(5.0, start.getResistance());
    }
}