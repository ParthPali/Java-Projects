package jrJava.barbarianAttack;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FancyDrawingBoard extends JPanel {

	private JFrame frame;
	private BufferedImage bImage, tImage;
	private Graphics bufferG, transG;
	private Image bgImage;
	
	public FancyDrawingBoard(int x, int y, int w, int h){
		frame = new JFrame();
		frame.setTitle("Fancy Drawing Board");
		frame.setBounds(x, y, w, h);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setPreferredSize(new Dimension(w, h));
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		
		bImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		bufferG = bImage.getGraphics(); // any thread can access this.
	
		tImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		transG = tImage.getGraphics(); // any thread can access this.
		
		((Graphics2D)bufferG).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)transG).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		bgImage = new ImageIcon(Coordinator.PATH + "bg2.png").getImage();
	}
	
	
	public void addKeyListener(KeyListener listener){ frame.addKeyListener(listener); }
	public void addMouseListener(MouseListener listener){ super.addMouseListener(listener); }
	public void addMouseMotionListener(MouseMotionListener listener){ super.addMouseMotionListener(listener); }
	
	public void removeKeyListener(KeyListener listener){ frame.removeKeyListener(listener); }
	public void removeMouseListener(MouseListener listener){ super.removeMouseListener(listener); }
	public void removeMouseMotionListener(MouseMotionListener listener){ super.removeMouseMotionListener(listener); }
	
	
	public Graphics getBufferedGraphics(){ return bufferG; }
	public Graphics getTransGraphics(){ return transG; }
	
	public BufferedImage getTImage() { return tImage; }
	
	
	public void clear(){ // clear the bImage
		bufferG.drawImage(bgImage, 0,  0, null);
		/*
		int i, j;
		for(i=0; i<this.getWidth(); i++){
			for(j=0; j<this.getHeight(); j++){
				bImage.setRGB(i, j, 0xffffffff);
			} 
		}
		*/
	}
	
	public void clearTrans(){ // clear the bImage
		int i, j;
		for(i=0; i<this.getWidth(); i++){
			for(j=0; j<this.getHeight(); j++){
				tImage.setRGB(i, j, 0x00000000);
			} 
		}
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(bImage, 0, 0, this);
		g.drawImage(tImage, 0, 0, this);
	}
	
}









