package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.qwyxand.ksporbitalkalculator.Body;
import com.qwyxand.ksporbitalkalculator.R;

/**
 * PhaseDisplayCanvas
 * Created by Matthew on 1/9/2017.
 *
 * Canvas which draws two bodies, the origin and the destination, and the angle between them
 * relative to the orbital center.
 */
public class PhaseDisplayCanvas extends View {

    private Body origin;
    private Body destination;
    private float phaseAngle;

    private float y;
    private float x;
    private float minDim;

    private RectF bounds;

    private Paint orbitPaint;
    private Paint parentPaint;
    private Paint originPaint;
    private Paint destinationPaint;
    private Paint angleDisplayPaint;
    private Paint bodyLabelPaint;
    private Paint arcLabelPaint;

    public PhaseDisplayCanvas(Context c, AttributeSet attrs) {
        super(c, attrs);

        // Setup RectF for bounds on the angle display arc with default values
        bounds = new RectF(-1f, -1f, 1f, 1f);

        initPaints(c);
    }

    @Override
    /** onSizeChanged
     * Overridden to store coordinates of the center of the canvas whenever the canvas is resized,
     * and to calculate which dimension is smaller so drawn circles don't exceed the size of the
     * canvas.
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        x = w/2;
        y = h/2;
        minDim = (h>w) ? w : h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // If one of the values required to draw the view isn't set up, return without drawing
        if (origin == null || destination == null || phaseAngle == Float.NEGATIVE_INFINITY) {
            canvas.drawText("Phase angle has not been calculated", x, y , bodyLabelPaint);
            return;
        }

        float bodyRadius = getResources().getInteger(R.integer.body_radius);
        bodyRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bodyRadius,
                getResources().getDisplayMetrics());

        float outerRad = minDim/2 - bodyRadius*2;
        float innerRad;
        float minInnerRad = 4 * bodyRadius;

        float origRad;
        float destRad;

        // Determine which radius is smaller, scale that orbit's display size relative to the larger one
        if (origin.sma < destination.sma) {
            destRad = outerRad;
            origRad = origin.sma/destination.sma * destRad;
            if (origRad < minInnerRad)
                origRad = minInnerRad;
            innerRad = origRad;
        }
        else {
            origRad = outerRad;
            destRad = destination.sma/origin.sma * origRad;
            if (destRad < minInnerRad)
                destRad = minInnerRad;
            innerRad = destRad;
        }

        // Draw the origin and destination orbits
        canvas.drawCircle(x, y, destRad, orbitPaint);
        canvas.drawCircle(x, y, origRad, orbitPaint);

        originPaint.setColor(origin.color);
        destinationPaint.setColor(destination.color);

        // Calculate sin and cos for phase angle, which will be used to place the destination planet
        // And to draw the angle display
        float phaseCos = (float) Math.cos(Math.toRadians(phaseAngle));
        float phaseSin = (float) Math.sin(Math.toRadians(phaseAngle));

        // Calculate location along its orbit for the destination planet
        float destX = (x + phaseCos*destRad);
        float destY = (y - phaseSin*destRad);

        // Draw the angle display lines and the angle display arc
        canvas.drawLine(x + phaseCos*minDim/2, y - phaseSin*minDim/2, x, y, angleDisplayPaint);
        float angleRad = (innerRad < outerRad - innerRad) ? (innerRad + outerRad)/2 : innerRad/2;
        bounds.set(x-angleRad, y-angleRad , x+angleRad, y+angleRad);
        canvas.drawLine(x, y, x+minDim/2, y, angleDisplayPaint);
        canvas.drawArc(bounds, 0f, -phaseAngle, false, angleDisplayPaint);

        // Draw the parent, origin, and destination bodies
        canvas.drawCircle(x, y, bodyRadius, parentPaint);
        canvas.drawCircle(x + origRad, y, bodyRadius, originPaint);
        canvas.drawCircle(destX, destY, bodyRadius, destinationPaint);

        // Draw text labels for the origin, parent, and destination bodies
        canvas.drawText(origin.name, x+origRad, y+bodyRadius*2, bodyLabelPaint);
        canvas.drawText("Kerbol", x, y + bodyRadius*2, bodyLabelPaint);
        canvas.drawText(destination.name, destX, destY+bodyRadius*2, bodyLabelPaint);

        // Draw angle arc display text
        float angleDisplayX = (angleRad*1.1f) * (float) Math.cos(Math.toRadians(phaseAngle/2));
        float angleDisplayY = (angleRad*1.1f) * (float) Math.sin(Math.toRadians(phaseAngle/2));
        canvas.drawText(phaseAngle + "°", x +angleDisplayX,  y - angleDisplayY, arcLabelPaint);
    }

    private void initPaints(Context context) {
        float pathWidth = getResources().getInteger(R.integer.path_width);
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pathWidth,
                getResources().getDisplayMetrics());
        pathWidth = pixelAmount;

        float textSize = getResources().getInteger(R.integer.text_size);
        pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSize,
                getResources().getDisplayMetrics());
        textSize = pixelAmount;

        // Setup paint for drawing orbit circles
        orbitPaint = new Paint();
        orbitPaint.setAntiAlias(true);
        orbitPaint.setColor(ContextCompat.getColor(context, R.color.colorOrbitCircles));
        orbitPaint.setStyle(Paint.Style.STROKE);
        orbitPaint.setStrokeWidth(pathWidth);

        // Setup paint for drawing angle lines
        angleDisplayPaint = new Paint();
        angleDisplayPaint.setAntiAlias(true);
        angleDisplayPaint.setColor(ContextCompat.getColor(context, R.color.colorAngleLines));
        angleDisplayPaint.setStyle(Paint.Style.STROKE);
        angleDisplayPaint.setStrokeWidth(pathWidth);

        // Setup paint for drawing sun
        parentPaint = new Paint();
        parentPaint.setAntiAlias(true);
        parentPaint.setColor(ContextCompat.getColor(context, R.color.colorKerbolDisplay));

        // Setup paint for drawing origin and destination (without color)
        originPaint = new Paint();
        originPaint.setAntiAlias(true);
        destinationPaint = new Paint();
        destinationPaint.setAntiAlias(true);

        // Setup paint for drawing text labels
        bodyLabelPaint = new Paint();
        bodyLabelPaint.setColor(Color.BLACK);
        bodyLabelPaint.setTextAlign(Paint.Align.CENTER);
        bodyLabelPaint.setTextSize(textSize);
        arcLabelPaint = new Paint();
        arcLabelPaint.setColor(Color.BLACK);
        arcLabelPaint.setTextSize(textSize);
    }

    public void setOrigin(Body orig) {
        origin = orig;
    }

    public void setDestination(Body dest) {
        destination = dest;
    }

    public void setPhaseAngle(float phase) {
        phaseAngle = phase;
    }
}
