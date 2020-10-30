package jrJava.maze_3_withMaze;

import java.io.OutputStream;
import java.io.PrintWriter;

public class MessageSender {

	private PrintWriter pw;
	
	public MessageSender(OutputStream os){
		pw = new PrintWriter(os);
	}
	
	public void send(String message){
		pw.println(message);
		pw.flush();
	}
}
