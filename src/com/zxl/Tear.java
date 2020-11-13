package com.zxl;

/*
 * 眼泪
 * 作为滑稽发射的子弹
 */

public class Tear extends Role{
	    private static int speed = 18; //控制速度，越大越快
	    private static final int r = 10;

	    public Tear(double X, double Y, int id, GUI gui, int angle) {
	        super(X, Y, r, id, 10, gui);
	        this.angle = angle;
	        calMoveDirection();
	    }

	    protected void calMoveDirection() {
	        dx = speed * Math.cos(Math.toRadians(angle));
	        dy = speed * Math.sin(Math.toRadians(angle));
	    }


	    /*
	     * 产生眼泪
	     */
	    public static Role createNewTear(double X, double Y, int id, GUI gui, int angle) {
	        return new Tear(X, Y, id, gui, angle);
	    }

}
