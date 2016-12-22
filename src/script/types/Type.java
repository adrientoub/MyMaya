package script.types;

/**
 * Created by Adrien on 19/12/2016.
 */
public abstract class Type {
    protected Type() {}

    public boolean compatibleWith(Type other) {
        return false;
    }
}
