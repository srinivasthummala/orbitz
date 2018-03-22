package listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerTestNG implements ITestListener {

	
	public void onFinish(ITestContext arg0) {
		System.out.println("Completed executing test " + arg0.getName());
	}

	public void onStart(ITestContext arg0) {
		System.out.println("About to begin executing Test " + arg0.getName());		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {
	    System.out.println("The name of the testcase failed is :"+arg0.getName());					
		
	}

	public void onTestSkipped(ITestResult arg0) {
	    System.out.println("The name of the testcase Skipped is :"+arg0.getName());						
	}

	public void onTestStart(ITestResult arg0) {
	    System.out.println(arg0.getName()+" testcase started");							
	}

	public void onTestSuccess(ITestResult arg0) {
	    System.out.println("The name of the testcase passed is :"+arg0.getName());							
	}

	
}
