package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Adrien on 28/12/2016.
 */
public class ConverterModel {
    public static BufferedImage errorMessage(String errorMessage) {
        System.err.println("Error: " + errorMessage);
        return null;
    }

    public static BufferedImage openPPM(String path) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        Scanner scanner = new Scanner(fis);
        String str = scanner.next();
        if (!str.equals("P3")) {
            return errorMessage("wrong PPM format");
        }
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int maxRGB = scanner.nextInt();
        if (maxRGB != 255 || width < 0 || height < 0) {
            return errorMessage("wrong PPM format");
        }
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int red = scanner.nextInt();
                int green = scanner.nextInt();
                int blue = scanner.nextInt();
                bi.setRGB(j, i, new Color(red, green, blue).getRGB());
            }
        }
        return bi;
    }
}
