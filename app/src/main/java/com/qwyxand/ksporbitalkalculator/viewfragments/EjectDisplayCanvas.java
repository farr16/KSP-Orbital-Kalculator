package com.qwyxand.ksporbitalkalculator.viewfragments;

import android.view.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.qwyxand.ksporbitalkalculator.Body;
import com.qwyxand.ksporbitalkalculator.R;

/**
 * EjectDisplayCanvas
 * Created by Matthew on 1/9/2017.
 *
 * Canvas which draws the origin body, a spaceship, and the angle between the spaceship's position and
 * the origin's prograde or retrograde vector when performing the burn for the transfer maneuver,
 */
public class EjectDisplayCanvas extends View {

    private Body origin;
    private float ejectionAngle;
    private boolean inner;

    private float y;
    private float x;
    private float minDim;
    private float pathWidth;

    private Paint orbitPaint;
    private Paint originPaint;
    private Paint originLabelPaint;
    private Paint labelPaint;
    private Paint angleDisplayPaint;
    private Paint arrowPaint;
    private Paint shipPaint;

    private RectF bounds;

    private Path trianglePath;

    public EjectDisplayCanvas(Context c, AttributeSet attrs) {
        super(c, attrs);

        bounds = new RectF (0f, 0f, 0f, 0f);

        trianglePath = new Path();

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

        // If one of the values required to draw the view isn't set up, return without drawing
        if (origin == null || ejectionAngle == Float.NaN) {
            canvas.drawText("Ejection angle has not been calculated", x, y, labelPaint);
            return;
        }

        float angleRad = minDim/2;

        /* This float stores the angle (in degrees) used when drawing the angle to the screen, as
         * opposed to the variable ejectionAngle which stores angle from the origin's prograde or
         * retrograde  */
        float ejectDisplayAngle;

        float textAngleOffset;

        if (inner) {
            ejectDisplayAngle = ejectionAngle-90;
            textAngleOffset = 270 + ejectionAngle/2;
            //Draw first line in arc display in direction of origin's prograde vector
            canvas.drawLine(x, y-angleRad, x, y, angleDisplayPaint);
        }
        else {
            ejectDisplayAngle = ejectionAngle+90;
            textAngleOffset = 90 + ejectionAngle/2;
            //draw first line in arc display in direction of origin's retrograde vector
            canvas.drawLine(x, y+angleRad, x, y, angleDisplayPaint);
        }
        float endPointX = x + (float) Math.cos(Math.toRadians(ejectDisplayAngle)) * angleRad;
        float endPointY = y + (float) Math.sin(Math.toRadians(ejectDisplayAngle)) * angleRad;
        canvas.drawLine(x, y, endPointX, endPointY, angleDisplayPaint);

        angleRad *= 0.66f;
        bounds.set(x-angleRad, y-angleRad, x+angleRad, y+angleRad);
        canvas.drawArc(bounds, ejectDisplayAngle, -ejectionAngle, false, angleDisplayPaint);

        // Draw display of the ejection angle in degrees
        angleRad *= 1.3f;
        float textX = x + (float) Math.cos(Math.toRadians(textAngleOffset)) * angleRad;
        float textY = y + (float) Math.sin(Math.toRadians(textAngleOffset)) * angleRad;
        canvas.drawText(ejectionAngle + "°", textX, textY, labelPaint);

        // Draw circle representing the origin body
        float originRad = minDim/8;
        originPaint.setColor(origin.color);
        originPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, originRad, originPaint);

        canvas.drawText(origin.name, x, y, originLabelPaint);

        // Draw circle representing player ship's orbit around the origin body
        float orbitRad = originRad * 1.75f;
        canvas.drawCircle(x,y,orbitRad, orbitPaint);

        // Draw triangle representing the player's craft in the view

