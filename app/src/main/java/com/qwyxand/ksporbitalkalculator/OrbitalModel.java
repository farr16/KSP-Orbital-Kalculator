package com.qwyxand.ksporbitalkalculator;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * OrbitalModel
 * Created by Matthew on 1/8/2017.
 *
 * Stores the array of Bodies in the Kerbol system
 * Takes in data passed in by the main activity to perform orbital maneuver calculations, then passes
 * the results back to the main activity
 */

public class OrbitalModel implements MVC_Main.OrbitalModelOps {

    private Body[] bodies;
    private Context context;
    private boolean modelEmpty;
    private int origIndex;
    private int destIndex;
    private float phase;
    private float eject;
    private float ejectV;
    private float deltaV;

    public OrbitalModel(Context c) {
        context = c;
        initBodies();

        origIndex = destIndex = -1;

        resetModel();
    }

    private void initBodies() {
        bodies = new Body[8];

        bodies[0] = new Body("Moho", ContextCompat.getColor(context, R.color.colorMohoDisplay), 168.60938f, 250, 9646.630f, 5263138.304f);
        bodies[1] = new Body("Eve", ContextCompat.getColor(context, R.color.colorEveDisplay), 8171.7302f, 700, 85109.365f, 9832684.544f);
        bodies[2] = new Body("Kerbin", ContextCompat.getColor(context, R.color.colorKerbinDisplay), 3531.6f, 600, 84159.286f, 13599840.256f);
        bodies[3] = new Body("Duna", ContextCompat.getColor(context, R.color.colorDunaDisplay), 301.36321f, 320, 47921.949f, 20726155.264f);
        bodies[4] = new Body("Dres", ContextCompat.getColor(context, R.color.colorDresDisplay), 21.484489f, 138, 32832.840f, 40839358.203f);
        bodies[5] = new Body("Jool", ContextCompat.getColor(context, R.color.colorJoolDisplay), 282528.0f, 6000, 2455985.2f, 68773560.320f);
        bodies[6] = new Body("Eeloo", ContextCompat.getColor(context, R.color.colorEelooDisplay), 74.410815f, 210, 119082.94f, 90118820.000f);
        bodies[7] = new Body("Kerbol", ContextCompat.getColor(context, R.color.colorKerbolDisplay), 1172332800f, 261600, Float.POSITIVE_INFINITY, 0f);

    }

    public String[] getBodyNamesList() {
        String[] names = new String[bodies.length-1];

        for (int i=0; i<names.length; i++)
            names[i] = bodies[i].name;

        return names;
    }

    public void resetModel() {
        modelEmpty = true;
        phase = eject = ejectV = deltaV = Float.NEGATIVE_INFINITY;
    }

    public void calculate(int origIdx, int destIdx, int park) {
        origIndex = origIdx;
        destIndex = destIdx;
        Body orig = bodies[origIndex];
        Body dest = bodies[destIndex];
        Body cent = bodies[bodies.length-1];

        // Calculate the phase angle between the two planets at the start of the maneuver
        double tTransfer = Math.PI * Math.sqrt( Math.pow(orig.sma + dest.sma, 3) / (8 * cent.mu) );
        double angTravel = Math.sqrt(cent.mu/dest.sma) * tTransfer/dest.sma * 180/Math.PI;
        double d_phase = (180 - angTravel) % 360;

        // Calculate the ejection velocity
        double parkR = orig.rad + park; // Radius of parking orbit from center of origin
        // Distance from center of orbital system on point of exit from origin's sphere of influence
        float exitR = orig.sma + orig.soi;
        // Sphere of influence exit velocity
        double exitV = Math.sqrt(cent.mu/exitR) * (Math.sqrt(2*dest.sma / (exitR+dest.sma)) - 1);
        double ejectVNum = parkR * (orig.soi*exitV*exitV - 2*orig.mu) + 2*orig.soi*orig.mu;
        double ejectVDen = parkR * orig.soi;
        double d_ejectVkm = Math.sqrt(ejectVNum/ejectVDen);

        // Calculate the deltaV required for the exit burn
        double d_deltaVkm = (d_ejectVkm - Math.sqrt(orig.mu/parkR));

        // Calculate the angle for the exit burn
        double eta = d_ejectVkm * d_ejectVkm / 2 - orig.mu / parkR;
        double h = parkR * d_ejectVkm;
        double e = Math.sqrt(1 + (2*eta*h*h)/(orig.mu*orig.mu));
        double d_ejectDeg;
        if (e < 1){
            double a = -orig.mu/(2 * eta);
            double l = a * (1 - e*e);
            double nu = Math.acos((l-orig.soi) / (e*orig.soi));
            double phi = Math.atan2( (e * Math.sin(nu)), (1 + e*Math.cos(nu)));
            d_ejectDeg = (90 - (phi * 180/Math.PI) + (nu * 180/Math.PI)) % 360;
        }
        else {
            double ejectRad = Math.acos(1/e);
            d_ejectDeg = (180 - ejectRad * 180/Math.PI) % 360;
        }

        phase = new BigDecimal(d_phase).setScale(2, RoundingMode.HALF_UP).floatValue();
        eject = new BigDecimal(d_ejectDeg).setScale(2, RoundingMode.HALF_UP).floatValue();

        d_ejectVkm *= 1000;
        ejectV = new BigDecimal(d_ejectVkm).setScale(2, RoundingMode.HALF_UP).floatValue();
        d_deltaVkm *= 1000;
        deltaV = new BigDecimal(d_deltaVkm).setScale(2, RoundingMode.HALF_UP).floatValue();

        modelEmpty = false;
    }

    public Body getOrigin() {
        return bodies[origIndex];
    }

    public Body getDestination() {
        return bodies[destIndex];
    }

    public float getPhaseAngle() {
        return phase;
    }

    public float getEjectionAngle() {
        return eject;
    }

    public float getEjectionVelocity() {
        return ejectV;
    }

    public float getDeltaV() {
        return deltaV;
    }

    public boolean isModelEmpty() {
        return modelEmpty;
    }
}
