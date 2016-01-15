package analysis;

import junit.framework.TestCase;

/**
 * Created by bagus maulana on 15/01/2016.
 */
public class ParserDataTest extends TestCase {
    private ParserData parserData;

    public void setUp() throws Exception {
        super.setUp();
        parserData = new ParserData();
    }

    public void testAddComponents() throws Exception {
        parserData.addComponent("1", "style", "value");
    }

    public void testAddWires() throws Exception {
        parserData.addWire("1", "2");
    }

    public void testGetComponents() throws Exception {
        parserData.addComponent("1", "style", "value");
        assertEquals(1, parserData.getComponents().size());
    }

    public void testGetWires() throws Exception {
        parserData.addWire("1", "2");
        assertEquals(1, parserData.getWires().size());
    }
}