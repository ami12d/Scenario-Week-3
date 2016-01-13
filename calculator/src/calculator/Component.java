package calculator;

/**
 * Created by bagus maulana on 11/01/2016.
 */
public abstract class Component {
    private double resistance;
    //private Wire prev;
    //private Wire next;
    protected Circuit parent;

    public Component(double resistance, Circuit parent) {
        this.resistance = resistance;
        this.parent = parent;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getResistance() {
        return resistance;
    }

    public double getVoltage() {
        if(parent.getClass() == SeriesCircuit.class) {
            return parent.getVoltage() * getResistance() / parent.getResistance();
        } else if(parent.getClass() == ParallelCircuit.class) {
            return parent.getVoltage();
        }
        return 0; //should be unreachable
    }

    public double getCurrent() {
        return getVoltage() / getResistance();
    }

    /*public Wire getPrev() {
        return prev;
    }

    public void setPrev(Wire prev) {
        this.prev = prev;
    }

    public Wire getNext() {
        return next;
    }

    public void setNext(Wire next) {
        this.next = next;
    }*/
}
