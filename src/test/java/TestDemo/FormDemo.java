package TestDemo;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import Pages.SimpleFormPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class FormDemo extends BaseTest {

	private SimpleFormPage simpleFormPage;

	@Step("Execute form functionality test")
	@Test
	public void testFormFunctionality() {
		simpleFormPage = new SimpleFormPage(page, baseUrl); // Ensure baseUrl is passed correctly
		attachScreenshot("Before Navigation");
		navigateAndTestForm();
		attachScreenshot("After Form Submission");

	}

	@Step("Navigate to Simple Form Demo page and test form functionality")
	private void navigateAndTestForm() {
		simpleFormPage.navigateToSimpleFormDemo();
		attachScreenshot("After Navigation");
		String value = "Welcome to LambdaTest";
		fillMessageAndSubmit(value);
		attachScreenshot("After Message Submission");
		verifyMessage(value);
	}

	@Step("Fill in message and submit the form with value: {value}")
	private void fillMessageAndSubmit(String value) {
		simpleFormPage.fillMessageAndSubmit(value);

	}

	@Step("Verify the submitted message is: {value}")
	private void verifyMessage(String value) {
		simpleFormPage.verifyMessage(value);
		attachScreenshot("Message verified");
	}

	private void attachScreenshot(String name) {
	    System.out.println("Attaching screenshot with name: " + name);
	    try {
	        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
	        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
	        System.out.println("Screenshot attached successfully.");
	    } catch (Exception e) {
	        System.err.println("Error while attaching screenshot: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
