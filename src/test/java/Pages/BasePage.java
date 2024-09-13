package Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }
    
    protected void navigateTo(String url) {
        page.navigate(url);
    }
    
    protected void assertContainsText(String selector, String expectedText) {
        PlaywrightAssertions.assertThat(page.locator(selector)).containsText(expectedText);
    }

    protected void clickLinkByName(String linkName) {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(linkName)).click();
    }
}
