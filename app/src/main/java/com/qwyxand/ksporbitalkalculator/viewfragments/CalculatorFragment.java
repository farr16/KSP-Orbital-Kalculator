package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class CalculatorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View calcView = inflater.inflate(R.layout.fragment_calculator, container, false);
        return calcView;
    }
}
