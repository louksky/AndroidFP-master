package com.shenkar.galargov.androidfp;

import android.graphics.PointF;

public class RectLogic {

    private static final float gravity = -9.8f;
    private static final float animationSpeedFactor = 5.0f;

    /**
     * http://www.softschools.com/formulas/physics/trajectory_formula/162/
     *
     * @param ret (x,y) value returns here
     */
    public void calculateBulletLocation(PointF ret, float v0, float radAngle, long t0, long t1) {
        double x = calculateXLocation(v0, radAngle, t0, t1);
        double y = v0 == 0 ? 0 :
                x * Math.tan(radAngle)
                        - (gravity * x * x) / (2 * v0 * v0 * Math.cos(radAngle) * Math.cos(radAngle));

        ret.x = (float) x;
        ret.y = (float) y;
    }

    private double calculateXLocation(float v0, double radAngle, long t0, long t1) {
        // time is in milliseconds, formula in seconds
        return Math.cos(radAngle) * v0 * animationSpeedFactor * (t1 - t0) / 1000;
    }

}
