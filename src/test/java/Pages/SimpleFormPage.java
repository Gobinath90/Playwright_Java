package Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SimpleFormPage extends BasePage {

    private String baseUrl;

	public SimpleFormPage(Page page, String baseUrl) {
		super(page);
        this.baseUrl = baseUrl;
	}

	public void navigateToSimpleFormDemo() {
		 // Debug statement to check baseUrl value
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("Base URL is not set");
        }
        navigateTo(baseUrl);
        clickLinkByName("Simple Form Demo");
        assertContainsText("h1", "Simple Form Demo");
		
		 String currentUrl = page.url();
		    if (!currentUrl.contains("simple-form-demo")) {
		        throw new AssertionError("URL does not contain 'simple-form-demo'. Current URL: " + currentUrl);
		    } else {
		        System.out.println("URL validation passed: " + currentUrl);
		    }
	}

	public void fillMessageAndSubmit(String message) {
		page.getByPlaceholder("Please enter your Message").fill(message);
        System.out.println("Verify the Entered Text : " + message);

		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get Checked Value")).click();
		
	}

	public void verifyMessage(String expectedMessage) {
        assertContainsText("#message", expectedMessage);
        System.out.println("Verify the TEXT validation passed : " + expectedMessage);
	}
}
