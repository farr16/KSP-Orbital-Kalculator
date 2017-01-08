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
 * EjectDisplayFragment
 * Created by Matthew on 1/6/2017.
 *
 * Defines the eject angle display interface for the application.
 *
 * Displays the origin body, a spaceship, and the angle between the spaceship's position and the
 * origin's prograde or retrograde vector when performing the burn for the transfer maneuver,
 */
public class EjectDisplayFragment extends Fragment implements MVC_Main.ViewOps ,
        MVC_Main.EjectViewOps {

    private String text;
    private TextView ejectTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View ejectView = inflater.inflate(R.layout.fragment_eject_display, container, false);

        if (text == null)
            text = getString(R.string.eject_label);

        ejectTextView = (TextView) ejectView.findViewById(R.id.EjectTestDisplay);
        ejectTextView.setText(text);

        return ejectView;
    }

    @Override
    public void resetDisplay() {
        text = getString(R.string.eject_label);
    }

    @Override
    public void updateEjectDisplay(Body orig, float eject) {
        text = "Origin: " + orig.name +  "\nEject: " + eject + "°";
    }
}