        // Calculate the size of the triangle so it appears the same size on different screen
        // densities
        float triSize = getResources().getInteger(R.integer.triangle_width);
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, triSize,
                getResources().getDisplayMetrics());
        triSize = pixelAmount;

        float triCenX = (float) (x + Math.cos(Math.toRadians(ejectDisplayAngle)) * orbitRad);
        float triCenY = (float) (y +Math.sin(Math.toRadians(ejectDisplayAngle)) * orbitRad);
        drawRotatedTriangle(canvas, triCenX, triCenY, triSize, ejectDisplayAngle, shipPaint);

        // Draw arrow showing the direction of the ship's orbit around the origin body
        float arcRadius = orbitRad * 1.25f;
        bounds.set(x-arcRadius, y-arcRadius, x+arcRadius, y+arcRadius);
        float coverage = -30f;
        // Draw arrow arc
        arrowPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(bounds, ejectDisplayAngle - coverage/2, coverage, false, arrowPaint);
        triCenX = (float) (x + arcRadius*Math.cos(Math.toRadians(ejectDisplayAngle + coverage/2)));
        triCenY = (float) (y + arcRadius*Math.sin(Math.toRadians(ejectDisplayAngle + coverage/2)));
        triSize *= 0.66f;
        // Draw arrow head
        arrowPaint.setStyle(Paint.Style.FILL);
        drawRotatedTriangle(canvas, triCenX, triCenY, triSize, ejectDisplayAngle+coverage/2, arrowPaint);
    }

    /**
     * initPaints
     *
     * Helper method which initializes all the Paint objects used in canvas draw calls
     * @param context
     */
    private void initPaints(Context context) {

        // Calculate the width of the orbit path elements so they appear the same size on different
        // screen densities
        pathWidth = getResources().getInteger(R.integer.path_width);
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pathWidth,
                getResources().getDisplayMetrics());
        pathWidth = pixelAmount;

        // Calculate the text size so it appears the same size on different screen densities
        float textSize = getResources().getInteger(R.integer.text_size);
        pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSize,
                getResources().getDisplayMetrics());
        textSize = pixelAmount;

        orbitPaint = new Paint();
        orbitPaint.setAntiAlias(true);
        orbitPaint.setColor(ContextCompat.getColor(context, R.color.colorOrbitCircles));
        orbitPaint.setStyle(Paint.Style.STROKE);
        orbitPaint.setStrokeWidth(pathWidth);

        arrowPaint = new Paint();
        arrowPaint.setAntiAlias(true);
        arrowPaint.setColor(ContextCompat.getColor(context, R.color.colorArrowDisplay));
        arrowPaint.setStrokeWidth(pathWidth);

        shipPaint = new Paint();
        shipPaint.setAntiAlias(true);
        shipPaint.setColor(ContextCompat.getColor(context, R.color.colorShipDisplay));

        originPaint = new Paint();
        originPaint.setAntiAlias(true);

        originLabelPaint = new Paint();
        originLabelPaint.setColor(Color.WHITE);
        originLabelPaint.setTextAlign(Paint.Align.CENTER);
        originLabelPaint.setTextSize(textSize);

        labelPaint = new Paint();
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(textSize);

        angleDisplayPaint = new Paint();
        angleDisplayPaint.setAntiAlias(true);
        angleDisplayPaint.setColor(ContextCompat.getColor(context, R.color.colorAngleLines));
        angleDisplayPaint.setStyle(Paint.Style.STROKE);
        angleDisplayPaint.setStrokeWidth(pathWidth);
    }

    /**
     * drawRotatedTriangle
     *
     * Helper method for drawing a rotated triangle, used to draw the triangle representing the player
     * ship and the head of the arrow representing the direction of the ship's orbit
     *
     * @param c canvas on which the triangle is drawn
     * @param x x coordinate of the center-point of the triangle
     * @param y y coordinate of the center-point of the triangle
     * @param size half the height of the triangle
     * @param angle angle the triangle should be rotated by
     * @param paint paint used to draw the triangle, must be a fill-styled paint
     */
    private void drawRotatedTriangle(Canvas c, float x, float y, float size, float angle, Paint paint) {
        trianglePath.reset();

        trianglePath.moveTo(x, y - size/2);
        trianglePath.lineTo(x - size/2, y + size/2);
        trianglePath.lineTo(x + size/2, y + size/2);
        c.rotate(angle, x, y);
        c.drawPath(trianglePath, paint);
        c.rotate(-angle, x, y);
    }

    public void setOrigin(Body orig) {
        origin = orig;
    }

    public void setEjectionAngle(float eject) {
        ejectionAngle = eject;
    }

    public void setInner(boolean in) {
        inner = in;
    }

}
