package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public abstract class Component {
    private double resistance;
    private Wire prev;
    private Wire next;
    private int id;

    public Component(double resistance, int id) {
        this.resistance = resistance;
        this.id = id;
    }

    public double getVoltage() {
        double totalResistance = getResistance();
        Component prev = getPreviousComponent();
        while(prev.getClass() != Battery.class && prev.getClass() != ParallelNodeStart.class ) {
            totalResistance += prev.getResistance();
            prev = prev.getPreviousComponent();
        }
        Component next = getNextComponent();
        if(prev.getClass() == Battery.class) {
            totalResistance += prev.getResistance();
            while(next.getClass() != Battery.class) {
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        } else if(prev.getClass() == ParallelNodeStart.class) {
            while(next != prev.getNextComponent().getPreviousComponent()) { //gets prev's pair
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        }
        return prev.getVoltage() * getResistance() / totalResistance;
    }

    public double getTotalResistance() {
        //gets the total resistance of the series circuit the component is connected to.
        double totalResistance = resistance;
        Component prev = getPreviousComponent();
        while(prev.getClass() != Battery.class && prev.getClass() != ParallelNodeStart.class ) {
            totalResistance += prev.getResistance();
            prev = prev.getPreviousComponent();
        }
        Component next = getNextComponent();
        if(prev.getClass() == Battery.class) {
            totalResistance += prev.getResistance();
            while(next.getClass() != Battery.class) {
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        } else if(prev.getClass() == ParallelNodeStart.class) {
            while(next != prev.getNextComponent().getPreviousComponent()) { //gets prev's pair
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        }
        return totalResistance;
    }

    public Component getPreviousComponent() {
        return prev.getConnectedComponent(this);
    }

    public Component getNextComponent() {
        return next.getConnectedComponent(this);
    }

    public double getResistance() {
        return resistance;
    }

    public double getCurrent() {
        return getVoltage() / getResistance();
    }

    public void setPrev(Wire prev) {
        this.prev = prev;
    }

    public void setNext(Wire next) {
        this.next = next;
    }

    public int getId() {
        return id;
    }
}
