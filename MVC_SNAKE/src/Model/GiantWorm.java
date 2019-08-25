package Model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class GiantWorm implements Serializable {
	public int dead=0;
public ArrayList<Point> snakeParts = new ArrayList<Point>();
public final int UP = 0, DOWN = 1, LEFT = 2,RIGHT = 3,SCALE=10;
public int direction = DOWN,tailLength = 10;
public int score;
public Point head,apple;

public GiantWorm()
{
	super();
	comeToLife();
}
public void comeToLife()
{
Random random = new Random();
	tailLength =1;
	direction = RIGHT;
	head = new Point(1,1);
	snakeParts.clear();
	snakeParts.add(head);
	dead=0;
	apple=new Point(random.nextInt(79), random.nextInt(60));
	score=0;
}

@Override 
public String toString() 
{
	return snakeParts.toString()+direction+head.toString()+dead+score+UP+DOWN+LEFT+RIGHT+SCALE+apple.toString()+tailLength;
}


public void snakeMove() 
{
	Random random = new Random();
	switch(direction)
	{
	case UP:
	{
		head=new Point(head.x,head.y-1);
		if(oOB(head))
		{
			dead=1;
			return;
		}
		else
		{
			dead=0;
		 snakeParts.add(head);
		 break;
		}
	}
	case DOWN:
	{
		head= new Point (head.x,head.y+1);
		if(oOB(head))
		{
			dead=1;
			return;
		}
		else
		{
			dead=0;
			snakeParts.add(head);
			break;
		}
	}
	case RIGHT:
	{
		head = new Point (head.x+1,head.y);
		if(oOB(head))
		{
			dead=1;
			return;
		}
		else
		{
			dead=0;
			snakeParts.add(head);
		break;
		}
	}
	case LEFT:
	{
		head = new Point (head.x-1,head.y);
		if(oOB(head))
		{
			dead=1;
			return;
		}
		else
		{
			dead=0;
			snakeParts.add(head);
			break;
		
		}
	}
	}
	
	if(head.equals(apple))
	{
		score +=10;
		tailLength++;
		apple.setLocation(random.nextInt(79), random.nextInt(66));
		return;
	}
	if(snakeParts.size()>tailLength)
	{
		
		snakeParts.remove(0);
	}
	
}

private boolean oOB(Point p)
{
	if(head.x<0||head.x>79||head.y<0||head.y>69)
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
