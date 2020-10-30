package jrJava.barbarianAttack;

import java.awt.Graphics2D;
import java.awt.Image;

import resources.SoundPlayer;

public abstract class Barbarian {

	protected int x, y;
	protected int vx;
	protected Image[] images;
	protected int width, height;
	protected int imageDrawIndex, imageDrawCycle = 10;
	private static SoundPlayer scream;
	
	static {
		scream = new SoundPlayer(Coordinator.PATH + "scream1.wav");
	}
	
	public Barbarian(Image[] images, int x, int y, int vx){
		this.images = images;
		this.x = x;
		this.y = y;
		this.vx = vx;
		
		width = images[0].getWidth(null);
		height = images[0].getHeight(null);
	}
	
	
	public abstract void move();
	
	
	public void draw(Graphics2D g){ 
		imageDrawIndex++;
		int selection = imageDrawIndex%imageDrawCycle>imageDrawCycle/2? 0:1;
		g.drawImage(images[selection], x, y, null);
	}
	
	
	public boolean isHit(Arrow arrow) {
		double arrowX = arrow.getX();
		double arrowY = arrow.getY();
		boolean isHit = arrowX>=x && arrowX<=x+width && arrowY>=y && arrowY<=y+height;
		if(isHit) scream.play();
		return isHit;
	}
	
}




