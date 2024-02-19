package sdet_advanced;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;



public class getMySqlDataIntoJsonFile {
	
	@Test
	public void getMySqlDataInJsonFormat() throws Exception {
		
		Connection conn = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Business", "root", "root");
		
		//create object of the statement class which will help us to execute queries
		Statement o_stmnt = conn.createStatement();
		ResultSet o_rs = o_stmnt.executeQuery("select * from CustomerInfo where purchasedDate=CURDATE()-2 and Location ='Asia';");
		
		//Step 1 - We are creating a resultset object and by rs.next() will fetch us the first row of the 
		//resultset. resultset.getString(<coulumn index>) will fetch us the value of the column mentioned
		//in the column index of that row
		/* 
		o_rs.next();	//this sets the pointer to the first row
		o_rs.getString(1); //here 1 is the column number
		*/
		
		//Step 2 - To get the record of all the rows we use a While loop which will loop through all the lines
		//of the resultset until the end of line is reached
		/*
		while (o_rs.next()) {
			System.out.println(o_rs.getString(1));
			System.out.println(o_rs.getString(2));
			System.out.println(o_rs.getInt(3));
			System.out.println(o_rs.getString(4));
		}*/
		
		//Step 3 - We are creating a new class customerDetails where we have written some getter and setter
		//methods and we will be assigning values to the setter methods from here by creating an object
		//of customerDetails class
		//NOTE - With Getter and Setter the variable will store only the value of the last row. When we are
		//looping for the first row, the variables will hold the value of the first row. But when looping
		//for the second row, the values in the variables will get overwritten by the values of the send row
		//and so on. Thus finally the variables will hold the value of the last row only
		/*
		customerDetails o_custDet = new customerDetails();
		
		while (o_rs.next()) {
			
			o_custDet.setCourseName(o_rs.getString(1));
			o_custDet.setPurchaseDate(o_rs.getString(2));
			o_custDet.setAmount(o_rs.getInt(3));
			o_custDet.setLocation(o_rs.getString(4));
		}
		
		ObjectMapper o_objMap = new ObjectMapper();
		o_objMap.writeValue(new File("C:\\Users\\das_k\\Downloads\\custDetails.json"), o_custDet);
		*/
		
		//Step 4 - We are creating an ArrayList to add multiple rows and finally printing them to the JSON file
		ArrayList<customerDetails> o_arrlst = new ArrayList<customerDetails>();
		
		customerDetails o_custDet;
		
		while (o_rs.next()) {
			
			o_custDet = new customerDetails();
			
			o_custDet.setCourseName(o_rs.getString(1));
			o_custDet.setPurchaseDate(o_rs.getString(2));
			o_custDet.setAmount(o_rs.getInt(3));
			o_custDet.setLocation(o_rs.getString(4));
			
			o_arrlst.add(o_custDet);
		}
		
		for (int i=0; i <= o_arrlst.size()-1;i++) {
			ObjectMapper o_objMap = new ObjectMapper();
			o_objMap.writeValue(new File("C:\\Users\\das_k\\Downloads\\custDetails_row" + i + ".json"), o_arrlst.get(i));
		}
		
		conn.close();
	}
}
