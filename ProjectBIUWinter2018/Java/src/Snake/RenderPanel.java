package Snake;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
	
@Override
protected void paintComponent(Graphics g)
{
	
	super.paintComponent(g);
	
	
	g.setColor(Color.GREEN);
	g.fillRect(0, 0, 800, 700);
	Core r=Core.m_Snake_core;
	
	for(Point point:r.snake.snakeParts)
	{
		g.setColor(Color.BLUE);
		g.fillRect(point.x*r.snake.SCALE, point.y*r.snake.SCALE, r.snake.SCALE, r.snake.SCALE);
	}
		g.setColor(Color.RED);
		g.fillRect(r.apple.x*r.snake.SCALE, r.apple.y*r.snake.SCALE, r.snake.SCALE, r.snake.SCALE);
	
		String string = "Score: " + r.score;
		g.setColor(Color.WHITE);
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);
		string = "Game Over!";
		if (r.snake.dead)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) r.dim.getHeight() / 4);
		}
		string = "Paused!";

		if (r.paused && !r.snake.dead)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) r.dim.getHeight() / 4);
		}
	
	
}

}
