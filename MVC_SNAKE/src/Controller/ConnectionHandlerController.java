package Controller;

import java.awt.Point;
import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

//import javax.swing.JOptionPane;

import Model.GiantWorm;
public class ConnectionHandlerController {
public Socket socket;
public DataInputStream modelInputStream;

public  DataOutputStream outPutToModel;
public final int DBupdate=7,newTurn=0,LoginAttempt=1,getUserStats=2,FirstTurn=3,PresentHighScores=4,closeConnection=5,register=6,ping=-1;
//public ObjectOutputStream objectSender;
//public ObjectInputStream objectReceiver;
//private String line;
public String IP;

ConnectionHandlerController(String args) 
{
	IP=args;
}

public void initiateConnection() throws UnknownHostException, IOException
{
	socket=new Socket(IP,8000);
//	socket.setSoTimeout(80);
	outPutToModel= new DataOutputStream(socket.getOutputStream());
	modelInputStream= new DataInputStream(socket.getInputStream());
}

public GiantWorm startingPosition()
{
	try {
		initiateConnection();
		outPutToModel.writeInt(FirstTurn);
		GiantWorm snake= receiveSnake();
		return snake;
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}

public void sendSnakeController2Model(GiantWorm giantworm)
{
	//try {
	//	objectSender.writeObject(giantworm);
		//objectSender.close();
	try {
		outPutToModel.writeInt(giantworm.tailLength);
		for(int i = 0;i<giantworm.tailLength;i++)
		{
			Point tmp=giantworm.snakeParts.get(i);
			outPutToModel.writeInt(tmp.x);
			outPutToModel.writeInt(tmp.y);
		}
		outPutToModel.writeInt(giantworm.direction);
		outPutToModel.writeInt(giantworm.head.x);
		outPutToModel.writeInt(giantworm.head.y);
		
		outPutToModel.writeInt(giantworm.dead);
		outPutToModel.writeInt(giantworm.score);
		outPutToModel.writeInt(giantworm.apple.x);
		outPutToModel.writeInt(giantworm.apple.y);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("ControlSendSnake");
	}
}

public GiantWorm receiveSnake() 
{
	int num;
GiantWorm tmp;
try {
	tmp = new GiantWorm();
	ArrayList<Point> list=new ArrayList<Point>();
	num=modelInputStream.readInt();//num of members in array
	for(int i = 0;i<num;i++)
	{
		int x = modelInputStream.readInt();
		int y = modelInputStream.readInt();
		list.add(new Point(x,y));
	}
	tmp.snakeParts=list;
	tmp.tailLength=num;
	tmp.direction=modelInputStream.readInt();
	int x =modelInputStream.readInt();
	int y = modelInputStream.readInt();
	tmp.head=new Point(x,y);
	tmp.dead=modelInputStream.readInt();
	tmp.score=modelInputStream.readInt();
	x=modelInputStream.readInt();
	y=modelInputStream.readInt();
	tmp.apple=new Point(x,y);

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Control Receive Snake");
		tmp= null;
	}
	return tmp;
}

public boolean checkLoginDetails(String UserID,String PW)
{

		try {
			initiateConnection();
			outPutToModel.writeInt(LoginAttempt);
			outPutToModel.writeInt(UserID.length());
			for(int i = 0;i<UserID.length();i++)
			{
				outPutToModel.writeChar(UserID.charAt(i));
			}
			outPutToModel.writeInt(PW.length());
			for(int i = 0;i<PW.length();i++)
			{
				outPutToModel.writeChar(PW.charAt(i));
			}
			
		//	M.modelInputStream=new DataInputStream(M.socket.getInputStream());
			
			//kill loging and initiate game()
			//dialog fail w/e
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		int result = -1;
		while(result==-1)
		{
		try {
			result=modelInputStream.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
		}
	
		}
		if(result==1)
		{
			return true;
		}
		else {
			return false;
		}
		
}

public boolean checkIP()
{
	
int answer=0;
	try {
		initiateConnection();
		outPutToModel.writeInt(ping);
		 answer = modelInputStream.readInt();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}

 if(answer==1)
 {
	 try {
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return true;
 }
 else
 {
	 return false;
 }

}

public boolean sendRegistery(String UserID,String PW)
{
	try {
		initiateConnection();
		outPutToModel.writeInt(register);
		
		outPutToModel.writeInt(UserID.length());
		for(int i = 0;i<UserID.length();i++)
		{
			outPutToModel.writeChar(UserID.charAt(i));
		}
		outPutToModel.writeInt(PW.length());
		for(int i = 0;i<PW.length();i++)
		{
			outPutToModel.writeChar(PW.charAt(i));
		}
		
		
		int  answer = modelInputStream.readInt();
		if(answer==1)
		{
			return true;
		}
		else if(answer==-2)
		{
			return false;
		}
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return false;
}

public void requestDataUpdate(String UserID,String TargetedArea,String newValue)
{
	try {
		initiateConnection();
		outPutToModel.writeInt(DBupdate);
		outPutToModel.writeInt(UserID.length());
		for(int i=0;i<UserID.length();i++)
		{
			outPutToModel.writeChar(UserID.charAt(i));
		}
		outPutToModel.writeInt(TargetedArea.length());
		for(int i=0;i<TargetedArea.length();i++)
		{
			outPutToModel.writeChar(TargetedArea.charAt(i));
		}
		outPutToModel.writeInt(newValue.length());
		for(int i=0;i<newValue.length();i++)
		{
			outPutToModel.writeChar(newValue.charAt(i));
			//potential bug to be tested on numbers
		}
		@SuppressWarnings("unused")
		int i=modelInputStream.readInt();
		Core.m_snake_core.popHighScores();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public User getUserStats(String UserID)
{
	try {
		initiateConnection();
		outPutToModel.writeInt(getUserStats);
		outPutToModel.writeInt(UserID.length());
		for(int i = 0 ; i< UserID.length();i++)
		{
			outPutToModel.writeChar(UserID.charAt(i));
		}
		int highScore= modelInputStream.readInt();
		int authlevel= modelInputStream.readInt();
		User tmp=new User(UserID,authlevel,highScore);
		return tmp;
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}

public String[][] getHighScores()
{
	try {
		initiateConnection();
		outPutToModel.writeInt(PresentHighScores);
		int count = modelInputStream.readInt();
		String[][] tmp= new String[count][3];
		for(int t=0;t<3;t++)
		{
			for(int i=0;i<count;i++)
			{
				int length = modelInputStream.readInt();
				String str="";
				for(int j=0;j<length;j++)
				{
					char a = modelInputStream.readChar();
					str=str+a;
				}
				tmp[i][t]=str;
			}
		}
		return tmp;
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
}

