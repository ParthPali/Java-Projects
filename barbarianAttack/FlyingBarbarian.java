package jrJava.barbarianAttack;

import java.awt.Image;

public class FlyingBarbarian extends Barbarian {

	protected int vy;
	protected int upperLimit, lowerLimit;
	
	public FlyingBarbarian(Image[] images, int x, int y, int vx, int flySpan) {
		super(images, x, y, vx);
		upperLimit = y + flySpan;
		lowerLimit = y - flySpan;
		vy = (int)(2 + Math.random()*3);
	}
	
	public void move(){
		x += vx;
		// game over condition.
		if(x > Coordinator.GAME_OVER_X) Coordinator.gameOver = true;
		y += vy;
		if(y>upperLimit || y<lowerLimit) vy = -vy;
	}
	
}






