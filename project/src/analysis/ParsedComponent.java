package analysis;

/**
 * Created by bagus maulana on 14/01/2016.
 */
public class ParsedComponent {
    private String style;
    private String value;
    private int id;

    public ParsedComponent(String style, String value, int id) {
        this.style = style;
        this.value = value;
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public String getValue() {
        return value;
    }

    public int getID() {
        return id;
    }
}
