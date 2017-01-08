package com.qwyxand.ksporbitalkalculator;

/**
 * Body
 * Created by Matthew on 1/8/2017.
 *
 * Holds information about bodies in the Kerbol system, either planets or the star Kerbol, used in
 * orbital maneuver calculations.
 *
 * Class stores final variables, functioning like a C struct.
 *
 * name - the name of the Body
 * color - the color used for displaying the Body on the phase and eject angle displays
 * mu - the Body's standard gravitational parameter, in km^3/s^2
 * rad - the Body's radius in m
 * soi - the Body's sphere of influence in m
 * sma - the Body's semi-major axis in m
 */

public class Body {
    public final String name;
    public final int color;
    public final float mu;
    public final float rad;
    public final float soi;
    public final float sma;

    public Body (String _name, int _color, float _mu, float _rad, float _soi, float _sma) {
        name = _name;
        color = _color;
        mu = _mu;
        rad = _rad;
        soi = _soi;
        sma = _sma;
    }
}
