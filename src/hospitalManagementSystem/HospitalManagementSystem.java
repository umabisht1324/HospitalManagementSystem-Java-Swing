package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="Qqweasd@1234";
	

	public static void main(String[] args) {
//		try
//		{
////			conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcDatabase", "root" , "Qqweasd@1234");		//	provides method which help us in connecting with database
//			Class.forName("conn.mysql.cj.jdbc.Driver");
//			
//		}
//		catch(ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
		try
		{
			Scanner sc=new Scanner(System.in);
			Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root" , "Qqweasd@1234");		//	provides method which help us in connecting with database
			Patients p=new Patients(conn,sc);
			Doctors d=new Doctors(conn,sc);
			while(true)
			{
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient: ");
				System.out.println("2. View Patients: ");
				System.out.println("3. View Doctors: ");
				System.out.println("4. Book an Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter Your Choice: ");
				int ch=sc.nextInt();
				switch(ch)
				{
					case 1:
						p.addPatient();
						System.out.println();
						break;
					case 2:
						p.viewPatients();
						System.out.println();
						break;
					case 3:
						d.viewDoctor();
						System.out.println();
						break;
					case 4:
						bookAppointment(conn,sc,p,d);
						System.out.println();
						break;
					case 5:
						System.out.println("System is Closed Sucessfully");
					    System.exit(0);
					default:
						System.out.println("Enter Valid Choice!!! : ");
						
						
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		

	}
	
	public static void bookAppointment(Connection conn,Scanner sc,Patients p,Doctors d)
	{
		System.out.println("Enter Patient's Id: ");
		int pId=sc.nextInt();
		System.out.println("Enter Doctor's Id: ");
		int dId=sc.nextInt();
		System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
		String appointDate=sc.next();
		boolean pa=p.checkPatients(pId);
		boolean da=d.checkDoctor(dId);
		if(pa)
		{
			System.out.println("Patient  Doesn't Exist!!! ");
		}
		if(da)
		{
			System.out.println("Doctor Doesn't Exist!!! ");
		}
		if(pa && da)
		{
			if(checkDoctorAvail(pId,dId, appointDate,conn))
			{
				String q="INSERT INTO appointments(pid,did,appointmentDate)VALUES(?,?,?)";
				try {
					PreparedStatement ps=conn.prepareStatement(q);
					ps.setInt(2, pId);
					ps.setInt(3, dId);
					ps.setString(4, appointDate);
					int rowAff=ps.executeUpdate();
					if(rowAff>0)
					{
						System.out.println("Appointment Booked !!! ");

					}
					else
					{
						System.out.println("Failed to Book Appointment !!! ");
					}
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Doctor Not Available on this Date!!! ");
				System.out.println("Enter New Appointment Date (YYYY-MM-DD): ");
				appointDate=sc.next();
				bookAppointment( conn, sc, p, d);
			}
		}
		else
		{
			System.out.println("Either Patient or Doctor Doesn't Exist!!! ");
		}
		
	}
	
	public static boolean checkDoctorAvail(int patientId, int doctorId, String appointmentDate, Connection conn) {
	    String q = "SELECT COUNT(*) FROM appointments WHERE did=? AND appointmentDate=?";
	    try {
	        PreparedStatement ps = conn.prepareStatement(q);
	        ps.setInt(1, doctorId);
	        ps.setString(2, appointmentDate);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count == 0) {
	                return true; // Doctor is available on the given date
	            } else {
	                return false; // Doctor is not available on the given date
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Return false in case of an exception
	}

}


// snippet.java
// package snippet;

// public class Snippet {
// 	(base) umabisht@UMAs-MacBook-Air HospitalManagementSystem-Java-Swing % cd "/Users/umabisht/Desktop/PLACEMENT/HospitalManagementSystem-Java-Swing/" && javac HospitalManagementSystem.java && java HospitalManagementSystem
// 	package Patients does not exist
// 	import Patients.Patients; 
// 	               ^
// 	HospitalManagementSystem.java:4: error: 
// 	HospitalManagementSystem.java:3: error: 
// }

