package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Wire {
    private Component prev;
    private Component next;

    public Wire(Component prev, Component next) {
        prev.setNext(this);
        next.setPrev(this);
        this.prev = prev;
        this.next = next;
    }

    public Component getConnectedComponent(Component component) {
        //returns the component connected by the wire to the component in the parameter
        if(component == prev) return next;
        if(component == next) return prev;
        else return null;
    }
}
