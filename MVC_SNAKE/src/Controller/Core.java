package Controller;


import Model.GiantWorm;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

@SuppressWarnings("unused")
public class Core implements ActionListener{
public static Core m_snake_core;
private View.View view;
public GiantWorm snake;
public User currUser;
private ConnectionHandlerController M;


private Timer timer;
//public Dimension dim;

public boolean paused=false;
public boolean dirchange=false;
public static void main(String[] args) {
	// TODO Auto-generated method stub
	 try {
		 
		m_snake_core=new Core();
		m_snake_core.startLoginScreen();
		while(true)
		{
			
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally
	 {
		try {
			m_snake_core.M.socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			
		}
	 }
}

public Core() 
{
	view=new View.View();
	timer=new Timer(100,this);
	M=new ConnectionHandlerController(null);
}

public void startLoginScreen()
{
	view.initLoginScreen();
}

public void startNewGame() 
{	
	
	timer.stop();
	snake=M.startingPosition();
	timer.start();
}

public void startGame() 
{
		snake= M.startingPosition();
		view.initViewAtGameStart();
		timer.start();
}
public void HandlerOfKeys(KeyEvent e)  {
	// TODO Auto-generated method stub
	int i = e.getKeyCode();
	if(i== KeyEvent.VK_P)
	{
		popHighScores();
	}
if(!dirchange)
{
	if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && snake.direction !=snake.RIGHT)
	{
		snake.direction = snake.LEFT;
		dirchange=true;
	}

	if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && snake.direction != snake.LEFT)
	{
		snake.direction = snake.RIGHT;
		dirchange=true;
	}

	if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && snake.direction !=snake.DOWN)
	{
		snake.direction = snake.UP;
		dirchange=true;
	}

	if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && snake.direction != snake.UP)
	{
		snake.direction = snake.DOWN;
		dirchange=true;
	}
}
	if (i == KeyEvent.VK_SPACE)
	{
		if (snake.dead==1)
		{
			startNewGame();
		}
		else
		{
			paused = !paused;
		}
	}
}

@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	dirchange=false;
	if(snake.head != null && snake.dead==0&&!paused)
	{
		view.repaintGamePanel();
		NewTurn();
	}
}

public void NewTurn()
	{
		try {
			//timer.stop();
			M.initiateConnection();
			M.outPutToModel.writeInt(M.newTurn);
			M.sendSnakeController2Model(snake);
			GiantWorm tmp =M.receiveSnake();
			if(tmp!=null)
			{
			snake = tmp;
			if(snake.dead==1)
			{
				if(currUser.highScore<snake.score)
				{
					requestDataUpdate(currUser.userID,"HighScore",Integer.toString(snake.score));
					currUser.highScore=snake.score;
				}
			}
			}
			
			M.socket.close();
			//timer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("XXX");
		}finally {try {
			M.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
	}

public void LoginButtonClick(String UserID,String PW,String IP)
{
	M.IP=IP;
	
		
	//	if(!M.checkIP())
	//	{
	//		JOptionPane.showMessageDialog(null, "Login failed, Bad IP", "fuck",1);
	//		return;
	//	}

	
	
	if(M.checkLoginDetails(UserID,PW))
	{
		view.L.frame.dispose();
		currUser=M.getUserStats(UserID);
		startGame();
	}
	else 
	{
		JOptionPane.showMessageDialog(null, "Login failed", "fuck",1);
	}
	
}

public void RegisterButtonClick(String UserID,String PW,String IP)
{
	
	M.IP=IP;
	
	
		if(M.sendRegistery(UserID, PW))
		{
			currUser=new User(UserID,0,0);
			//need to create user data
			startGame();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Username taken", "fuck",1);
		}
		
	//if(M.)
}

public void popHighScores()
{
	String[][] tmp=	M.getHighScores();
	view.presentHighScores(tmp);
}
public boolean CheckServerIPButtonClick(String IP)
{
	
	M.IP=IP;
	
	
	if(	M.checkIP())
	{
		return true;
	}
	else
	{
		return false;
	}
}

public void requestDataUpdate(String UserID,String TargetedArea,String newValue)
{
	M.requestDataUpdate(UserID,TargetedArea,newValue);
	
}
}