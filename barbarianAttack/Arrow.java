package jrJava.barbarianAttack;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Arrow {

	private static Image eImage;
	private static int eWidth, eHeight;
	private static double gravity = -0.3;
	private double x, y, vx, vy, length;
	private boolean collided;
	
	static {
		eImage = new ImageIcon(Coordinator.PATH + "explosion.png").getImage();
		eWidth = eImage.getWidth(null);
		eHeight = eImage.getHeight(null);
	}
	
	public Arrow(double x, double y, double vx, double vy, double length){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.length = length;
	}
	
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}



	public void move(BufferedImage tImage){
		for(int i=0; i<10; i++){
			x += vx/10;
			y += vy/10;
			vy -= gravity/10;
			
			// check collisions.
			if(x>0 && y>0 && y<650){
				int color = tImage.getRGB((int)x, (int)y);
				int alpha = color>>>24;
				if(alpha!=0){
					collided = true;
					BarbarianManager.remove(this);
					break;
				}
			}
		}
	}
	
	
	public void draw(Graphics2D g){
		g.setColor(Color.BLACK);
		double hyp = Math.sqrt(vx*vx + vy*vy);
		g.drawLine((int)x, (int)y, (int)(x-length*vx/hyp), (int)(y-length*vy/hyp));
		
		if(collided){
			g.drawImage(eImage, (int)x-eWidth/2, (int)y-eHeight/2, null);
			ArrowManager.remove(this); 
		}
	}
	
}












