package calculator;

/**
 * Created by bagus maulana on 12/01/2016.
 */
public class IDFactory {
    public static int id = 0;

    public static int getId() {
        id++;
        return id;
    }
}
