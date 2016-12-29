package model;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Adrien on 28/12/2016.
 */
public class ConverterModel {
    public static WritableImage errorMessage(String errorMessage) {
        System.err.println("Error: " + errorMessage);
        return null;
    }

    public static WritableImage openPPM(String path) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        Scanner scanner = new Scanner(fis);
        String str = scanner.next();
        if (!str.equals("P3")) {
            return errorMessage("wrong PPM format");
        }
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        double maxRGB = scanner.nextInt();
        if (maxRGB < 0 || width < 0 || height < 0) {
            return errorMessage("wrong PPM format");
        }
        WritableImage image = new WritableImage(width, height);
        PixelWriter px = image.getPixelWriter();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int red = scanner.nextInt();
                int green = scanner.nextInt();
                int blue = scanner.nextInt();
                px.setColor(j, i, Color.color(red / maxRGB, green / maxRGB, blue / maxRGB));
            }
        }
        return image;
    }
}
