package jrJava.barbarianAttack;


public class MathUtility {

	public static void translate(Point[] points, Point ref){
		for(int i=0; i<points.length; i++){
			points[i].x += ref.x;
			points[i].y += ref.y;
		}
	}
	
	public static Point translate(Point p, double xShift, double yShift){
		return new Point(p.x + xShift, p.y + yShift);
	}
	
	public static Point scale(Point p, Point ref, double scaleFactor){
		double dx = p.x - ref.x;
		double dy = p.y - ref.y;
		return new Point(ref.x + dx*scaleFactor, ref.y + dy*scaleFactor);
	}
	
	public static Point rotate(Point p, Point ref, double angle){
		double dx = p.x - ref.x;
		double dy = p.y - ref.y;
		
		double rdx = dx*Math.cos(angle) - dy*Math.sin(angle);
		double rdy = dx*Math.sin(angle) + dy*Math.cos(angle);
		
		return new Point(rdx + ref.x, rdy + ref.y);
	}
	
	
	public static Point scaleAndRotate(Point p, Point ref, double scaleFactor, double angle){
		double dx = p.x - ref.x;
		double dy = p.y - ref.y;
		
		double rdx = dx*Math.cos(angle) - dy*Math.sin(angle);
		double rdy = dx*Math.sin(angle) + dy*Math.cos(angle);
		
		return new Point(rdx*scaleFactor + ref.x, rdy*scaleFactor + ref.y);
	}
	

}





