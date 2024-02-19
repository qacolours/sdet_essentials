package remoteTesting.utilities;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class reusables {

	public void copyFileToDestination(String sourceFilePath, String destStringPath) throws Exception {
		
		File src = new File(sourceFilePath);
		File dest = new File(destStringPath);
		
		FileUtils.copyFile(src,dest);
	}
	
	public long getCurrentTimestampInMillis() {
		long millis = System.currentTimeMillis();
		return millis;
	}
}
