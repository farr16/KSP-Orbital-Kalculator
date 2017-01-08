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

    private String text = null;
    private TextView phaseTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View phaseView = inflater.inflate(R.layout.fragment_phase_display, container, false);

        if (text == null)
            text = getString(R.string.phase_label);

        phaseTextView = (TextView) phaseView.findViewById(R.id.PhaseTestDisplay);
        phaseTextView.setText(text);

        return phaseView;
    }

    @Override
    public void resetDisplay() {
        text = getString(R.string.phase_label);
    }

    @Override
    public void updatePhaseDisplay(Body orig, Body dest, float phase) {
        text = "Origin: " + orig.name + "\nDest: " + dest.name + "\nPhase: " + phase + "°";
    }
}
