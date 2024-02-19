package remoteTesting.dockerValidation;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class baseTest {

	@BeforeSuite
	public void setupEnvironment() throws Exception {
		startDocker o_start = new startDocker();
		o_start.runBatchToStart();
	}
	
	@AfterSuite
	public void teardownEnvironment() throws Exception {
		terminateDocker o_terminate = new terminateDocker();
		o_terminate.runBatchToTerminate();
	}
}
