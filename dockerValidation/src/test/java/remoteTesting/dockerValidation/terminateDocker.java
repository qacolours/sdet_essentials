package remoteTesting.dockerValidation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Calendar;

import org.testng.annotations.Test;

import junit.framework.Assert;
import remoteTesting.utilities.reusables;

public class terminateDocker {
	
	reusables o_reusaReusables = new reusables();

	@Test
	public void runBatchToTerminate() throws Exception {
		
		boolean flag_hubStopped = false;
		String file_name = System.getProperty("user.dir") + "\\dockerDocuments\\output.txt";
		Calendar currentTime = null;
		
		Runtime o_runtime = Runtime.getRuntime();
		
		//Execute .bat file when .bat file and docker_compose.yaml files are in home directory
		//o_runtime.exec("cmd /c \"start terminate_docker.bat\"");
		
		//Execute .bat file when .bat file and docker_compose.yaml files are in dockerDocuments under 
		//home directory
		o_runtime.exec("cmd /c start cmd.exe /K \"cd dockerDocuments && start terminate_docker.bat\"");
		
		
		currentTime = Calendar.getInstance();
		currentTime.add(Calendar.SECOND, 45);
		long expectedCompletionTime = currentTime.getTimeInMillis();
		
		//wait for some time to get the output text created
		Thread.sleep(5000);
		
		while (System.currentTimeMillis() < expectedCompletionTime) {
			BufferedReader reader = new BufferedReader(new FileReader(file_name));
			String current_line = reader.readLine();
			
			while (current_line!=null && !flag_hubStopped) {
				
				if (current_line.contains("selenium-hub exited")) {
					System.out.println("Hub has been terminated successfully");
					flag_hubStopped = true;
					break;
				}
				
				current_line = reader.readLine();
			}
			
			Thread.sleep(5000);
			reader.close();
			
			if (flag_hubStopped) {
				break;
			}
		}
		
		Assert.assertTrue(flag_hubStopped);
		
		String sourceLogFilePath = file_name;
		String destLogFilePath = System.getProperty("user.dir") + "\\backup_logs\\output_" + o_reusaReusables.getCurrentTimestampInMillis() + ".txt";

		o_reusaReusables.copyFileToDestination(sourceLogFilePath, destLogFilePath);
		
		File delete_file = new File(sourceLogFilePath);
		
		if (delete_file.delete()) {
			System.out.println("output.txt has been deleted successfully");
			
			o_runtime.exec("cmd /c \"start kill_cmd.bat\"");
		} else {
			System.out.println("output.txt has not been deleted");
		}
	}
}
