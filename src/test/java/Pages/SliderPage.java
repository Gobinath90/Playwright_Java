package Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SliderPage extends BasePage {

	public SliderPage(Page page) {
		super(page);
	}

	public void navigateTo() {
		page.navigate("https://www.lambdatest.com/selenium-playground/");
        clickLinkByName("Drag & Drop Sliders");
	}

	public void verifyDefaultValue() throws Exception {
        assertContainsText("#slider3", "Default value 15");
		Thread.sleep(2000);

	}

	public void setSliderValue(String value) {
		page.locator("#slider3").getByRole(AriaRole.SLIDER).fill(value);
		System.out.println("Slider value set to: " + value);

	}

	public void verifySliderValue(String expectedValue) throws Exception {
		Thread.sleep(2000);
        assertContainsText("#rangeSuccess", expectedValue);
		System.out.println("Validation Slider Value:" + expectedValue);

	}

}
