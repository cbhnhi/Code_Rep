package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	Controller.Core r;
	 private final int width, height;
	 private Color snakeColor = Color.BLUE;
	 private Color appleColor = Color.RED;
	//   private Graphics2D g2d;
	   
	public GamePanel(int width,int height)
	{
		this.width = width;
		this.height = height;
	}

	 @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(width, height);
	    }

	    @Override
	    public Color getBackground() {
	        return Color.black;
	    }

	    @Override
	    public boolean isOpaque() {
	        return true;
	    }


	    /**
	     * Ensures GUI is painted when the window is moved or hidden.
	     */
	    @Override
	    public void paintComponent(Graphics g) {

	        super.paintComponent(g);
	        r=  Controller.Core.m_snake_core;
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, width, height);
			
			for(Point point:r.snake.snakeParts)
			{
				g.setColor(snakeColor);
				g.fillRect(point.x*r.snake.SCALE, point.y*r.snake.SCALE, r.snake.SCALE, r.snake.SCALE);
			}
				g.setColor(appleColor);
				g.fillRect(r.snake.apple.x*r.snake.SCALE, r.snake.apple.y*r.snake.SCALE, r.snake.SCALE, r.snake.SCALE);
			
				String string = "Score: " + r.snake.score;
				g.setColor(appleColor);
				g.drawString(string, (int) (width / 2 - string.length() * 2.5f), 10);
			
				if (r.snake.dead==1)
				{
					string = "Game Over!";
					g.drawString(string, (int) (width / 2 - string.length() * 2.5f), height / 4);
				}
			

				if (r.paused && r.snake.dead==0)
				{
					string = "Paused!";
					g.drawString(string, (int)(width / 2 - string.length() * 2.5f), height / 4);
				}
		
		}

	    }



