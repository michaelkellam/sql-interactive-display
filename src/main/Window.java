package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Window {

	/*
	 * GUI for the user
	 */
	private static final long serialVersionUID = 1L;
	
	private SQLOps sql;
	
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel southPanel;
	private JPanel subPanel;
	private String[] colLabels;
	private ArrayList<String> rowLabs = new ArrayList<String>();
	
	private Button bAdd,bRemove,bUpdate,bSearch,bRefresh,bChange;

	private Input input;
	
	public boolean isFirst = true;
	
	private String table;
	
	public Window(SQLOps sql,Connection con) {
		
		table = sql.getTable();
		
		this.sql = sql;
		frame = new JFrame("SQL GUI");
		
		input = new Input(this);
		
				
		mainPanel = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;
			
			
			
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				ResultSet rs = null;
				
				int numCol = 0;
				int numRow = 0;
				
				try {
					rs = con.prepareStatement("SELECT * FROM " + table).executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
					
				try {
					numCol = 0;
					numRow = 0;
					numCol = con.prepareStatement("SELECT * FROM " + table).getMetaData().getColumnCount();
					colLabels = new String[numCol];
								
					while (rs.next()) {
						numRow++;

					}
					
					/* Gets the column names */
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						colLabels[i-1] = rs.getMetaData().getColumnName(i);
					}
					
					
				} catch(SQLException e) {
					e.printStackTrace();
				}
				
				g.setColor(this.getBackground());
				g.fillRect(0,0,this.getWidth(),this.getHeight());
				g.setColor(Color.BLACK);
				
				/* Draw borders */
				g.drawLine(15, 15, 15, this.getHeight()-48);
				g.drawLine(15, 15, this.getWidth()-15, 15);
				g.drawLine(this.getWidth()-15,15,this.getWidth()-15,this.getHeight()-48);
				g.drawLine(15, this.getHeight()-48, this.getWidth()-15, this.getHeight()-48);
				
				double wid = (double) (this.getWidth()-15-15) / numCol;
				double hi = (double) (this.getHeight()-48-15) / (numRow+1);
				
				/* Draw columns, write labels */
				for (int i = 0; i < numCol; i++) {
					if (i > 0) {
						g.setColor(Color.BLACK);
						g.setFont(new Font("Courier",Font.PLAIN,13));
						g.drawLine((int)wid * i+1, 15, (int) wid * i+1, this.getHeight()-48);
					}
					g.setColor(Color.BLUE);
					g.drawString(colLabels[i], ((int) wid * i + 1) - (colLabels[i].length() - 50), 15 +  (int) hi/2);
				}
				
				/* Draw rows */
				g.setColor(Color.BLACK);
				for (int i = 1; i <= numRow; i++) {
					g.drawLine(15, i*(int)hi, this.getWidth()-15, i*(int)hi);
				}
				
				
				int realRow = numRow - 1;
				
				ArrayList<String> arr = null;
				/* Get data of entire table */
				try {
					arr = sql.getEntireTable(con, "" + table);
				} catch(SQLException e) {
					System.out.println(e);
				}
								
				/* Draw contents of table */
				
				/* If there's only one entry, the inputting of the table needs to be approached differently. */
				if (numRow == 1) {
					for (int i = 0; i < arr.size(); i++) {
						g.drawString(arr.get(i), (int) wid/4 + ((i)*(int)wid), this.getHeight() - (int) (0.3 * this.getHeight()));
					}
					/* If there's more than one entry, this will print out everything. */
				} else {
					for (int i = 0; i < numCol; i++) {
						for (int j = 0; j <= realRow; j++) { 
						g.drawString(arr.get((numRow)*i+j), (i*(int)wid) + (this.getWidth()/numCol)/5 - arr.get(numRow*i+j).length()/(int)1.1, this.getHeight()/numRow + (j*(int)hi));
						}
					}
					
					System.out.println(table + " table");
					System.out.println(getOps().getDB() + " DB");
					if (!arr.isEmpty()) {
						arr.clear();
					}
				}
				
				System.out.println("Panel drawn");
			}
		};
		frame.addKeyListener(input);
		frame.setFocusable(true);		
		southPanel = new JPanel();
		subPanel = new JPanel(new BorderLayout());
		
		bAdd = new Button("Add",this);
		bUpdate = new Button("Update",this);
		bRemove = new Button("Remove",this);
		bRefresh = new Button("Refresh",this);
		bSearch = new Button("Search",this);
		bChange = new Button("Change",this);
		
		frame.add(mainPanel);
		mainPanel.setPreferredSize(new Dimension(960,540));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		mainPanel.setLayout(new BorderLayout());
		southPanel.setLayout(new FlowLayout());

		
		southPanel.setPreferredSize(new Dimension(640,40));		
		
		mainPanel.add(southPanel,BorderLayout.SOUTH);
		
		
		southPanel.setBorder(new EmptyBorder(10,10,10,10));
		
										// (N,W,S,E)
		southPanel.setBorder(new EmptyBorder(5,25,5,25));
		
		
		southPanel.add(bAdd,FlowLayout.LEFT);
		southPanel.add(bUpdate,FlowLayout.CENTER);
		southPanel.add(bChange,FlowLayout.RIGHT);
		southPanel.add(bSearch,FlowLayout.RIGHT);
		southPanel.add(bRefresh,FlowLayout.RIGHT);
		southPanel.add(bRemove,FlowLayout.RIGHT);
		
		
		frame.setVisible(true);
		
		
	}
	
	public void sendInfo() {
		System.out.println("Successfully setup");
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void paint(Graphics g) {
		
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public SQLOps getOps() {
		return sql;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
