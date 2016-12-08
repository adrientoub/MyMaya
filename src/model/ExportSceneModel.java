package model;

import javafx.scene.Camera;

import java.io.*;

/**
 * Created by Adrien on 08/12/2016.
 */
public class ExportSceneModel {
    public static void exportScene(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("screen 800 600");
            bw.newLine();
            Camera c = SceneModel.getCamera();
            bw.write("camera " + c.getTranslateX() + " " + c.getTranslateY() + " " + c.getTranslateZ() + " 1 0 0 0 -1 0");
            bw.newLine();
            bw.write("dlight 0 1 1 255 255 255");
            bw.newLine();
            bw.write("dlight 0 1 -1 255 255 255");
            bw.newLine();
            bw.write("dlight 0 -1 1 255 255 255");
            bw.newLine();
            bw.write("alight 255 255 255");
            bw.newLine();
            for (Shape shape: SceneModel.getShapes()) {
                bw.write(shape.toString());
                bw.newLine();
            }
            bw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.err.println("Impossible to export to " + filename);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
