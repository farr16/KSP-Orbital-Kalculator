package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwyxand.ksporbitalkalculator.Body;
import com.qwyxand.ksporbitalkalculator.MVC_Main;
import com.qwyxand.ksporbitalkalculator.R;

/**
 * PhaseDisplayFragment
 * Created by Matthew on 1/6/2017.
 *
 * Defines the phase angle display interface for the application.
 *
 * Displays two bodies, the origin and the destination, and the angle between them relative to the
 * orbital center.
 */
public class PhaseDisplayFragment extends Fragment implements MVC_Main.ViewOps ,
        MVC_Main.PhaseViewOps {

    private Body origin = null;
    private Body destination = null;
    private float phaseAngle = Float.NaN;

    private PhaseDisplayCanvas phaseDisplayCanvas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View phaseView = inflater.inflate(R.layout.fragment_phase_display, container, false);

        phaseDisplayCanvas = (PhaseDisplayCanvas) phaseView.findViewById(R.id.PhaseAngleDisplayCanvas);
        phaseDisplayCanvas.setOrigin(origin);
        phaseDisplayCanvas.setDestination(destination);
        phaseDisplayCanvas.setPhaseAngle(phaseAngle);

        return phaseView;
    }

    @Override
    /**
     * resetDisplay
     *
     * Callback method used by MainActivity to refresh the display
     */
    public void resetDisplay() {
        origin = null;
        destination = null;
        phaseAngle = Float.NaN;

        if (phaseDisplayCanvas != null) {
            phaseDisplayCanvas.setOrigin(origin);
            phaseDisplayCanvas.setDestination(destination);
            phaseDisplayCanvas.setPhaseAngle(phaseAngle);
            phaseDisplayCanvas.invalidate();
        }
    }

    @Override
    /**
     * updateEjectDisplay
     *
     * Callback method used by MainActivity to pass information from the calculator model
     */
    public void updatePhaseDisplay(Body orig, Body dest, float phase) {
        origin = orig;
        destination = dest;
        phaseAngle = phase;
        if (phaseDisplayCanvas != null) {
            phaseDisplayCanvas.setOrigin(origin);
            phaseDisplayCanvas.setDestination(destination);
            phaseDisplayCanvas.setPhaseAngle(phaseAngle);
        }
    }
}
