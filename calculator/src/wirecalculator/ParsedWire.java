package wirecalculator;

/**
 * Created by bagus maulana on 14/01/2016.
 */
public class ParsedWire {
    private int source;
    private int target;

    public ParsedWire(int source, int target){
        this.source = source;
        this.target = target;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }
}
