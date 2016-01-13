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

    public Component getPrev() {
        return prev;
    }

    public void setPrev(Component prev) {
        this.prev = prev;
    }

    public Component getNext() {
        return next;
    }

    public void setNext(Component next) {
        this.next = next;
    }
}
