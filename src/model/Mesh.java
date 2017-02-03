package model;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;

import java.util.ArrayList;

/**
 * Created by Adrien on 15/01/2017.
 */
public class Mesh extends Shape {
    private javafx.scene.shape.MeshView internalMesh;
    private static int meshCount = 0;
    private ArrayList<Point3D> vertices;
    private ArrayList<ObjReader.Face> faces;

    public Mesh(MeshView internalMesh, Color color, String name, ArrayList<Point3D> vertices, ArrayList<ObjReader.Face> faces) {
        super("mesh" + meshCount, color);
        meshCount++;
        this.internalMesh = internalMesh;
        if (name != null) {
            meshCount--;
            this.name = name;
        }
        attributes.setRefl(0);
        this.vertices = vertices;
        this.faces = faces;
    }

    @Override
    public Node getInnerObject() {
        return internalMesh;
    }

    @Override
    public double getScale() {
        return internalMesh.getScaleX();
    }

    @Override
    public void setScale(double scale) {
        super.setScale(scale);
        internalMesh.setScaleX(scale);
        internalMesh.setScaleY(scale);
        internalMesh.setScaleZ(scale);
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        internalMesh.setDrawMode(drawMode);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        ((PhongMaterial)(internalMesh.getMaterial())).setDiffuseColor(color);
    }

    @Override
    public String toString() {
        int triangle = 0;
        StringBuilder sb = new StringBuilder();
        String colorString = getColorString(color);
        String attributeString = attributes.toString();

        double scaleX = internalMesh.getScaleX();
        double scaleY = internalMesh.getScaleY();
        double scaleZ = internalMesh.getScaleZ();

        double translateX = internalMesh.getTranslateX();
        double translateY = internalMesh.getTranslateY();
        double translateZ = internalMesh.getTranslateZ();

        sb.append("mesh \"").append(name).append("\"\n");

        for (ObjReader.Face face: faces) {
            sb.append("triangle \"").append(name).append(triangle++).append("\" ");

            for (int i = 0; i < 3; i++) {
                Point3D point3D = vertices.get(face.vertices[i * 2]);
                sb.append(point3D.getX() * scaleX + translateX).append(" ")
                  .append(point3D.getY() * scaleY + translateY).append(" ")
                  .append(point3D.getZ() * scaleZ + translateZ).append(" ");
            }
            sb.append(attributeString).append(" ").append(colorString).append("\n");
        }

        return sb.append("end").toString();
    }
}
