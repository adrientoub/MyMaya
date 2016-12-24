package script.types;

/**
 * Created by Adrien on 19/12/2016.
 */
public class BooleanType extends Type {
    private static Type instance = new BooleanType();

    public static Type getInstance() {
        return instance;
    }
}
