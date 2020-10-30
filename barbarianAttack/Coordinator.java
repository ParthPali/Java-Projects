package jrJava.barbarianAttack;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import resources.SoundPlayer;

public class Coordinator implements KeyListener{

	public static final String PATH = "jrJava/barbarianAttack/imagesAndSounds/";
	public static boolean gameOver;
	public static final int GAME_OVER_X = 1100;
	private static FancyDrawingBoard board;
	private static Graphics2D g, gT;
	private static BufferedImage tImage;

	public static void main(String[] args) { play(); }

	public static void play() {
		board = new FancyDrawingBoard(0, 0, 1450, 800);
		g = (Graphics2D) board.getBufferedGraphics();
		gT = (Graphics2D) board.getTransGraphics();
		tImage = board.getTImage();
		BarbarianManager manager = new BarbarianManager();

		Bow bow = new Bow(1163, 523, 0.3, 45.0);
		board.addMouseListener(bow);
		board.addMouseMotionListener(bow);
		board.addKeyListener(manager);

		SoundPlayer bgSound = new SoundPlayer(PATH + "jack.wav");
		bgSound.playLoop();

		while(!gameOver){

			BarbarianManager.launch();

			BarbarianManager.move();
			ArrowManager.move(tImage);

			board.clearTrans();
			board.clear();

			bow.draw(g);
			BarbarianManager.draw(gT);
			ArrowManager.draw(g);

			board.repaint();

			try { 
				Thread.sleep(30);
			} catch (InterruptedException e) {}
		}

		bgSound.stop();
		Image gameOverImage = new ImageIcon(PATH + "bg_gameOver.png").getImage();
		Image gameOverImage2 = new ImageIcon(PATH + "bg_gameOver2.png").getImage();
		gT.drawImage(gameOverImage, 0, 0, null);
		gT.drawImage(gameOverImage2, 0, 0, null);
		board.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
