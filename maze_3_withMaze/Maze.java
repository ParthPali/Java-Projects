package jrJava.maze_3_withMaze;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Maze {
	
	private BufferedImage mazeImage;
	private int width, height;
	private ArrayList<Prize> prizes;
	
	
	public Maze() {
		try {
			mazeImage = ImageIO.read(new File("jrJava/maze_1/maze.png"));
		} catch (IOException e) { }
		width = mazeImage.getWidth();
		height = mazeImage.getHeight();
		
		prizes = new ArrayList<Prize>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File("jrJava/maze_3_withMaze/targets.txt"));
			while(scanner.hasNextLine()) {
				prizes.add(new Prize(scanner.nextLine()));
			}
		} catch (FileNotFoundException e) { }
	}
	
	public int checkCollisionWithPrizes(int noseX, int noseY) {
		int score;
		for(int i=0; i<prizes.size(); i++) {
			score = prizes.get(i).checkCollision(noseX, noseY);
			if(score>0) return score;
		}
		return 0;
	}
	
	public void draw(Graphics g) {
		g.drawImage(mazeImage, 0, 0, null);
		
		Prize each;
		for(int i=prizes.size()-1; i>=0; i--) {
			each = prizes.get(i);
			each.draw(g);
			
			if(each.toBeRemoved()) {
				prizes.remove(i);
			}
		}
	}
	
	public boolean hitWall(int x, int y) {
		if(getAlpha(x, y)<100) return false;
		return true;
	}
	
	public void setPixel(int x, int y, int alpha, int red, int green, int blue) {
		int pixelColor = (alpha<<24) | (red<<16) | (green<<8) | (blue<<0);
		mazeImage.setRGB(x, y, pixelColor);
	}
	
	public int getAlpha(int x, int y) {
		int pixel = mazeImage.getRGB(x, y);
		return (pixel>>24) & 0xFF;
	}
	
	public int getRed(int x, int y) { return -1; }
	public int getGreen(int x, int y) { return -1; }
	public int getBlue(int x, int y) { return -1; }
	
}
