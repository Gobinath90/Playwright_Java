package TestDemo;

import org.testng.annotations.Test;

import Pages.SliderPage;

public class SliderPageTest extends BaseTest {

	@Test
	public void testSliderPage() throws Exception {
		SliderPage sliderPage = new SliderPage(page);
		sliderPage.navigateTo();
		sliderPage.verifyDefaultValue();
		sliderPage.setSliderValue("95");
		sliderPage.verifySliderValue("95");

	}
}
