package model;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Adrien on 30/12/2016.
 */
public class ImportSceneModel {
    private final static Pattern quotedString = Pattern.compile("\"(.+)\"");

    private static Color readColor(Scanner scanner) {
        int red;
        int green;
        int blue;
        if (scanner.hasNextInt()) {
            red = scanner.nextInt();
        } else {
            return null;
        }
        if (scanner.hasNextInt()) {
            green = scanner.nextInt();
        } else {
            return null;
        }
        if (scanner.hasNextInt()) {
            blue = scanner.nextInt();
        } else {
            return null;
        }
        return Color.color(red / 255., green / 255., blue / 255.);
    }

    private static Attributes readAttributes(Scanner scanner) {
        Attributes attributes = new Attributes();
        if (scanner.hasNextDouble()) {
            attributes.setDiff(scanner.nextDouble());
        }
        if (scanner.hasNextDouble()) {
            attributes.setRefl(scanner.nextDouble());
        }
        if (scanner.hasNextDouble()) {
            attributes.setSpec(scanner.nextDouble());
        }
        if (scanner.hasNextDouble()) {
            attributes.setShin(scanner.nextDouble());
        }
        if (scanner.hasNextDouble()) {
            attributes.setRefr(scanner.nextDouble());
        }
        if (scanner.hasNextDouble()) {
            attributes.setOpac(scanner.nextDouble());
        } else {
            return null;
        }
        return attributes;
    }

    private static Double readDouble(Scanner scanner) {
        if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        }
        return null;
    }

    private static String readQuotedString(Scanner scanner) {
        if (scanner.hasNext(quotedString)) {
            String str = scanner.next(quotedString);
            return str.substring(1, str.length() - 1);
        }
        return null;
    }

    private static Point3D readPosition(Scanner scanner) {
        Double[] position = new Double[3];
        if (scanner.hasNextDouble()) {
            position[0] = scanner.nextDouble();
        }
        if (scanner.hasNextDouble()) {
            position[1] = scanner.nextDouble();
        }
        if (scanner.hasNextDouble()) {
            position[2] = scanner.nextDouble();
        } else {
            return null;
        }
        return new Point3D(position[0], position[1], position[2]);
    }

    private static void readSphere(Scanner scanner) {
        String name = readQuotedString(scanner);
        Double radius = readDouble(scanner);
        Point3D position = readPosition(scanner);
        Attributes attr = readAttributes(scanner);
        Color c = readColor(scanner);
        if (name == null || radius == null || position == null || attr == null || c == null) {
            return;
        }
        System.out.println("sphere \"" + name + "\" " + radius + " " + position + " " + attr + " " + c);
        SceneModel.addSphere(radius, c, position, name, attr);
    }

    private static void readCamera(Scanner scanner) {
        // TODO: set rotation angles from u, v
        Point3D pos = readPosition(scanner);
        if (pos == null) {
            return;
        }
        Point3D u = readPosition(scanner);
        Point3D v = readPosition(scanner);
        SceneModel.getCamera().getInnerObject().setTranslateX(pos.getX());
        SceneModel.getCamera().getInnerObject().setTranslateY(pos.getY());
        SceneModel.getCamera().getInnerObject().setTranslateZ(pos.getZ());
        System.out.println("camera " + pos + " " + u + " " + v);
    }

    private static void readScreen(Scanner scanner) {
        // TODO: save this value
        int width;
        int height;
        if (scanner.hasNextInt()) {
            width = scanner.nextInt();
        } else {
            return;
        }
        if (scanner.hasNextInt()) {
            height = scanner.nextInt();
        } else {
            return;
        }
        System.out.println("screen " + width + " " + height);
    }

    private static void readAlight(Scanner scanner) {
        Color color = readColor(scanner);
        if (color == null) {
            return;
        }
        System.out.println("alight " + color);
        SceneModel.getAmbientLight().setColor(color);
    }

    private static void readPlight(Scanner scanner) {
        String name = readQuotedString(scanner);
        Point3D pos = readPosition(scanner);
        Color color = readColor(scanner);
        if (name == null || pos == null || color == null) {
            return;
        }
        System.out.println("plight \"" + name + "\" " + pos + " " + color);
        SceneModel.addPointLight(color, pos, name);
    }

    private static void readDlight(Scanner scanner) {
        String name = readQuotedString(scanner);
        Point3D direction = readPosition(scanner);
        Color color = readColor(scanner);
        if (color == null || direction == null || name == null) {
            return;
        }
        System.out.println("dlight \"" + name + "\" " + direction + " " + color);
        SceneModel.addDirectionalLight(color, direction, name);
    }

    private static void readPlane(Scanner scanner) {
        // TODO: add planes to the scene
        String name = readQuotedString(scanner);
        for (int i = 0; i < 4; i++) {
            if (scanner.hasNextInt()) {
                scanner.nextInt();
            }
        }
        Attributes attr = readAttributes(scanner);
        System.out.println("plane \"" + name + "\" " + attr);
    }

    public static void importScene(File file) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useLocale(Locale.ENGLISH);
            SceneModel.clear();
            HistoryModel.addOpen(file.getCanonicalPath());
            while (scanner.hasNext()) {
                String str = scanner.next();
                switch (str) {
                    case "sphere":
                        readSphere(scanner);
                        break;
                    case "camera":
                        readCamera(scanner);
                        break;
                    case "screen":
                        readScreen(scanner);
                        break;
                    case "alight":
                        readAlight(scanner);
                        break;
                    case "plight":
                        readPlight(scanner);
                        break;
                    case "dlight":
                        readDlight(scanner);
                        break;
                    case "plane":
                        readPlane(scanner);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Impossible to export to " + file);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
