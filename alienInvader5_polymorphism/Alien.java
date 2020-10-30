package jrJava.alienInvader5_polymorphism;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public abstract class Alien {

	protected int x, y; // center, bottom
	protected /*static*/ int width, height;
	protected int vx, vy;
	//private static int eyeRadius;
	//private Color bodyColor, eyeColor;
	protected Image image, imageAlt;
	protected boolean isCollided;
	protected static Color explosionColor;
	protected static int explosionRadius;
	protected Ship target;
	protected int drawCount, drawAltCycle = 6;
	
	
	
	static {
		//eyeRadius = 3;
		explosionColor = Color.pink;
		explosionRadius = 120;
	}
	
	public Alien(int x, int y, int vx, int vy, Image image, Image imageAlt, Ship target){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		//this.bodyColor = bodyColor;
		//this.eyeColor = eyeColor;
		this.target = target;
		
		this.image = image;
		this.imageAlt = imageAlt;
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	
	public int getX(){ return x; }
	public int getY(){ return y; }
	public /*static*/ int getWidth(){ return width; }
	public /*static*/ int getHeight(){ return height; }
	
	
	public abstract void move();
	public abstract void shootMissile();
	public abstract void draw(Graphics canvas);
	
	
	public boolean isHit(Torpedo torpedo){
		
		if(   	torpedo.getX()>=x-width/2-Torpedo.getWidth()/2 &&
				torpedo.getX()<=x+width/2+Torpedo.getWidth()/2 &&
				torpedo.getY()>=y-height-Torpedo.getHeight()   &&
				torpedo.getY()<=y ){
			
			AlienManager.remove(this); 
			Coordinator.score += 2;
			return true;
		}
		
		return false;
	}
}






