package script.types;

/**
 * Created by Adrien on 19/12/2016.
 */
public class VoidType extends Type {
    private static Type instance = new VoidType();

    @Override
    public boolean compatibleWith(Type other) {
        return other instanceof VoidType;
    }

    public static Type getInstance() {
        return instance;
    }
}
