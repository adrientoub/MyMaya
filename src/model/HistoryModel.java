package model;

import view.AttributesView;

/**
 * Created by Adrien on 15/12/2016.
 */
public class HistoryModel {
    public static void addTranslation(double x, double y, double z) {
        AttributesView.getInstance().addLine("Translate " + x + " " + y + " " + z);
    }

    public static void addScale(double scale) {
        AttributesView.getInstance().addLine("Scale " + scale);
    }
}
