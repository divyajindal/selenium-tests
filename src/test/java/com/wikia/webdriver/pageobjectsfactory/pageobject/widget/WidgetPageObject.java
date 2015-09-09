package com.wikia.webdriver.pageobjectsfactory.pageobject.widget;

import com.wikia.webdriver.common.contentpatterns.MercuryMessages;
import com.wikia.webdriver.common.core.api.ArticleContent;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.mercury.BasePageObject;

import org.openqa.selenium.WebDriver;

/**
 * @ownership: Content X-Wing
 */
public abstract class WidgetPageObject extends BasePageObject {

  protected String tagName;

  protected WidgetPageObject(WebDriver driver) {
    super(driver);
  }

  protected abstract boolean isTagLoadedOnMercury();

  protected abstract boolean isTagLoadedOnOasis();

  protected String getTagName() {
    return tagName;
  }

  private String getArticleName() {
    return tagName.substring(0, 1).toUpperCase() + tagName.substring(1).toLowerCase() + "Widget";
  }

  private String getTag() {
    return "<" + getTagName() + ">";
  }

  public void createAndNavigate(String wikiUrl) {
    ArticleContent articleContent = new ArticleContent();
    articleContent.clear(getArticleName());
    articleContent.push(getTag(), getArticleName());

    openMercuryArticleByName(wikiUrl, getArticleName());
  }

  public boolean isLoadedOnMercury() {
    boolean result = isTagLoadedOnMercury();
    PageObjectLogging.log(getTagName(), MercuryMessages.VISIBLE_MSG, result);
    return result;
  }

  public boolean isLoadedOnOasis() {
    boolean result = isTagLoadedOnOasis();
    PageObjectLogging.log(getTagName(), MercuryMessages.VISIBLE_MSG, result);
    return result;
  }
}
