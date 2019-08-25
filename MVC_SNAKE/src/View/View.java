package View;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




import Controller.Core;

public final class View implements KeyListener{




//private JPanel content;
public Controller.Core core;
public LoginScreen L;
public GameScreen g;
public View()
{
	core=Core.m_snake_core;
}

public void presentHighScores(String[][] tmp)
{
	@SuppressWarnings("unused")
	HighScorePresenter a = new HighScorePresenter(tmp);
}
public void initLoginScreen()
{
	L= new LoginScreen();
}

public void initViewAtGameStart()
{
	g= new GameScreen();
	g.frame.addKeyListener(this);
}
@Override
public void keyPressed(KeyEvent arg0) {
		Core.m_snake_core.HandlerOfKeys(arg0);
}
public void repaintGamePanel()
{
	g.repaintGamePanel();
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
