package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.qwyxand.ksporbitalkalculator.Body;

/**
 * Created by Matthew on 1/9/2017.
 */

public class PhaseDisplayCanvas extends View {

    private Body origin;
    private Body destination;
    private float phaseAngle;

    public PhaseDisplayCanvas(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public void setOrigin(Body orig) {
        origin = orig;
    }

    public void setDestination(Body dest) {
        destination = dest;
    }

    public void setPhaseAngle(float phase) {
        phaseAngle = phase;
    }
}
