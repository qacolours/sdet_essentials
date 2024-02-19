package sdet_advanced;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;



public class fetchDataFromMySql {
	
	@Test
	public void getMySqlDataInJsonFormat() throws Exception {
		
		Connection conn = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Business", "root", "root");
		
		//create object of the statement class which will help us to execute queries
		Statement o_stmnt = conn.createStatement();
		ResultSet o_rs = o_stmnt.executeQuery("select * from CustomerInfo where purchasedDate=CURDATE()-2 and Location ='Asia';");
		
		ArrayList<customerDetails> o_arrlst = new ArrayList<customerDetails>();
		Gson o_javaObjToJsonString = new Gson();
		JSONArray o_createJsonArray = new JSONArray();
		
		ObjectMapper o_objMap = new ObjectMapper();
		
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
			
			//o_objMap.writeValue(new File("C:\\Users\\das_k\\Downloads\\custDetails_row" + i + ".json"), o_arrlst.get(i));
			
			String jsonString = o_javaObjToJsonString.toJson(o_arrlst.get(i));
			o_createJsonArray.add(jsonString);
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data",o_createJsonArray);
		
		String unescapedJSONString = StringEscapeUtils.unescapeJava(jsonObj.toJSONString()).replace("\"{", "{").replace("}\"", "}");
		
		
		System.out.println(unescapedJSONString);
		
		FileWriter o_write = new FileWriter("C:\\sdet_automation\\sdet_advanced\\output_json\\custInfo.json");
		o_write.write(unescapedJSONString);
		o_write.close();
		
		conn.close();
	}
}
