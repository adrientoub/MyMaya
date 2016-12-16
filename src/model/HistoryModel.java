package model;

import view.AttributesView;

/**
 * Created by Adrien on 15/12/2016.
 */
public class HistoryModel {
    public static void addTranslation(double x, double y, double z, String name) {
        AttributesView.getInstance().addLine("Translate \"" + name + "\" " + x + " " + y + " " + z);
    }

    public static void addScale(double scale, String name) {
        AttributesView.getInstance().addLine("Scale \"" + name + "\" " + scale);
    }
}
