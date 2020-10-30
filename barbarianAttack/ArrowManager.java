package jrJava.barbarianAttack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class ArrowManager {

	private static LinkedList<Arrow> arrows;
	
	static {
		arrows = new LinkedList<Arrow>();
	}
	
	public static void add(Arrow arrow){
		arrows.add(arrow); 
	}
	
	public static void move(BufferedImage tImage){
		Iterator<Arrow> iter = arrows.Iterator();
		Arrow each;
		while(iter.hasNext()){
			each = iter.next();
			each.move(tImage);
			// if gone out of boundary, remove it.
			if(each.getX()<0 || each.getY()>650) iter.remove();
		}
	}
	
	public static void draw(Graphics2D g){
		Iterator<Arrow> iter = arrows.Iterator();
		while(iter.hasNext()){
			iter.next().draw(g);
		}
	}
	
	public static void remove(Arrow arrow){
		Iterator<Arrow> iter = arrows.Iterator();
		while(iter.hasNext()){
			if(iter.next().equals(arrow)){
				iter.remove();
				return;
			}
		}
	}
	
}





