package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private Body origin = null;
    private float ejectionAngle = Float.NaN;
    private boolean inner = false;

    private EjectDisplayCanvas ejectDisplayCanvas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View ejectView = inflater.inflate(R.layout.fragment_eject_display, container, false);
        ejectDisplayCanvas = (EjectDisplayCanvas) ejectView.findViewById(R.id.EjectionAngleDisplayCanvas);
        ejectDisplayCanvas.setOrigin(origin);
        ejectDisplayCanvas.setEjectionAngle(ejectionAngle);
        ejectDisplayCanvas.setInner(inner);

        return ejectView;
    }

    @Override
    /**
     * resetDisplay
     *
     * Callback method used by MainActivity to refresh the display
     */
    public void resetDisplay() {
        origin = null;
        ejectionAngle = Float.NaN;
        inner = false;
        if (ejectDisplayCanvas != null) {
            ejectDisplayCanvas.setOrigin(origin);
            ejectDisplayCanvas.setEjectionAngle(ejectionAngle);
            ejectDisplayCanvas.setInner(inner);
            ejectDisplayCanvas.invalidate();
        }
    }

    @Override
    /**
     * updateEjectDisplay
     *
     * Callback method used by MainActivity to pass information from the calculator model
     */
    public void updateEjectDisplay(Body orig, float eject, boolean in) {
        origin = orig;
        ejectionAngle = eject;
        inner = in;
        if (ejectDisplayCanvas != null) {
            ejectDisplayCanvas.setOrigin(origin);
            ejectDisplayCanvas.setEjectionAngle(ejectionAngle);
            ejectDisplayCanvas.setInner(inner);
        }
    }
}
