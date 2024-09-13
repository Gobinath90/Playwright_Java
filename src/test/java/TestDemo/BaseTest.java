package TestDemo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class BaseTest {
	protected static Playwright playwright;
	protected static Browser browser;
	protected static Page page;
	protected static String baseUrl = "https://www.lambdatest.com/selenium-playground/"; // Ensure this is correctly set

	@Parameters({ "remote", "browser", "version", "platform", "width", "height" })
	@BeforeMethod
	@Step("Setting up the test environment")
	public void setup(String remote, String browserName, String browserVersion, String platform, int width,
			int height) {
		Allure.addAttachment("Test Setup Parameters",
				String.format("Remote: %s, Browser: %s, Version: %s, Platform: %s, Width: %d, Height: %d", remote,
						browserName, browserVersion, platform, width, height));
		Allure.step("Setting up test environment");

		if ("true".equals(remote)) {
			setupRemote(browserName, browserVersion, platform);
		} else {
			setupLocal(browserName, browserVersion, width, height);
		}
	}

	private void setupLocal(String browserName, String browserVersion, int width, int height) {
		Allure.step("Setting up local environment for browser: " + browserName);
		try {
			playwright = Playwright.create();
			BrowserType browserType = getBrowserType(playwright, browserName);

			// Set launch options to run in headed mode
			LaunchOptions launchOptions = new LaunchOptions();
			launchOptions.setHeadless(false); // Run in headed mode

			if (!"latest".equalsIgnoreCase(browserVersion)) {
				launchOptions.setChannel(browserVersion);
			}
			browser = browserType.launch(launchOptions);
			page = browser.newPage();
			page.setViewportSize(width, height);
			Allure.addAttachment("Local Browser Setup", String.format("Browser: %s, Version: %s, Width: %d, Height: %d",
					browserName, browserVersion, width, height));
		} catch (Exception e) {
			Allure.addAttachment("Setup Local Error", e.getMessage());
			throw e;
		}

	}

	private void setupRemote(String browserName, String browserVersion, String platform) {
		Allure.step("Setting up remote environment for browser: " + browserName);
        try {
		playwright = Playwright.create();
		BrowserType browserType = playwright.chromium(); // Change to other types if needed
		JsonObject capabilities = new JsonObject();
		JsonObject ltOptions = new JsonObject();

		String user = "gobi90.test";
		String accessKey = "O2iIv7RhoDt1NTw3xQJPr2tUNAEoxSvyWsl3kt42xu7AjCzgDk";

		capabilities.addProperty("browserName", browserName);
		capabilities.addProperty("browserVersion", browserVersion);
		ltOptions.addProperty("platform", platform);
		ltOptions.addProperty("name", "Playwright Test");
		ltOptions.addProperty("build", "Playwright Java Build");
		ltOptions.addProperty("user", user);
		ltOptions.addProperty("accessKey", accessKey);
		capabilities.add("LT:Options", ltOptions);

		String cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + capabilities.toString();
		browser = browserType.connect(cdpUrl);
		page = browser.newPage();
		 Allure.addAttachment("Remote Browser Setup", String.format("Browser: %s, Version: %s, Platform: %s", 
                 browserName, browserVersion, platform));
     } catch (Exception e) {
         Allure.addAttachment("Setup Remote Error", e.getMessage());
         throw e;
     }
	}

	private BrowserType getBrowserType(Playwright playwright, String browserName) {
		switch (browserName.toLowerCase()) {
		case "pw-firefox":
			return playwright.firefox();
		case "pw-webkit":
			return playwright.webkit();
		case "chrome":
		default:
			return playwright.chromium();
		}
	}

	@AfterMethod
	public void tearDown() {
        Allure.step("Tearing down the test environment");
		// System.out.println("Starting teardown...");

		try {
			if (browser != null) {
				browser.close();
                Allure.addAttachment("Browser Closed", "Browser has been closed.");
				// System.out.println("Browser closed.");
				browser = null;
			}
		} catch (Exception e) {
            Allure.addAttachment("Browser Closure Error", e.getMessage());
			System.err.println("Error closing browser: " + e.getMessage());
		}

		try {
			if (playwright != null) {
				playwright.close();
                Allure.addAttachment("Playwright Closed", "Playwright has been closed.");
				// System.out.println("Playwright closed.");
				playwright = null;
			}
		} catch (Exception e) {
            Allure.addAttachment("Playwright Closure Error", e.getMessage());
			System.err.println("Error closing Playwright: " + e.getMessage());
		}
        Allure.step("Teardown complete.");
		// System.out.println("Teardown complete.");
	}

}
