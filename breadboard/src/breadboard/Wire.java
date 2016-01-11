package breadboard;

/**
 * Created by bagus maulana on 11/01/2016.
 */
public class Wire {
    public static final int FROM = 0;
    public static final int TO = 1;
    private Component prev;
    private Component next;

    public Wire() {}

    public Wire(Component c, int direction) {
        if(direction == FROM) { prev = c; }
        if(direction == TO) { next = c; }
    }

    public Wire(Component prev, Component next) {
        this.prev = prev;
        this.next = next;
    }
}
