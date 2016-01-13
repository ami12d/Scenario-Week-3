package calculator;

/**
 * Created by bagus maulana on 11/01/2016.
 */
public class Wire {
    public static final int PREV = 0;
    public static final int NEXT = 1;
    private Component prev;
    private Component next;

    public Wire() { }

    public Wire(Component c, int direction) {
        if(direction == PREV) { prev = c; }
        if(direction == NEXT) { next = c; }
    }

    public Wire(Component prev, Component next) {
        this.prev = prev;
        this.next = next;
    }
}
