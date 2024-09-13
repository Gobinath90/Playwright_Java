package TestDemo;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import Pages.FormPage;

public class FormPageTest extends BaseTest {
	@Test
	public void testFormSubmission() {

		FormPage formPage = new FormPage(page);
		page.navigate(baseUrl);
		formPage.clickInputForm();

		// Submit the form without filling any data to trigger the error message
		formPage.submitEmptyForm();
		System.out.println("Submitted the form without filling any data.");

		page.waitForTimeout(2000);

		String validationMessage = (String) page.locator("input[name='name']:invalid")
				.evaluate("el => el.validationMessage");

		switch (validationMessage) {
	    case "Please fill out this field.":
	        System.out.println("Validation message is displayed correctly: " + validationMessage);
	        break;
	    case "Please fill in this field.":
	        System.out.println("Validation message is displayed correctly: " + validationMessage);
	        break;

	    default:
	        System.out.println("Validation message is not displayed as expected: " + validationMessage);
	        break;
	}

		formPage.fillForm("John Doe", "john.doe@example.com", "Password123!", "Example Corp", "https://example.com",
				"United States", "Sydney", "123 George Street", "Suite 456", "New South Wales (NSW)", "2000");
		page.waitForTimeout(2000);

		formPage.submitForm();

		// Verify the success message
		String successMessage = formPage.getSuccessMessage();
		System.out.println("Success Message: " + successMessage);
		page.waitForTimeout(2000);

		assertEquals("Thanks for contacting us, we will get back to you shortly.", successMessage,
				"Success message is not as expected");

	}

}
