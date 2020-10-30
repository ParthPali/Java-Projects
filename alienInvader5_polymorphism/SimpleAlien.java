package jrJava.alienInvader5_polymorphism;

import java.awt.Graphics;
import java.awt.Image;

public class SimpleAlien extends Alien {

	public SimpleAlien(int x, int y, int vx, int vy, Image image, Image imageAlt, Ship target){
		super(x, y, vx, vy, image, imageAlt, target); 
	}
	
	
	public void move(){ 
		x += vx;
		y += vy;
		
		if(y>720) AlienManager.remove(this); 
		
		if(x<0+width/2 || x>800-width/2) vx *= -1;
		
		shootMissile();
	
		isCollided = target.isHit(this);
	}
	

	
	public void shootMissile(){
		if(Math.random()<0.1 && MissileManager.seatAvailable()){
			MissileManager.add( new Missile(x, y + Missile.getHeight(), 2*vy, target) );
		}
	}
	
	
	
	public void draw(Graphics canvas){
		
		drawCount++;
		
		if(drawCount%drawAltCycle<drawAltCycle/2) canvas.drawImage(image, x-width/2, y-height, null);
		else canvas.drawImage(imageAlt, x-width/2, y-height, null);
		
		if(isCollided){
			canvas.setColor(explosionColor);
			canvas.drawOval(x-explosionRadius,  y-explosionRadius, 2*explosionRadius, 2*explosionRadius);
			Coordinator.gameOn = false;
		}
	}
	
	
}
