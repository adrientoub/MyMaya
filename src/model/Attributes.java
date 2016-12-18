package model;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Attributes {
    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    // Diffusion coefficient 0 < diff < 1
    private double diff = 1;
    // Reflection coefficient 0 < refl < 1
    private double refl = 0.5;
    // Specular coefficient 0 < spec < 1
    private double spec = 1;
    // Shininess, used for specular light > 0
    private double shin = 50;
    // Refraction coefficient (angle) > 0
    private double refr = 1;
    // Opacity 0 < opac < 1
    private double opac = 1;

    @Override
    public String toString() {
        return diff + " " + refl + " " + spec + " " + shin + " " + refr + " " + opac;
    }

    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = clamp(diff, 0, 1);
    }

    public double getRefl() {
        return refl;
    }

    public void setRefl(double refl) {
        this.refl = clamp(refl, 0, 1);
    }

    public double getSpec() {
        return spec;
    }

    public void setSpec(double spec) {
        this.spec = clamp(spec, 0, 1);
    }

    public double getShin() {
        return shin;
    }

    public void setShin(double shin) {
        this.shin = Math.max(shin, 0.);
    }

    public double getRefr() {
        return refr;
    }

    public void setRefr(double refr) {
        this.refr = Math.max(refr, 0.);
    }

    public double getOpac() {
        return opac;
    }

    public void setOpac(double opac) {
        this.opac = clamp(opac, 0, 1);
    }
}
