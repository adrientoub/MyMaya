package script.types;

/**
 * Created by Adrien on 19/12/2016.
 */
public class IntegerType extends NumericType {
    private static Type instance = new IntegerType();

    @Override
    public boolean compatibleWith(Type other) {
        return other instanceof NumericType;
    }

    public static Type getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "IntegerType{}";
    }
}
