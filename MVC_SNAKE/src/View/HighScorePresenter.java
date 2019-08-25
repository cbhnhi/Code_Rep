package View;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controller.Core;

@SuppressWarnings("serial")
public class HighScorePresenter extends JFrame{
	private JPanel topPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private String[] columnNames=new String[] {"Username", "Highscore","AuthorityLevel"};
	private String[][] dataValues;
	
	public HighScorePresenter(String[][] dataValues)
	{
		this.dataValues=dataValues;
		setTitle("The Good ones");
		setSize(300,300);
		setResizable(false);
		
		topPanel= new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);
	//	setDefaultCloseOperation(EXIT_ON_CLOSE);
		

		 myTableModel model=new myTableModel();
		 table =new JTable( );
		 table.setRowHeight(50);
		 table.setModel(model);
		 scrollPane=new JScrollPane(table);
		 topPanel.add(scrollPane,BorderLayout.CENTER);   
		 table.addMouseListener(new java.awt.event.MouseAdapter()
			 {
			 public void mouseClicked(java.awt.event.MouseEvent e)
			 	{
				 
				 if(Core.m_snake_core.currUser.authlevel==0)
				 {
					 return;
				 }
				 int row=table.rowAtPoint(e.getPoint());
				 int col= table.columnAtPoint(e.getPoint());
				 String temp = JOptionPane.showInputDialog(null,"Edit this value?");
				if(temp!=null)
				{
						//we're handling highscore update
						Core.m_snake_core.requestDataUpdate(table.getValueAt(row, 0).toString(),columnNames[col],temp);
						
				}
			 	}
			 });
			
		 setVisible(true);
	}
	
	public class myTableModel extends DefaultTableModel
     {

     	myTableModel()
     	{
     		super(dataValues,columnNames);
     		
     	}
     	public boolean isCellEditable(int row,int cols)
     	{
     				return false;                                                                                         
     	}

     }         
}
