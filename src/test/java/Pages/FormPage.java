package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FormPage {
	private Page page;

	public FormPage(Page page) {
		this.page = page;
	}

	private Locator inputFormSubmitLink() {
		return page.locator("//a[text()='Input Form Submit']");
	}

	private Locator nameInput() {
		return page.locator("#name");
	}

	private Locator emailInput() {
		return page.locator("#inputEmail4");
	}

	private Locator passwordInput() {
		return page.locator("#inputPassword4");
	}

	private Locator companyInput() {
		return page.locator("#company");
	}

	private Locator websiteInput() {
		return page.locator("#websitename");
	}

	private Locator cityInput() {
		return page.locator("#inputCity");
	}

	private Locator address1Input() {
		return page.locator("#inputAddress1");
	}

	private Locator address2Input() {
		return page.locator("#inputAddress2");
	}

	private Locator stateInput() {
		return page.locator("#inputState");
	}

	private Locator zipInput() {
		return page.locator("#inputZip");
	}

	private Locator submitButton() {
		return page.locator("button:has-text('Submit')");
	}

	private Locator successMessage() {
		return page.locator("//p[@class='success-msg hidden']");
	}

	public void clickInputForm() {
		inputFormSubmitLink().click();
	}

	public void submitEmptyForm() {
		submitButton().click();
	}

	public void fillForm(String name, String email, String password, String company, String website, String country,
			String city, String address1, String address2, String state, String zip) {
		nameInput().fill(name);
		emailInput().fill(email);
		passwordInput().fill(password);
		companyInput().fill(company);
		websiteInput().fill(website);
		page.selectOption("select[name='country']", country);
		cityInput().fill(city);
		address1Input().fill(address1);
		address2Input().fill(address2);
		stateInput().fill(state);
		zipInput().fill(zip);
	}

	public void submitForm() {
		submitButton().click();
	}

	public String getSuccessMessage() {
		return successMessage().textContent();
	}
}
