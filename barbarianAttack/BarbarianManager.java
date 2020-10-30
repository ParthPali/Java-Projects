package jrJava.barbarianAttack;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import javax.swing.ImageIcon;

public class BarbarianManager implements KeyListener{

	private static LinkedList<Barbarian> barbarians;
	private static Image[][] images;
	
	
	static {
		barbarians = new LinkedList<Barbarian>();
		
		images = new Image[2][];
		images[0] = new Image[6];
		images[1] = new Image[6];
		
		images[0][0] = new ImageIcon(Coordinator.PATH + "barbarian_robot1_image1.png").getImage();
		images[0][1] = new ImageIcon(Coordinator.PATH + "barbarian_robot2_image1.png").getImage();
		images[0][2] = new ImageIcon(Coordinator.PATH + "barbarian_robot3_image1.png").getImage();
		images[0][3] = new ImageIcon(Coordinator.PATH + "barbarian_robot4_image1.png").getImage();
		images[0][4] = new ImageIcon(Coordinator.PATH +          "dragon1_image1.png").getImage();
		images[0][5] = new ImageIcon(Coordinator.PATH +          "dragon2_image1.png").getImage();
		
		images[1][0] = new ImageIcon(Coordinator.PATH + "barbarian_robot1_image2.png").getImage();
		images[1][1] = new ImageIcon(Coordinator.PATH + "barbarian_robot2_image2.png").getImage();
		images[1][2] = new ImageIcon(Coordinator.PATH + "barbarian_robot3_image2.png").getImage();
		images[1][3] = new ImageIcon(Coordinator.PATH + "barbarian_robot4_image2.png").getImage();
		images[1][4] = new ImageIcon(Coordinator.PATH +          "dragon1_image2.png").getImage();
		images[1][5] = new ImageIcon(Coordinator.PATH +          "dragon2_image2.png").getImage();
		
	}
	
	
	public static void launch(){
		if(Math.random()>0.02) return;
		
		Barbarian barbarian;
		if(Math.random()>0.5){
			int rIndex = (int)(Math.random()*4);
			barbarian = new RunningBarbarian(new Image[]{images[0][rIndex], images[1][rIndex]}, -30, 600, 2);
			barbarians.add(barbarian);
		}
		else {
			int rIndex = 4 + (int)(Math.random()*2);
			int y = 50 + (int)(Math.random()*3)*150;
			barbarian = new FlyingBarbarian(new Image[]{images[0][rIndex], images[1][rIndex]}, -30, y, 2, 30);
			barbarians.add(barbarian);
		}
		
	}
	
	
	public static void move(){
		Iterator<Barbarian> iter = barbarians.Iterator();
		while(iter.hasNext()){
			iter.next().move();
		}
	}
	
	
	public static void draw(Graphics2D g){
		Iterator<Barbarian> iter = barbarians.Iterator();
		while(iter.hasNext()){
			iter.next().draw(g);
		}
	}
	
	
	public static void remove(Arrow arrow){
		Iterator<Barbarian> iter = barbarians.Iterator();
		Barbarian each;
		while(iter.hasNext()){
			each = iter.next();
			if(each.isHit(arrow)) iter.remove();
		}
	}
	
	private static void removeAll() {
		Iterator<Barbarian> iter = barbarians.Iterator();
		while(iter.hasNext()) iter.remove();
		iter.remove();
	}
	
	public void keyPressed(KeyEvent e) {
		if(Coordinator.gameOver) {
			if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				removeAll();
				Coordinator.gameOver = false;
				Coordinator.play();
			}
		}
	}

	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }
	
}






