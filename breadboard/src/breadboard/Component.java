package breadboard;

/**
 * Created by bagus maulana on 11/01/2016.
 */
public abstract class Component {
    private double resistance;
    private Wire prev;
    private Wire next;

    public Component(double resistance) {
        this.resistance = resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getResistance() {
        return resistance;
    }
}
