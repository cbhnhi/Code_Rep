package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameScreen {
	private JPanel content;
	public Dimension dim;
	public JFrame frame;
	private final GamePanel gamepanel;
	GameScreen()
	{

		dim=Toolkit.getDefaultToolkit().getScreenSize();
	      frame = new JFrame("Use directional keys to turn the snake,press space for pause toggle, press P to see Highscores");
	     

	      content = new JPanel();
	  	  
	      gamepanel = new GamePanel(800,700);	
	  	
	      content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
	      content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	      content.setBackground(Color.WHITE);
	   //   content.add(gameHeaderPanel);
	      content.add(gamepanel);
	      frame.add(content);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setResizable(false);
	      frame.pack();
	    
	      frame.setLocationRelativeTo(null);
	      frame.setVisible(true);
	}


public void repaintGamePanel()
{
	gamepanel.repaint();
}
}
