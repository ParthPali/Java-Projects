package jrJava.maze_3_withMaze;

import java.awt.Graphics;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	private String serverIPAddress = "localhost";
	private int serverPortNumber = 5454;
	private InputStream is;
	private OutputStream os;
	
	public InputStream getInputStream() { return is; }
	public OutputStream getOutputStream() { return os; }
	
	
	public void connect(){
		try {
			Socket socket = new Socket(serverIPAddress, serverPortNumber);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			
		} catch (Exception e) { }
		
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.connect();
		
		MessageSender sender = new MessageSender(client.getOutputStream());
		MessageReceiver receiver = new MessageReceiver(client.getInputStream());
		Thread t = new Thread(receiver);
		t.start();
		
		Mouse myMouse = new Mouse(true, 180, 370, 3, -1, 0);
		Mouse enemyMouse = new Mouse(false, 120, 250, 3, 0, 1);
		
		receiver.setEnemyMouse(enemyMouse);
		myMouse.setMessageSender(sender);
		
		Maze maze = new Maze();
		myMouse.setMaze(maze);
		enemyMouse.setMaze(maze);
		
		GraphicsBoard board = new GraphicsBoard(600, 600);
		Graphics g = board.getCanvas();
		board.getJFrame().addKeyListener(myMouse);
		
		Thread mouseThread = new Thread(myMouse);
		mouseThread.start();
		
		while(true) {
			board.clear();
			
			myMouse.draw(g);
			enemyMouse.draw(g);
			maze.draw(g);
			
			board.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
