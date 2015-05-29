import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.io.Files;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by ZuoYun on 5/29/15. Time: 4:09 PM Information:
 */
public class DemoTest {

  public DemoTest() {
    ;
  }

  @Test
  public void demo() throws MalformedURLException, InterruptedException {

    WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());

    driver.get("http://renren.com/");
    System.out.println(driver.getTitle());

    driver.manage().timeouts().setScriptTimeout(1L, TimeUnit.SECONDS);

    WebElement we = driver.findElement(By.id("email"));
    we.sendKeys("zuo.zhong@163.com");

    WebElement we_ = driver.findElement(By.id("password"));
    we_.sendKeys("zuoyun1992");

    WebElement subBtn = driver.findElement(By.id("login"));
    subBtn.submit();

    Thread.sleep(2000L);

    String html = driver.getPageSource();
    Document doc = Jsoup.parse(html);
    System.out.println(driver.getCurrentUrl());

    waitWhenUrlPredicate(driver, "");

    for (Element element : doc.select("p.blog-text")) {
      System.out.println(StringUtils.center("数据", 20, "="));
      System.out.println(element.text());
      System.out.println(StringUtils.center("", 20, "="));
    }

    driver.quit();
  }

  @Test
  public void demo2() throws IOException, InterruptedException {

    WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());

    driver.get("https://login.taobao.com/member/login.jhtml");

    driver.findElement(By.id("TPL_username_1")).sendKeys("magican18");
    driver.findElement(By.id("TPL_password_1")).sendKeys("raytest1");
    driver.findElement(By.id("J_SubmitStatic")).submit();

    driver.findElement(By.id("J_StandardCode_m"));

    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

    Files.copy(screenshot, new File("snapshot.jpg"));

//    waitWhenUrlPredicate(driver, "index");

//    driver.get("http://fuwu.taobao.com/ser/my_service.htm");
//    System.out.println(driver.getCurrentUrl());
//    driver.quit();
  }

  private void waitWhenUrlPredicate(WebDriver driver, final String contains) {
    new WebDriverWait(driver, 10).until(new Predicate<WebDriver>() {
      public boolean apply(WebDriver input) {
        boolean isTrue = input.getCurrentUrl().contains(contains);
        if (isTrue) {
          System.out.println("等待请求" + input.getCurrentUrl() + "成功!");
        }
        return isTrue;
      }
    });
  }


}