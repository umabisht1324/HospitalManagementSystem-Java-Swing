package hospitalManagementSystem;

//import static java.lang.System.out;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients 
{

	private Connection conn;
	private Scanner sc;
	
	public Patients(Connection conn,Scanner sc) 
	{
		this.conn=conn;
		this.sc=sc;
	}
	
	public void addPatient()
	{
		System.out.print("Enter Patient Name: ");
	    sc.nextLine(); // Consume the newline character left in the buffer
	    String name = sc.nextLine(); // Now read the entire line including spaces
	    System.out.print("Enter Patient Age: ");
	    int age = sc.nextInt();
	    System.out.print("Enter Patient Gender: ");
	    String gender = sc.next();

		
		try
		{
			
			String q="INSERT into patients(pname,page,gender) VALUES(?,?,?)";
			PreparedStatement ps=conn.prepareStatement(q);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			int rowsAffected=ps.executeUpdate();
			if(rowsAffected>0)
			{
				System.out.println("Query Executed SucessFully");
			}
			else
			{
				System.out.println("Query Didn't Executed Sucessfully");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void viewPatients()
	{
		String query="SELECT * from Patients";
		try {
			PreparedStatement ps=conn.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+-----------+-------------------------------+--------+-----------+");
			System.out.println("|Patient Id |Name                           |Age     |Gender)    |");
			System.out.println("+-----------+-------------------------------+--------+-----------+");
			while(rs.next())
			{
				int id=rs.getInt("pid");
				String name=rs.getString("pname");
				int age = rs.getInt("page");
				String gender=rs.getString("gender");
				System.out.printf("|%-11d|%-31s|%-8d|%-11s|\n", id, name, age, gender);

				System.out.println("+-----------+-------------------------------+--------+-----------+");

			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public boolean checkPatients(int id) {
	    String query = "SELECT * FROM patients WHERE pid=?";
	    try {
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        return rs.next(); // Return true if there is a row with the given ID, false otherwise
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Return false in case of an exception or if no row with the given ID is found
	}

	
	

	

}
