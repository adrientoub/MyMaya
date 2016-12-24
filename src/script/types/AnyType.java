package script.types;

/**
 * Created by Adrien on 22/12/2016.
 */
public class AnyType extends Type {
    private static AnyType instance = new AnyType();

    private AnyType() {
    }

    @Override
    public boolean compatibleWith(Type other) {
        return true;
    }

    public static Type getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "AnyType{}";
    }
}
