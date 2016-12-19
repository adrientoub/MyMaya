package script.types;

/**
 * Created by Adrien on 19/12/2016.
 */
public abstract class Type {
    protected static Type instance;

    protected Type() {}

    public static Type getInstance() {
        return instance;
    }
}
