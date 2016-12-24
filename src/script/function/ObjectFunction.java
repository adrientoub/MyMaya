package script.function;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import model.SceneModel;
import model.Sphere;
import script.ast.ArgumentListDef;
import script.lexer.NumberToken;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.DoubleType;
import script.types.StringType;

import java.util.List;

/**
 * Created by Adrien on 24/12/2016.
 */
public class ObjectFunction extends Function {
    public enum ObjectType {
        SPHERE,
        POINT_LIGHT,
        DIRECTIONAL_LIGHT
    }
    private ObjectType objectType;

    public ObjectFunction(ObjectType objectType) {
        super(new ArgumentListDef("name", "x", "y", "z", "r", "g", "b"));
        this.objectType = objectType;
        getArguments().getArgumentListType().getTypes().set(0, StringType.getInstance());
        for (int i = 1; i < 7; i++) {
            getArguments().getArgumentListType().getTypes().set(i, DoubleType.getInstance());
        }
    }

    @Override
    public void apply(List<Token> arguments) {
        QuotedStringToken name = (QuotedStringToken) arguments.get(0);
        NumberToken x = (NumberToken) arguments.get(1);
        NumberToken y = (NumberToken) arguments.get(2);
        NumberToken z = (NumberToken) arguments.get(3);
        NumberToken r = (NumberToken) arguments.get(4);
        NumberToken g = (NumberToken) arguments.get(5);
        NumberToken b = (NumberToken) arguments.get(6);
        System.out.println("Creating " + objectType + " " + name + " at (" + x + ", " + y + ", " + z + ") with color (" + r + ", " + g + ", " + b + ")");
        Color c = Color.color(r.getValue(), g.getValue(), b.getValue());
        Point3D pos = new Point3D(x.getValue(), y.getValue(), z.getValue());
        switch (objectType) {
            case POINT_LIGHT:
                SceneModel.addPointLight(c, pos, name.getString());
                break;
            case SPHERE:
                SceneModel.addSphere(1, c, pos, name.getString());
                break;
            case DIRECTIONAL_LIGHT:
                SceneModel.addDirectionalLight(c, pos, name.getString());
                break;
            default:
                System.err.println("Not implemented for " + objectType);
        }
    }
}
