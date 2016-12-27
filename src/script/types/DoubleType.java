package script.types;

/**
 * Created by Adrien on 19/12/2016.
 */
public class DoubleType extends NumericType {
    private static Type instance = new DoubleType();

    @Override
    public boolean compatibleWith(Type other) {
        return other instanceof IntegerType || other instanceof DoubleType;
    }

    public static Type getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "DoubleType{}";
    }
}
