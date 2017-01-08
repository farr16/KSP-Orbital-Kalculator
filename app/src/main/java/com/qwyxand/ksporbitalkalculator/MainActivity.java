package com.qwyxand.ksporbitalkalculator;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.qwyxand.ksporbitalkalculator.viewfragments.*;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


/**
 * MainActivity
 * Created by Matthew on 1/5/2017.
 *
 * Defines the main interface for the application.
 * Handles user input on the bottom bar to manage switching between the fragment interfaces.
 * Passes data between fragments and the orbital model.
 */
public class MainActivity extends AppCompatActivity implements OnTabSelectListener, MVC_Main.ControllerOps {

    private BottomBar bottomBar;
    private MVC_Main.OrbitalModelOps model;

    private CalculatorFragment calculatorFragment;
    private PhaseDisplayFragment phaseDisplayFragment;
    private EjectDisplayFragment ejectDisplayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new OrbitalModel(getApplicationContext());

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            calculatorFragment = new CalculatorFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, calculatorFragment).commit();
        }

        phaseDisplayFragment = new PhaseDisplayFragment();
        ejectDisplayFragment = new EjectDisplayFragment();

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(this);
    }

    public void calculateButtonPressed(int origIdx, int destIdx, int park) {
        model.calculate(origIdx, destIdx, park);

        if (model.isModelEmpty()) {
            // TODO add error flags
            return;
        }

        float phase = model.getPhaseAngle();
        float eject = model.getEjectionAngle();
        float ejectV = model.getEjectionVelocity();
        float deltaV = model.getDeltaV();

        calculatorFragment.updateCalculatorDisplays(phase, eject, ejectV, deltaV);

        if (phaseDisplayFragment == null)
            phaseDisplayFragment = new PhaseDisplayFragment();
        phaseDisplayFragment.updatePhaseDisplay(model.getOrigin(), model.getDestination(), phase);

        if (ejectDisplayFragment == null)
            ejectDisplayFragment = new EjectDisplayFragment();
        ejectDisplayFragment.updateEjectDisplay(model.getOrigin(), eject);
    }

    public void resetButtonPressed() {
        model.resetModel();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof MVC_Main.ViewOps) {
            ((MVC_Main.ViewOps) fragment).resetDisplay();
        }
    }

    public String[] getBodyNamesList() {
        return model.getBodyNamesList();
    }

    public void onTabSelected(@IdRes int tabId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (tabId) {
            case R.id.tab_calculator:
                if (calculatorFragment == null)
                    calculatorFragment = new CalculatorFragment();
                transaction.replace(R.id.fragment_container, calculatorFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.tab_phase:
                if (phaseDisplayFragment == null)
                    phaseDisplayFragment = new PhaseDisplayFragment();
                transaction.replace(R.id.fragment_container, phaseDisplayFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.tab_eject:
                if (ejectDisplayFragment == null)
                    ejectDisplayFragment = new EjectDisplayFragment();
                transaction.replace(R.id.fragment_container, ejectDisplayFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Don't respond to back button presses since back button navigation shouldn't be used with
        // bottom bar tabs as per the Android Material Design Specification
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        else
            return super.onKeyDown(keyCode, event);
    }
}
