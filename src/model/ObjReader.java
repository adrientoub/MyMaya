package model;

import javafx.geometry.Point3D;
import javafx.scene.shape.TriangleMesh;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Adrien on 15/01/2017.
 */
public class ObjReader {
    public static class Face {
        public Face(int[] vertices) {
            this.vertices = vertices;
        }

        public int[] vertices;
    }

    public static void openObj(File file) throws IOException {
        System.out.println("Opening " + file.getAbsolutePath());
        Path path = Paths.get(file.getAbsolutePath());
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        ArrayList<Point3D> vertices = new ArrayList<>();
        ArrayList<Double> verticeTexture = new ArrayList<>();
        ArrayList<Face> faces = new ArrayList<>();

        String[] lines = content.split("\n");
        for (String line: lines) {
            if (line.length() == 0 || line.startsWith("#")) {
                continue;
            }
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "v": // vertex
                    vertices.add(new Point3D(Double.valueOf(tokens[1]), Double.valueOf(tokens[2]), Double.valueOf(tokens[3])));
                    break;
                case "vt": // vertex texture
                    verticeTexture.add(Double.valueOf(tokens[1]));
                    verticeTexture.add(Double.valueOf(tokens[2]));
                    break;
                case "vn": // vertex normal
                case "vp": // parameter space vertices
                case "o": // Object name
                case "s": // Smooth shading
                case "usemtl": // Use material
                case "mtllib": // Open .mtl file
                    break;
                case "f": // face
                    int[] f = new int[6];
                    for (int i = 0; i < 3; i++) {
                        String[] splited = tokens[i + 1].split("/");
                        f[i * 2] = Integer.parseInt(splited[0]) - 1;
                        if (splited.length > 1) {
                            f[i * 2 + 1] = Integer.parseInt(splited[1]) - 1;
                        } else {
                            f[i * 2 + 1] = 0;
                        }
                    }
                    faces.add(new Face(f));
                    break;
                default:
                    System.err.println(tokens[0] + " unknown");
            }
        }

        TriangleMesh tm = new TriangleMesh();
        float[] points = new float[vertices.size() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            points[i * 3] = (float) vertices.get(i).getX();
            points[i * 3 + 1] = (float) vertices.get(i).getY();
            points[i * 3 + 2] = (float) vertices.get(i).getZ();
        }
        tm.getPoints().addAll(points);
        for (Double d: verticeTexture) {
            tm.getTexCoords().addAll(d.floatValue());
        }
        if (verticeTexture.size() == 0) {
            tm.getTexCoords().addAll(0, 1);
        }
        for (Face face: faces) {
            tm.getFaces().addAll(face.vertices);
        }
        SceneModel.addMesh(tm, file.getPath(), vertices, faces);
    }
}
