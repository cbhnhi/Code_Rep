package Snake;

import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
public class Core implements ActionListener, KeyListener{
	JFrame m_jFrame;
	public static Core m_Snake_core;
	GiantWorm snake;
	Dimension dim;
	public Random random;
	public Point apple;
	public Timer timer = new Timer(80,this);
	public int score=0;
	public boolean paused=false;
public RenderPanel m_RenderPanel;
	public Core()
	{
		random= new Random();
		 dim = Toolkit.getDefaultToolkit().getScreenSize();
			m_jFrame = new JFrame("Snake");
			m_jFrame.setVisible(true);
			m_jFrame.setSize(800, 700);
			m_jFrame.setResizable(false);
			m_jFrame.setLocation(dim.width/2-m_jFrame.getWidth()/2, dim.height/2-m_jFrame.getHeight()/2);
			m_jFrame.add(m_RenderPanel = new RenderPanel());
			m_jFrame.addKeyListener(this);
			m_jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startGame();
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Core m_Snake_core=new Core();
		
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		m_RenderPanel.repaint();;
		if(snake.head != null && !snake.dead&&!paused)
		{
			snake.snakeMove();
			System.out.println("x=" + snake.head.x + " Y="+ snake.head.y);
			if(snake.head.equals(apple))
			{
				score +=10;
				snake.tailLength++;
				apple.setLocation(random.nextInt(79), random.nextInt(66));
			}
		}
	}
	public void startGame()
	{
	 snake= new GiantWorm();
	 score=0;
	 random=new Random();
	 apple=new Point(random.nextInt(79), random.nextInt(79));
	 timer.start();
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && snake.direction != snake.RIGHT)
		{
			snake.direction = snake.LEFT;
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && snake.direction != snake.LEFT)
		{
			snake.direction = snake.RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && snake.direction !=snake. DOWN)
		{
			snake.direction = snake.UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && snake.direction != snake.UP)
		{
			snake.direction = snake.DOWN;
		}

		if (i == KeyEvent.VK_SPACE)
		{
			if (snake.dead)
			{
				startGame();
			}
			else
			{
				paused = !paused;
			}
		}
	}
		
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
