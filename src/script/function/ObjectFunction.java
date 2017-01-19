package script.function;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import model.Mesh;
import model.ObjReader;
import model.SceneModel;
import model.Sphere;
import script.ast.ArgumentListDef;
import script.lexer.NumberToken;
import script.lexer.QuotedStringToken;
import script.lexer.Token;
import script.types.DoubleType;
import script.types.StringType;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Adrien on 24/12/2016.
 */
public class ObjectFunction extends Function {
    public enum ObjectType {
        SPHERE,
        BOX,
        MESH,
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
            case BOX:
                SceneModel.addBox(c, pos, name.getString());
                break;
            case POINT_LIGHT:
                SceneModel.addPointLight(c, pos, name.getString());
                break;
            case SPHERE:
                SceneModel.addSphere(1, c, pos, name.getString());
                break;
            case DIRECTIONAL_LIGHT:
                SceneModel.addDirectionalLight(c, pos, name.getString());
                break;
            case MESH:
                try {
                    Mesh m = ObjReader.openObj(new File(name.getString()));
                    m.setColor(c);
                    m.getInnerObject().setTranslateX(pos.getX());
                    m.getInnerObject().setTranslateY(pos.getY());
                    m.getInnerObject().setTranslateZ(pos.getZ());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.err.println("Not implemented for " + objectType);
        }
    }
}
