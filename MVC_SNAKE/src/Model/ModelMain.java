package Model;

import java.awt.Color;
import java.io.*;
import java.net.ServerSocket;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.net.*;

public class ModelMain {
	
	public static void main(String[] args) throws IOException {
		
	 @SuppressWarnings("resource")
	final ServerSocket server=new ServerSocket(8000);
	 JFrame frame=new JFrame("Server Running Confirmation");
	 JPanel panel =new JPanel();
	 frame.setBounds(10, 10, 200, 200);
	   panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	      panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	      panel.setBackground(Color.WHITE);
	      frame.add(panel);
	   //   content.add(gameHeaderPanel);
	      
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 frame.setVisible(true);
	while(true)
	{
		final Socket socket = server.accept();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					//all requests to the server are handled in here packaged away in a function for orders sake
					RequestManagement(socket);
			}
				catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("WOW2");
			}finally 
			{ 
				//System.out.println("IOException crap");
			try {
				if(!socket.isClosed())
				{
				socket.close();	
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("WOW");
			}
			}
			}	}).start();
		}
	
	}

	
		public static void RequestManagement(final Socket socket) throws IOException, ClassNotFoundException 
		{
			DBInterface conn = new DBInterface("localhost");
			final int DBupdate=7,newTurn=0,LoginAttempt=1,getUserStats=2,FirstTurn=3,PresentHighScores=4,closeConnection=5,register=6,ping=-1;
			int line;
			ConnectionHandlerModel M = new ConnectionHandlerModel(socket);
			GiantWorm snake;
			line = M.inputStream.readInt();
			//dB.MainClass conn;
			switch(line)
					{//database updates receives request parameters and send it to the sql interface
					case DBupdate:
					{
						conn.connectToDataBase();
						String UserID="";
						String TargetedArea="";
						String newValue="";
						int i = M.inputStream.readInt();
						for(int j=0;j<i;j++)
						{
							char a=M.inputStream.readChar();
							UserID=UserID+a;
						}
						i=M.inputStream.readInt();
						for(int j=0;j<i;j++)
						{
							char a = M.inputStream.readChar();
							TargetedArea=TargetedArea+a;
						}
						i=M.inputStream.readInt();
						for(int j=0;j<i;j++)
						{
							char a = M.inputStream.readChar();
							newValue=newValue+a;
						}
						conn.updateDB(UserID, TargetedArea, newValue);
						M.outputStream.writeInt(1);
						break;
					}//send ping back to client
					case ping:
					{
						
						M.outputStream.writeInt(1);
						break;
					}//receives user data and send request to database if registration valid
					case register:
						{
							
							conn.connectToDataBase();
							String UserID="";
							String PW="";
							int i = M.inputStream.readInt();
							for(int j=0;j<i;j++)
							{
								char a=M.inputStream.readChar();
								UserID=UserID+a;
							}
							i=M.inputStream.readInt();
							for(int j=0;j<i;j++)
							{
								char a = M.inputStream.readChar();
								PW=PW+a;
							}
							if(conn.checkIfIDExists(UserID))
							{
								
								M.outputStream.writeInt(-2);
								break;
							}
							else
							{
								conn.register(UserID,PW,0,0);
								M.outputStream.writeInt(1);
								break;
							}
							
						}//calculates new position and send snake back
					case newTurn:
						{
							 snake = M.receiveSnake();
							 
							 if(snake!=null)
							 {
								snake.snakeMove();
							 }
								M.SendSnake(snake);
						break;
						}//checks login data
					case LoginAttempt:
						{
							conn.connectToDataBase();
							String UserID="";
							String PW="";
							int i = M.inputStream.readInt();
							for(int j=0;j<i;j++)
							{
								char a=M.inputStream.readChar();
								UserID=UserID+a;
							}
							i=M.inputStream.readInt();
							for(int j=0;j<i;j++)
							{
								char a = M.inputStream.readChar();
								PW=PW+a;
							}
						
					Boolean t =conn.checkLoginDetails(UserID,PW);
						if(t)
						{
							
							M.outputStream.writeInt(1);
							break;
						}
						else
						{
							M.outputStream.writeInt(0);
						}
						break;
						}//returns user stats and sends them to client
					case getUserStats:
						{
							conn.connectToDataBase();
							String UserID="";
						int i =M.inputStream.readInt();
						for(int j =0;j<i;j++)
						{
							char a= M.inputStream.readChar();
							UserID= UserID+a;
						}
						int auth= conn.getauthLevel(UserID);
						int highS=conn.getHighScore(UserID);
						M.outputStream.writeInt(highS);
						M.outputStream.writeInt(auth);
						break;
						}//creates snake and sends it to client
					case FirstTurn:
						{
							snake= new GiantWorm();
							snake.comeToLife();
							M.SendSnake(snake);
							break;
						}//creates String[][] orders it to a proper structure 
					case PresentHighScores:
						{
							
							conn.connectToDataBase();
							String[][] tmp = conn.getScoresforBoard();
						int count =conn.getRowCount();
							M.outputStream.writeInt(count);
						for(int t=0;t<3;t++)
						{
							for(int i =0;i<count;i++)
							{
								M.outputStream.writeInt(tmp[i][t].length());
								for(int j=0;j<tmp[i][t].length();j++)
								{
									M.outputStream.writeChar(tmp[i][t].charAt(j));
								}
							}
						}
							break;
						}
					case closeConnection:
						{
							M.socket.close();
							break;
						}
					
					}//switch
			socket.close();
			}//function
}
		


