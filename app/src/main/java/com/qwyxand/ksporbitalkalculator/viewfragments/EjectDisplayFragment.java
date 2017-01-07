package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qwyxand.ksporbitalkalculator.R;

/**
 * EjectDisplayFragment
 * Created by Matthew on 1/6/2017.
 *
 * Defines the eject angle display interface for the application.
 *
 * Displays the origin body, a spaceship, and the angle between the spaceship's position and the
 * origin's prograde or retrograde vector when performing the burn for the transfer maneuver,
 */
public class EjectDisplayFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View ejectView = inflater.inflate(R.layout.fragment_eject_display, container, false);
        return ejectView;
    }
}
