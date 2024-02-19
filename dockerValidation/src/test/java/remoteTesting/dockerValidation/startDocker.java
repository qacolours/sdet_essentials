package remoteTesting.dockerValidation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Calendar;

import org.testng.annotations.Test;

import junit.framework.Assert;
import remoteTesting.utilities.reusables;

public class startDocker {
	
	reusables o_reuse = new reusables();

	@Test
	public void runBatchToStart() throws Exception {
		
		boolean flag_hubStarted = false;
		String file_name = System.getProperty("user.dir") + "\\dockerDocuments\\output.txt";
		Calendar currentTime = null;
		
		File delete_file = new File(file_name);
		
		if (delete_file.delete()) {
			System.out.println("output.txt was present and has been deleted successfully");
		} else {
			System.out.println("output.txt is not present now");
		}
		
		Runtime o_runtime = Runtime.getRuntime();
		
		//Execute .bat file when .bat file and docker_compose.yaml files are in home directory
		//o_runtime.exec("cmd /c \"start launch_docker.bat\"");
		
		//Execute .bat file when .bat file and docker_compose.yaml files are in dockerDocuments under 
		//home directory
		o_runtime.exec("cmd /c start cmd.exe /K \"cd dockerDocuments && start launch_docker.bat\"");
		
		
		currentTime = Calendar.getInstance();
		currentTime.add(Calendar.SECOND, 45);
		long expectedCompletionTime = currentTime.getTimeInMillis();
		
		//wait for some time to get the output text created
		Thread.sleep(5000);
		
		while (System.currentTimeMillis() < expectedCompletionTime) {
			BufferedReader reader = new BufferedReader(new FileReader(file_name));
			String current_line = reader.readLine();
			
			while (current_line!=null && !flag_hubStarted) {
				
				if (current_line.contains(" - Node has been added")) {
					System.out.println("Hub has been registered successfully");
					flag_hubStarted = true;
					break;
				}
				
				current_line = reader.readLine();
			}
			
			Thread.sleep(5000);
			reader.close();
			
			if (flag_hubStarted) {
				break;
			}
		}
		
		Assert.assertTrue(flag_hubStarted);
		
		//Execute .bat file when .bat file and docker_compose.yaml files are in home directory
		//o_runtime.exec("cmd /c \"start scaleUp_Chrome.bat\"");
		
		//Execute .bat file when .bat file and docker_compose.yaml files are in dockerDocuments under 
		//home directory
		o_runtime.exec("cmd /c start cmd.exe /K \"cd dockerDocuments && start scaleUp_Chrome.bat\"");
		
		Thread.sleep(30000);
	}
}
