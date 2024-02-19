package sdet_advanced;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class extractJson {

	//extractJsonToJavaObject
	
	//@Test
	public static void main(String[] args) throws Exception {
		ObjectMapper obj_map = new ObjectMapper();
		customerDetails obj_data = obj_map.readValue(new File("C:\\Users\\das_k\\Downloads\\custDetails_row1.json"), customerDetails.class);
		
		System.out.println(obj_data.getCourseName());
	}
}
