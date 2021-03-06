package model;

import java.io.*;

/**
 * Created by Adrien on 08/12/2016.
 */

public class ExportSceneModel {
    private static String lastFilename = null;
    private static int count = 0;

    public static void exportScene(String filename) {
        if (filename != null) {
            lastFilename = filename;
            count = 0;
        } else {
            filename = lastFilename + String.format("%04d", count);
            count++;
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("screen 800 600");
            bw.newLine();
            Camera c = SceneModel.getCamera();
            bw.write(c.toString());
            bw.newLine();

            bw.write(SceneModel.getAmbientLight().toString());
            bw.newLine();

            for (Object3D object3D: SceneModel.getObject3DS()) {
                bw.write(object3D.toString());
                bw.newLine();
            }
            bw.close();
            HistoryModel.addSave(filename);
        } catch (FileNotFoundException e) {
            System.err.println("Impossible to export to " + filename);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
