package wirecalculator;

/**
 * Created by bagus maulana on 13/01/2016.
 */
public class Ammeter extends Component {

    public Ammeter(int id) {
        super(0.0, id);
    }

    @Override
    public double getCurrent() {
        return getPreviousComponent().getCurrent();
    }
}
