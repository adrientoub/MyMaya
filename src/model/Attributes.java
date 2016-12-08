package model;

/**
 * Created by Adrien on 08/12/2016.
 */
public class Attributes {
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
}
