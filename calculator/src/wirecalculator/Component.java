package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public abstract class Component {
    private double resistance;
    private Wire prev;
    private Wire next;

    public Component(double resistance) {
        this.resistance = resistance;
    }

    public double getVoltage() {
        double totalResistance = getResistance();
        Component prev = getPreviousComponent();
        while(prev.getClass() != Battery.class && prev.getClass() != SeriesNodeStart.class ) {
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
        } else if(prev.getClass() == SeriesNodeStart.class) {
            while(next != prev.getNextComponent().getPreviousComponent()) { //gets prev's pair
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        }
        return prev.getVoltage() * getResistance() / totalResistance;
    }

    public double getTotalResistance() {
        double totalResistance = resistance;
        Component prev = getPreviousComponent();
        while(prev.getClass() != Battery.class && prev.getClass() != SeriesNodeStart.class ) {
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
        } else if(prev.getClass() == SeriesNodeStart.class) {
            while(next != prev.getNextComponent().getPreviousComponent()) { //gets prev's pair
                totalResistance += next.getResistance();
                next = next.getNextComponent();
            }
        }
        return totalResistance;
    }

    public Component getPreviousComponent() {
        return prev.getPrev();
    }

    public Component getNextComponent() {
        return next.getNext();
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
}
