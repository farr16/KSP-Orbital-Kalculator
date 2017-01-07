package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class PhaseDisplayFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View phaseView = inflater.inflate(R.layout.fragment_phase_display, container, false);
        return phaseView;
    }
}
