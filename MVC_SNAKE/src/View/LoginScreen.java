package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Controller.Core;

public class LoginScreen {

//private Dimension dim;
public JFrame frame;
public JButton Login,Register,CheckIP;
public JTextField User,PW,IP;

LoginScreen()
{
   
    frame = new JFrame("LoginScreen");
    frame.setSize(800,600);
    frame.setResizable(false);
    frame.setLayout(new FlowLayout());
 
 	User= new JTextField("UserID",50);
 	PW= new JTextField("Password",50);
 	IP = new JTextField("Server IP",50);
 	Login = new JButton("Login");
 	Register = new JButton("Register"); 	
 	CheckIP = new JButton("Check Server IP");

 	frame.add(Login);
 	frame.add(Register);
 	frame.add(CheckIP);
 	frame.add(User);
 	frame.add(PW);
 	frame.add(IP);
 	Login.addActionListener(new ActionListener()
 			{
 			  public void actionPerformed(ActionEvent e)
 			  {
 				String username = User.getText().trim();
 				String password = PW.getText().trim();
 				 String IP2=IP.getText().trim();
 				Core.m_snake_core.LoginButtonClick(username,password,IP2);
 			  }
 			}
 			);
 	
	Register.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  String username = User.getText().trim();
			  String password = PW.getText().trim();
			  String ip=IP.getText().trim();
			  Core.m_snake_core.RegisterButtonClick(username, password,ip); 
		  }
		}
		);
	CheckIP.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  String ip = IP.getText().trim();
			  if(Core.m_snake_core.CheckServerIPButtonClick(ip))
			  {
				  CheckIP.setBackground(Color.GREEN);
			  }
			  else
			  {
				  CheckIP.setBackground(Color.RED);
			  }
		  }
		}
		);
	
 	
 	
 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	frame.setVisible(true);
}
}
	
