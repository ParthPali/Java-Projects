package jrJava.maze_3_withMaze;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	private int serverPortNumber = 5454;
	private InputStream is;
	private OutputStream os;
	
	public InputStream getInputStream() { return is; }
	public OutputStream getOutputStream() { return os; }
	
	
	public void connect(){
		try {
			ServerSocket ss = new ServerSocket(serverPortNumber);
			Socket socket = ss.accept();
			is = socket.getInputStream();
			os = socket.getOutputStream();
			
		} catch (Exception e) { }
	}
	

	public static void main(String[] args) {
		Server server = new Server();
		server.connect();
		
		MessageSender sender = new MessageSender(server.getOutputStream());
		MessageReceiver receiver = new MessageReceiver(server.getInputStream());
		Thread t = new Thread(receiver);
		t.start();
		
		Mouse myMouse =  new Mouse(true, 120, 250, 3, 0, 1);
		Mouse enemyMouse = new Mouse(false, 180, 370, 3, -1, 0);
		
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
			} catch (InterruptedException e) { }
		}
	}
}
