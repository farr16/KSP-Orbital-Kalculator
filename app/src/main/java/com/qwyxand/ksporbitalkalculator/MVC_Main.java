package com.qwyxand.ksporbitalkalculator;

/**
 * MVC_Main
 * Created by Matthew on 1/8/2017.
 *
 * Holds interfaces which define actions used between the main activity, the view fragments, and
 * the orbital model.
 */
public interface MVC_Main {

    /**
     * Defines functionality implemented by all view fragments to be called by the main activity.
     */
    interface ViewOps {
        void resetDisplay();
    }

    /**
     * Defines functionality implemented calculator fragment to be called by the main activity.
     */
    interface CalculatorViewOps {
        void updateCalculatorDisplays(float phase, float eject, float ejectV, float deltaV);
    }

    /**
     * Defines functionality implemented phase display fragment to be called by the main activity.
     */
    interface PhaseViewOps {
        void updatePhaseDisplay(Body orig, Body dest, float phase);
    }

    /**
     * Defines functionality implemented by eject display fragment to be called by the main activity.
     */
    interface EjectViewOps {
        void updateEjectDisplay(Body orig, float eject, boolean inner);
    }

    /**
     * Defines functionality implemented by the main activity to be called by the calculator fragment.
     */
    interface ControllerOps {
        String[] getBodyNamesList();
        void calculateButtonPressed(int origIdx, int destIdx, int park);
        void resetButtonPressed();
    }

    /**
     * Defines functionality implemented by the orbital model to be called by the main activity.
     */
    interface OrbitalModelOps {
        void resetModel();
        boolean isModelEmpty();
        String[] getBodyNamesList();
        void calculate(int origIdx, int destIdx, int park);
        Body getOrigin();
        Body getDestination();
        float getPhaseAngle();
        float getEjectionAngle();
        float getEjectionVelocity();
        float getDeltaV();
    }
}
