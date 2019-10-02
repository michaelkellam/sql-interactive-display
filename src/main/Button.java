package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Button extends JButton implements ActionListener {

	/*
	 * Button Class:
	 * Defines the functions for each button
	 */
	
	private static final long serialVersionUID = 1L;

	private String function;
	
	private Window window;
	
	public Button(String function, Window window) {
		super(function);
		
		this.window = window;
		this.function = function;
		
		this.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		switch(function) {
		
		/* Add: Insert new row into existing table */
		case "Add":
			
			try {
				PreparedStatement ps = window.getOps().getConnection().prepareStatement("SELECT * FROM " + window.getOps().getTable());
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				String[] contents = new String[rsmd.getColumnCount()];
				
				if (!rsmd.isAutoIncrement(1)) {
					contents[0] = rsmd.getColumnName(1);
					for (int i = 1; i < contents.length; i++) {
						contents[i] = rsmd.getColumnName(i+1);
					}
				}else {
						for (int i = 0; i < contents.length; i++) {
							contents[i] = rsmd.getColumnName(i+1);
						}
					}
				
				JTextField[] ins = new JTextField[contents.length];
				JPanel pane = new JPanel();
				for (int i = 0; i < contents.length; i++) {
					pane.add(new JLabel(contents[i]));
					ins[i] = new JTextField(5);
					pane.add(ins[i]);
				}
				int option = JOptionPane.showConfirmDialog(null,pane,"Enter",JOptionPane.OK_CANCEL_OPTION);
				
				if (option == JOptionPane.OK_OPTION) {
					String[] finals = new String[ins.length];
					for (int i = 0; i < finals.length; i++) {
						finals[i] = ins[i].getText();
					}
					
					window.getOps().add(window.getOps().getConnection(), window.getOps().getTable(), finals);

				}
			} catch(SQLException err) {
				JOptionPane.showMessageDialog(null, err.toString());
			}
			
			refresh();
			
			
			break;
		case	 "Update":
			JPanel pane = new JPanel();
			try {
				PreparedStatement ps = window.getOps().getConnection().prepareStatement("SELECT * FROM " + window.getOps().getTable());
				ResultSet rs = ps.executeQuery();
				String col1 = rs.getMetaData().getColumnName(1);
				pane.add(new JLabel(col1));
				JTextField jt1 = new JTextField(5);
				pane.add(jt1);
				pane.add(new JLabel("Desired edit"));
				JTextField jt2 = new JTextField(5);
				pane.add(jt2);
				pane.add(new JLabel("New value"));
				JTextField jt3 = new JTextField(5);
				pane.add(jt3);

				int option = JOptionPane.showConfirmDialog(null, pane,"Enter the values",JOptionPane.OK_CANCEL_OPTION);
				
				String in1 = "";
				String in2 = "";
				String in3 = "";
				if (option == JOptionPane.OK_OPTION) {
					in1 = jt1.getText();
					in2 = jt2.getText();
					in3 = jt3.getText();
				} else {
					JOptionPane.showMessageDialog(null, "Update cancelled");
				}
				
				window.getOps().edit(window.getOps().getConnection(), window.getOps().getTable(), in1, in2, in3);
				
				JOptionPane.showMessageDialog(null,"Update successful");
				refresh();
			} catch(SQLException err) {
				err.printStackTrace();
			}
			break;
			/* Delete row from existing table */
		case "Remove":
			
			String col1 = "";
			try {
				PreparedStatement ps = window.getOps().getConnection().prepareStatement("SELECT * FROM " + window.getOps().getTable());
				ResultSet rs = ps.executeQuery();
				col1 = rs.getMetaData().getColumnName(1);
			} catch(SQLException err) {
				err.printStackTrace();
			}
			
			String in= JOptionPane.showInputDialog("Enter corresponding " + col1 + " to be removed");

			
			if (in.length() > 0) {
				window.getOps().delRow(window.getOps().getConnection(),window.getOps().getTable(),in);
			} else sendError("Invalid input");
			
			JOptionPane.showMessageDialog(null, "Successfully deleted");
			refresh();
			break;
		case "Refresh":
			System.out.println("Refreshing");
			refresh();
			break;
		case "Search":
			System.out.println("Searching");
			break;
		case "Change":
			JPanel panel = new JPanel();
			JTextField t1 = new JTextField(5);
			JTextField t2 = new JTextField(5);
			JLabel l1 = new JLabel("Database");
			JLabel l2 = new JLabel("Table");
			
			panel.add(l1);
			panel.add(t1);
			panel.add(l2);
			panel.add(t2);
			int option = JOptionPane.showConfirmDialog(null,panel,"Enter the database and table",JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				window.getOps().setDatabaseTable(t1.getText(), t2.getText());
				window.setTable(t2.getText());
				refresh();
			}
			break;
		}
	}
	
	private void refresh() {
		Dimension s = new Dimension(window.getFrame().getSize());
		Dimension decoy = new Dimension(0,0);
		window.getFrame().setSize(decoy);
		window.getFrame().setSize(s);
		
		window.getFrame().setFocusable(true);
	}
	
	public String getFunction() {
		return function;
	}
	
	public static void sendError(String msg) {
		JOptionPane.showMessageDialog(null,msg);
	}

}
