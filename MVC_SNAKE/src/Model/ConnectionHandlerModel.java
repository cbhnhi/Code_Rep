package Model;

import java.awt.Point;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandlerModel {

	public Socket socket;
	public DataInputStream inputStream;
	public DataOutputStream outputStream;

//	private String line;
	
	public ConnectionHandlerModel(Socket s) throws IOException {
		// TODO Auto-generated constructor stub
		socket=s;
		socket.setSoTimeout(800);
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream= new DataOutputStream(socket.getOutputStream());
	
		//outputStream.println("connected To Server");
	//	line = inputStream.readLine();
	//	System.out.println(line);
	}
	
	public GiantWorm receiveSnake()
	{
		int num;
		GiantWorm tmp= new GiantWorm();
		try {
			ArrayList<Point> list=new ArrayList<Point>();
			num=inputStream.readInt();//num of members in array
			for(int i = 0;i<num;i++)
			{
				int x = inputStream.readInt();
				int y = inputStream.readInt();
				list.add(new Point(x,y));
			}
			tmp.snakeParts=list;
			tmp.tailLength=num;
			tmp.direction=inputStream.readInt();
			int x =inputStream.readInt();
			int y = inputStream.readInt();
			tmp.head=new Point(x,y);
			tmp.dead=inputStream.readInt();
			tmp.score=inputStream.readInt();
			x=inputStream.readInt();
			y=inputStream.readInt();
			tmp.apple=new Point(x,y);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ModelReceiveSnake");
			tmp =null;
		}
		return tmp;
	}
	
	public void SendSnake(GiantWorm tmp) 
	{
		try {
			outputStream.writeInt(tmp.tailLength);
			for(int i = 0;i<tmp.tailLength;i++)
			{
				Point tmp2=tmp.snakeParts.get(i);
				outputStream.writeInt(tmp2.x);
				outputStream.writeInt(tmp2.y);
			}
			outputStream.writeInt(tmp.direction);
			outputStream.writeInt(tmp.head.x);
			outputStream.writeInt(tmp.head.y);
			outputStream.writeInt(tmp.dead);
			outputStream.writeInt(tmp.score);
			outputStream.writeInt(tmp.apple.x);
			outputStream.writeInt(tmp.apple.y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ModelSendSnake");
		}
	//	line="";
	}
}
