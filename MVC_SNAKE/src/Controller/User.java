package Controller;

public class User {
public String userID;
public int authlevel;
public int highScore;

public User(String userID,int authlevel,int highScore)
{
	this.userID=userID;
	this.authlevel=authlevel;
	this.highScore=highScore;
}

public User()
{
	this(null,0,0);
}

}
