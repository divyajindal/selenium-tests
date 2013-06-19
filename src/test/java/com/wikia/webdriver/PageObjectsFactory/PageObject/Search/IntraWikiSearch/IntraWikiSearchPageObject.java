package com.wikia.webdriver.PageObjectsFactory.PageObject.Search.IntraWikiSearch;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.wikia.webdriver.Common.ContentPatterns.URLsContent;
import com.wikia.webdriver.Common.Core.Assertion;
import com.wikia.webdriver.Common.Core.Global;
import com.wikia.webdriver.Common.Logging.PageObjectLogging;
import com.wikia.webdriver.PageObjectsFactory.PageObject.BasePageObject;

public class IntraWikiSearchPageObject extends BasePageObject{

	public IntraWikiSearchPageObject(WebDriver driver) {
		super(driver);
	}

	@FindBy(css=".articles")
	private WebElement articles;
	@FindBy(css=".photos-and-videos")
	private WebElement photosVideos;
	@FindBy(css=".blogs")
	private WebElement blogs;
	@FindBy(css=".people")
	private WebElement people;
	@FindBy(css=".everything")
	private WebElement everything;
	@FindBy(css="#search-v2-input")
	private WebElement searchField;
	@FindBy(css="#search-v2-button")
	private WebElement searchButton;
	@FindBy(css="#advanced-link")
	private WebElement advanced;
	@FindBy(css="[value=no_filter]")
	private WebElement filterAllFiles;
	@FindBy(css="[value=is_image]")
	private WebElement filterPhotos;
	@FindBy(css="[value=is_video]")
	private WebElement filterVideos;
	@FindBy(css="[name=rank]")
	private WebElement sortingOptions;
	@FindBy(css="li.result:nth-child(1) a")
	private WebElement firstResult;
	@FindBy(css=".Results article h1")
	private List<WebElement> titles;
	@FindBy(css=".Results article")
	private List<WebElement> descriptions;
	@FindBy(css=".Results article li>a")
	private List<WebElement> urls;
	@FindBy(css=".wikia-paginator")
	private WebElement paginationContainer;
	@FindBy(css=".paginator-page")
	private List<WebElement> paginationPages;
	@FindBy(css=".paginator-next")
	private WebElement paginatorNext;
	@FindBy(css=".paginator-prev")
	private WebElement paginatorPrev;
	@FindBy(css=".results-wrapper i")
	private WebElement noResultsCaption;
	@FindBy(css=".Results a.image:not(.video.lightbox)")
	private List<WebElement> thumbnailsImages;
	@FindBy(css=".Results a.image.video.lightbox")
	private List<WebElement> thumbnailsVideos;

	public void openIntraWikiSearch() {
		getUrl(Global.DOMAIN+URLsContent.intraWikiSearchPage);
		waitForElementByElement(searchButton);
		waitForElementByElement(searchField);
		PageObjectLogging.log("openIntraWikiSearch", "intra wiki search page opened", true);
	}

	private void typeSearchQuery(String query) {
		waitForElementByElement(searchField);
		searchField.sendKeys(query);
	}

	private void clickSearchbutton() {
		waitForElementByElement(searchButton);
		searchButton.click();
	}

	public void searchFor(String query) {
		typeSearchQuery(query);
		clickSearchbutton();
		PageObjectLogging.log("searchFor", "searching for query: "+query, true, driver);
	}

	private void verifyFirstResultName(String query) {
		waitForElementByElement(firstResult);
		Assertion.assertStringContains(firstResult.getText(), query.replace("_", " "));
	}

	private void verifyDescriptions(){
		for (WebElement elem:descriptions) {
			Assertion.assertTrue(!elem.getText().isEmpty());
		}
	}

	private void verifyUrl(String query) {
		for (WebElement elem:urls){
			Assertion.assertEquals((Global.DOMAIN+URLsContent.wikiDir+query).replaceAll("(_|/)", ""),
					elem.getAttribute("href").replaceAll("(_|/)", ""));
		}
	}

	public void verifyFirstResult(String query) {
		verifyFirstResultName(query);
		verifyDescriptions();
		verifyUrl(query);
	}

	public void verifyPagination() {
		waitForElementByElement(paginationContainer);
		int i=1;
		for (WebElement elem:paginationPages) {
			Assertion.assertEquals(Integer.toString(i), elem.getText());
			i++;
		}
	}

	public void clickNextPaginator() {
		waitForElementByElement(paginatorNext);
		clickAndWait(paginatorNext);
		PageObjectLogging.log("clickNextPaginator", "next paginator clicked", true);
	}

	public void clickPrevPaginator() {
		waitForElementByElement(paginatorPrev);
		clickAndWait(paginatorPrev);
		PageObjectLogging.log("clickPrevPaginator", "prev paginator clicked", true);
	}

	public void verifyResultsCount(int i) {
		Assertion.assertNumber(i, titles.size(), "checking results count");
	}

	public void verifyNoResults() {
		waitForElementByElement(noResultsCaption);
		Assertion.assertEquals("No results found.", noResultsCaption.getText());
	}

	public void selectPhotosVideos() {
		waitForElementByElement(photosVideos);
		photosVideos.click();
		waitForElementByElement(sortingOptions);
	}

	public void selectPhotosOnly() {
		waitForElementByElement(sortingOptions);
		filterPhotos.click();
	}

	public void verifyAllResultsImages(int numberOfResults) {
		Assertion.assertEquals(numberOfResults, thumbnailsImages.size());
	}

	public void verifyAllResultsVideos(int numberOfResults) {
		Assertion.assertEquals(numberOfResults, thumbnailsVideos.size());
	}

	public void selectVideosOnly() {
		waitForElementByElement(sortingOptions);
		filterVideos.click();
	}

	public void verifyNamespacesInTitles(String nameSpace) {
		for (WebElement elem:titles) {
			Assertion.assertStringContains(elem.getText(), nameSpace);
		}
	}
}
