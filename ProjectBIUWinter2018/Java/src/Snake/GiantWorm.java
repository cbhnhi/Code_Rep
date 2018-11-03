package Snake;

import java.awt.Point;
import java.util.ArrayList;

public class GiantWorm {
	public boolean dead=false;
public ArrayList<Point> snakeParts = new ArrayList<Point>();
public final int UP = 0, DOWN = 1, LEFT = 2,RIGHT = 3,SCALE=10;
public int direction = DOWN,tailLength = 10;
public Point head;

public GiantWorm()
{
	comeToLife();
}
public void comeToLife()
{
	tailLength =1;
	direction = RIGHT;
	head = new Point(1,1);
	snakeParts.clear();
	snakeParts.add(head);
	dead=false;
	
}
public void snakeMove() 
{
	switch(direction)
	{
	case UP:
	{
		head=new Point(head.x,head.y-1);
		if(oOB(head))
		{
			dead=true;
			return;
		}
		else
		{
			this.dead=false;
		 snakeParts.add(head);
		 break;
		}
	}
	case DOWN:
	{
		head= new Point (head.x,head.y+1);
		if(oOB(head))
		{
			this.dead=true;
			return;
		}
		else
		{
			dead=false;
			snakeParts.add(head);
			break;
		}
	}
	case RIGHT:
	{
		head = new Point (head.x+1,head.y);
		if(oOB(head))
		{
			this.dead=true;
			return;
		}
		else
		{
			this.dead=false;
			snakeParts.add(head);
		break;
		}
	}
	case LEFT:
	{
		head = new Point (head.x-1,head.y);
		if(oOB(head))
		{
			dead=true;
			return;
		}
		else
		{
			dead=false;
			snakeParts.add(head);
			break;
		
		}
	}
	}
	if(snakeParts.size()>tailLength)
	{
		snakeParts.remove(0);
	}
	
}
private boolean oOB(Point p)
{
	if(head.x<0||head.x>79||head.y<0||head.y>66)
	{
		return true;
	}
	else
	{
		for(Point point : snakeParts)
		{
			if(point.equals(p))
			{
				return true;
			}
		}
		return false;
	}
}

}
