package analysis;

/**
 * Created by bagus maulana on 14/01/2016.
 */
public class ParsedWire {
    private int source;
    private int target;

    public ParsedWire(int source, int target) {
        this.source = source;
        this.target = target;
    }

    public boolean isEquals(ParsedWire wire) {
        return (source == wire.getSource() && target == wire.getTarget()) ||
                (source == wire.getTarget() && target == wire.getSource());
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }
}
