package model;

import java.io.*;

/**
 * Created by Adrien on 08/12/2016.
 */
public class ExportSceneModel {
    public static void exportScene(String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("screen 800 600");
            bw.newLine();
            Camera c = SceneModel.getCamera();
            bw.write(c.toString());
            bw.newLine();
            for (Light light: SceneModel.getLights()) {
                bw.write(light.toString());
                bw.newLine();
            }
            for (Shape shape: SceneModel.getShapes()) {
                bw.write(shape.toString());
                bw.newLine();
            }
            bw.close();
            System.out.println("Saved in " + filename);
        } catch (FileNotFoundException e) {
            System.err.println("Impossible to export to " + filename);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
