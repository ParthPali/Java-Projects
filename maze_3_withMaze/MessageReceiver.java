package jrJava.maze_3_withMaze;

import java.io.InputStream;
import java.util.Scanner;

public class MessageReceiver implements Runnable {

	private Mouse enemyMouse;
	private Scanner scanner;
	
	public MessageReceiver(InputStream is){
		scanner = new Scanner(is);
	}
	
	public void setEnemyMouse(Mouse enemyMouse){
		this.enemyMouse = enemyMouse;
	}
	
	public void run(){
		String message;
		while(true){
			message = scanner.nextLine();
			enemyMouse.receiveData(message);
		}
	}
}
