package com.accenture.swarmPSO.bean;

/**
 * Can represent a position as well as a velocity.
 */
public class Vector {

    private double x, y;
    private double limit = Double.MAX_VALUE;

    public Vector () {
        this(0, 0);
    }

    public Vector (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public void set (double x, double y) {
        setX(x);
        setY(y);
    }

    public void setX (double x) {
        this.x = x;
    }

    public void setY (double y) {
        this.y = y;
    }

    void add (Vector v) {
        x += v.x;
        y += v.y;
        limit();
    }

    void sub (Vector v) {
        x -= v.x;
        y -= v.y;
        limit();
    }

    void mul (double s) {
        x *= s;
        y *= s;
        limit();
    }

    void div (double s) {
        x /= s;
        y /= s;
        limit();
    }

    void normalize () {
        double m = mag();
        if (m > 0) {
            x /= m;
            y /= m;
        }
    }

    private double mag () {
        return Math.sqrt(x*x + y*y);
    }

    void limit (double l) {
        limit = l;
        limit();
    }

    private void limit () {
        double m = mag();
        if (m > limit) {
            double ratio = m / limit;
            x /= ratio;
            y /= ratio;
        }
    }

    public Vector clone () {
        return new Vector(x, y);
    }

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", limit=" + limit + "]";
	}
}
