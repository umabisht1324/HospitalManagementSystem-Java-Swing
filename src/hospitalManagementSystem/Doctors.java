package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {

	private Connection conn;
	private Scanner sc;
	
	public Doctors(Connection conn,Scanner sc) 
	{
		this.sc=sc;
		this.conn=conn;
	}

	
	public void viewDoctor()
	{
		String q = "SELECT * FROM doctors";

		
		try {
			PreparedStatement ps=conn.prepareStatement(q);
			ResultSet rs=ps.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+--------------+----------------------+----------------------+");
			System.out.println("| Doctors Id   | Name                 | Specialization       |");
			System.out.println("+--------------+----------------------+----------------------+");
			
			while(rs.next())
			{
				int id=rs.getInt("did");
				String name=rs.getString("dname");
				String specz=rs.getString("specialization");
				
				System.out.printf("|%-14d|%-22s|%-22s|\n",id,name,specz);
				System.out.println("+--------------+----------------------+----------------------+");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	public boolean checkDoctor(int id) {
	    String q = "SELECT * FROM doctors WHERE did=?";
	    try {
	    	PreparedStatement ps=conn.prepareStatement(q);
	    	ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+--------------+----------------------+----------------------+");
			System.out.println("| Doctors Id   | Name                 | Specialization       |");
			System.out.println("+--------------+----------------------+----------------------+");
			
			while(rs.next())
			{
				int did=rs.getInt("did");
				String name=rs.getString("dname");
				String specz=rs.getString("specialization");
				
				System.out.printf("|%-14d|%-22s|%-22s|\n",did,name,specz);
				System.out.println("+--------------+----------------------+----------------------+");
				return true;
				
			}// Return true if there is a row with the given ID, false otherwise
//			return rs.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	    }
	    return false; // Return false in case of an exception
	}

	
	

}

