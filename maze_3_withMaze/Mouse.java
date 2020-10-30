package jrJava.maze_3_withMaze;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import resources.SoundPlayer;

public class Mouse implements Runnable, KeyListener {

	private static Image imageN, imageS, imageE, imageW;
	private static int size;
	
	private MessageSender sender;
	private Maze maze;
	
	private int x, y; // center of mouse
	private int stepSize; 
	private int xSpeed, ySpeed;
	private Image image;
	
	private boolean shouldBreakWall;
	private boolean isMine;
	
	private SoundPlayer bgSound;
	private boolean bgSoundStopped;
	private static SoundPlayer laserSound, explosionSound;
	
	static {
		imageN = new ImageIcon("jrJava/maze_1/tinyMouseN.png").getImage();
		imageS = new ImageIcon("jrJava/maze_1/tinyMouseS.png").getImage();
		imageE = new ImageIcon("jrJava/maze_1/tinyMouseE.png").getImage();
		imageW = new ImageIcon("jrJava/maze_1/tinyMouseW.png").getImage();
		size = imageN.getWidth(null);
		
		laserSound = new SoundPlayer("jrJava/maze_1/lip.wav");
		explosionSound = new SoundPlayer("jrJava/maze_1/explosion.wav");
	}
	
	public Mouse(boolean isMine, int x, int y, int stepSize,  int xSpeed, int ySpeed) {
		this.isMine = isMine;
		this.x = x;
		this.y = y;
		this.stepSize = stepSize;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		
		if(xSpeed==1 && ySpeed==0) image = imageE;
		else if(xSpeed==-1 && ySpeed==0) image = imageW;
		else if(xSpeed==0 && ySpeed==1) image = imageS;
		else if(xSpeed==0 && ySpeed==-1) image = imageN;
		
		if(isMine) {
			bgSound = new SoundPlayer("jrJava/maze_1/bgSound.wav");
			bgSoundStopped = false;
		}
	}
	
	public void setMessageSender(MessageSender sender) {
		this.sender = sender;
	}
	
	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	public void move() {
		x += xSpeed*stepSize;
		y += ySpeed*stepSize;
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, x-size/2, y-size/2, null);
	}
	
	public boolean isCollided() {
		if(ySpeed==0) { // horizontal move
			int xProbe = x + xSpeed*size/2;
			for(int yProbe = y-size/2+4; yProbe <= y+size/2-4; yProbe+=4) {
				if(maze.hitWall(xProbe, yProbe)) return true;
			}
		}
		else if(xSpeed==0) { // vertical move
			int yProbe = y + ySpeed*size/2;
			for(int xProbe = x-size/2+4; xProbe <= x+size/2-4; xProbe+=4) {
				if(maze.hitWall(xProbe, yProbe)) return true;
			}
		}
		return false;
	}
	
	public void run() {
		while(true){
			if(!isCollided()) {
				move();
				int score = maze.checkCollisionWithPrizes
									(x+xSpeed*size/2, y+ySpeed*size/2);
				
				if(score>0) laserSound.play();
				
				if(bgSoundStopped) {
					bgSound.playLoop();
					bgSoundStopped = false;
				}
			}
			else {
				bgSound.stop();
				bgSoundStopped = true;
			}
			sendData();
			
			if(shouldBreakWall) {
				breakWall();
				shouldBreakWall = false;
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) { }
		}
	}
	
	private void breakWall() {
		int theX, theY;
		if(xSpeed==0) { //moving vertically
			for(theX=x-size/2+2; theX<x+size/2-2; theX++) {
				for(theY=y+ySpeed*(size/2-3);
									theY!=y+ySpeed*(size/2+10); theY+=ySpeed) {
					maze.setPixel(theX, theY, 0, 0, 0, 0);
				}
			}
		} else if(ySpeed==0) { // moving horizontally
			for(theY=y-size/2+2; theY<y+size/2-2; theY++) {
				for(theX=x+xSpeed*(size/2-3);
									theX!=x+xSpeed*(size/2+10); theX+=xSpeed) {
					maze.setPixel(theX, theY, 0, 0, 0, 0);
				}
			}
		}
		explosionSound.play();
	}
	
	public void sendData() {
		sender.send(x +"," + y +"," + xSpeed + "," + ySpeed + "," + shouldBreakWall);
		StringBuilder sb = new StringBuilder();
		sb.append(x);
		sb.append(",");
		sb.append(y);
		sb.append(",");
		sb.append(xSpeed);
		sb.append(",");
		sb.append(ySpeed);
		sb.append(",");
		sb.append(shouldBreakWall);
		sender.send(sb.toString());
	}
	
	public void receiveData(String data) {
		StringTokenizer st = new StringTokenizer(data, ",");
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		xSpeed = Integer.parseInt(st.nextToken());
		ySpeed = Integer.parseInt(st.nextToken());
		shouldBreakWall = Boolean.parseBoolean(st.nextToken());
		
		if(xSpeed==1 && ySpeed==0) image = imageE;
		else if(xSpeed==-1 && ySpeed==0) image = imageW;
		else if(xSpeed==0 && ySpeed==1) image = imageS;
		else if(xSpeed==0 && ySpeed==-1) image = imageN;
		
		if(shouldBreakWall) {
			breakWall();
			shouldBreakWall = false;
		}
		
		int score = maze.checkCollisionWithPrizes
							(x+xSpeed*size/2, y+ySpeed*size/2);
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode==KeyEvent.VK_UP) {
			xSpeed = 0; ySpeed = -1;
			image = imageN;
		}
		else if(keyCode==KeyEvent.VK_DOWN) {
			xSpeed = 0; ySpeed = 1;
			image = imageS;
		}
		else if(keyCode==KeyEvent.VK_LEFT) {
			xSpeed = -1; ySpeed = 0;
			image = imageW;
		}
		else if(keyCode==KeyEvent.VK_RIGHT) {
			xSpeed = 1; ySpeed = 0;
			image = imageE;
		}
		else if(keyCode==KeyEvent.VK_SPACE) {
			shouldBreakWall = true;
		}
	}
	
	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }
}
