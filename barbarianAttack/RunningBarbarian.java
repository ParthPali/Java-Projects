package jrJava.barbarianAttack;

import java.awt.Image;

public class RunningBarbarian extends Barbarian {

	public RunningBarbarian(Image[] images, int x, int y, int vx){
		super(images, x, y, vx);
	}
	
	public void move(){
		x += vx;
		// game over condition
		if(x > Coordinator.GAME_OVER_X) Coordinator.gameOver = true;
	}
	
}
