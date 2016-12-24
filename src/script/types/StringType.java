package script.types;

/**
 * Created by Adrien on 19/12/2016.
 */
public class StringType extends Type {
    private static Type instance = new StringType();

    @Override
    public boolean compatibleWith(Type other) {
        return other instanceof StringType;
    }

    public static Type getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "StringType{}";
    }
}
