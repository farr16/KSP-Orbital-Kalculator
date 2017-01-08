package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qwyxand.ksporbitalkalculator.MVC_Main;
import com.qwyxand.ksporbitalkalculator.R;

/**
 * CalculatorFragment
 * Created by Matthew on 1/6/2017.
 *
 * Defines the calculator interface for the application.
 *
 * Allows the user to input their desired origin, destination, and parking orbit, which can be
 * passed to the main activity for maneuver calculation. Displays the numerical results of the
 * calculation.
 */

public class CalculatorFragment extends Fragment implements MVC_Main.ViewOps,
        MVC_Main.CalculatorViewOps, View.OnClickListener {

    private Spinner origin;
    private Spinner destination;
    private EditText parkingOrbitEntry;

    private TextView warningMessageDisplay;
    private TextView phaseAngleDisplay;
    private TextView ejectionAngleDisplay;
    private TextView ejectionVelocityDisplay;
    private TextView ejectionBurnDeltaVDisplay;

    private String warningText = "";
    private String phaseDegText = "";
    private String ejectDegText = "";
    private String ejectVText = "";
    private String deltaVText = "";

    private MVC_Main.ControllerOps controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View calcView = inflater.inflate(R.layout.fragment_calculator, container, false);

        String[] bodyNamesList = controller.getBodyNamesList();

        // Obtain references to the spinners
        origin = (Spinner) calcView.findViewById(R.id.OriginSelector);
        destination = (Spinner) calcView.findViewById(R.id.DestinationSelector);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, bodyNamesList);

        // Populate the spinners using the body name lists retrieved from the main activity
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        origin.setAdapter(adapter);
        destination.setAdapter(adapter);

        final Button calculateButton = (Button) calcView.findViewById(R.id.CalculateButton);
        final Button resetButton = (Button) calcView.findViewById(R.id.ResetButton);
        calculateButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        parkingOrbitEntry = (EditText) calcView.findViewById(R.id.ParkingOrbitEntry);
        warningMessageDisplay = (TextView) calcView.findViewById(R.id.WarningMessageDisplay);
        warningMessageDisplay.setText(warningText);
        phaseAngleDisplay = (TextView) calcView.findViewById(R.id.PhaseAngleDisplay);
        phaseAngleDisplay.setText(phaseDegText);
        ejectionAngleDisplay = (TextView) calcView.findViewById(R.id.EjectionAngleDisplay);
        ejectionAngleDisplay.setText(ejectDegText);
        ejectionVelocityDisplay = (TextView) calcView.findViewById(R.id.EjectionVelocityDisplay);
        ejectionVelocityDisplay.setText(ejectVText);
        ejectionBurnDeltaVDisplay = (TextView) calcView.findViewById(R.id.EjectionBurnDeltaVDisplay);
        ejectionBurnDeltaVDisplay.setText(deltaVText);

        return calcView;
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.CalculateButton:
                boolean flag = false;
                String warnings = "";
                int parkingOrbit = -1;

                // Get user inputs for the origin and destination
                int origIndex = origin.getSelectedItemPosition();
                int destIndex = destination.getSelectedItemPosition();

                if (origIndex == destIndex) {
                    warnings += getString(R.string.planet_selection_warning);
                    flag = true;
                }

                // Get user input for the parking orbit radius
                try {
                    parkingOrbit = Integer.parseInt(parkingOrbitEntry.getText().toString());
                }
                catch (NumberFormatException e) {
                    warnings += getString(R.string.parking_orbit_input_warning);
                    flag = true;
                }
                warningText = warnings;
                warningMessageDisplay.setText(warningText);
                // If planet inputs or parking orbit inputs aren't valid, don't perform calculations
                if (flag) {
                    return;
                }

                controller.calculateButtonPressed(origIndex, destIndex, parkingOrbit);
                break;

            case R.id.ResetButton:
                controller.resetButtonPressed();
                break;
        }
    }

    public void updateCalculatorDisplays(float phase, float eject, float ejectV, float deltaV) {
        phaseDegText = " " + phase + "°";
        phaseAngleDisplay.setText(phaseDegText);

        ejectDegText = " " + eject + "°";
        ejectionAngleDisplay.setText(ejectDegText);

        ejectVText = " " + ejectV + " m/s";
        ejectionVelocityDisplay.setText(ejectVText);

        deltaVText = " " + deltaV + " m/s";
        ejectionBurnDeltaVDisplay.setText(deltaVText);
    }

    public void resetDisplay() {
        parkingOrbitEntry.setText("");
        warningText = "";
        warningMessageDisplay.setText(warningText);
        phaseDegText = "";
        phaseAngleDisplay.setText(phaseDegText);
        ejectDegText = "";
        ejectionAngleDisplay.setText(ejectDegText);
        ejectVText = "";
        ejectionVelocityDisplay.setText(ejectVText);
        deltaVText = "";
        ejectionBurnDeltaVDisplay.setText(deltaVText);

    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);

        Activity activity = null;

        if (c instanceof Activity)
            activity = (Activity) c;

        try {
            controller = (MVC_Main.ControllerOps) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ControllerOps");
        }
    }
}
