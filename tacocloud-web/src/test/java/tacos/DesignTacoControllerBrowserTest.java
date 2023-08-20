package tacos;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Disabled("Reintroduce this test after fixing Spring Boot config")
public class DesignTacoControllerBrowserTest {
  
  private static ChromeDriver browser;
  
  @LocalServerPort
  private int port;
  
  @Autowired
  TestRestTemplate rest;

  //== 테스트 클래스에 있는 어떤 테스트를 처음 실행하기 전 한 번만 실행
  @BeforeAll
  public static void openBrowser() {
    browser = new ChromeDriver();
    browser.manage().timeouts()
        .implicitlyWait(10, TimeUnit.SECONDS);
  }

  //== 테스트 클래스에 있는 테스트를 모두 실행하고 난 후에 한 번만 실행됨.
  @AfterAll
  public static void closeBrowser() {
    browser.quit();
  }
  
  @Test
  @Disabled("TODO: Need to get around authentication in this test")
  public void testDesignATacoPage() throws Exception {
    browser.get("http://localhost:" + port + "/design");

    List<WebElement> ingredientGroups = browser.findElements(By.className("ingredient-group"));
    assertEquals(5, ingredientGroups.size());
    
    WebElement wrapGroup = ingredientGroups.get(0);
    List<WebElement> wraps = wrapGroup.findElements(By.tagName("div"));
    assertEquals(2, wraps.size());
    assertIngredient(wrapGroup, 0, "FLTO", "Flour Tortilla");
    assertIngredient(wrapGroup, 1, "COTO", "Corn Tortilla");
    
    WebElement proteinGroup = ingredientGroups.get(1);
    List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
    assertEquals(2, proteins.size());
    assertIngredient(proteinGroup, 0, "GRBF", "Ground Beef");
    assertIngredient(proteinGroup, 1, "CARN", "Carnitas");
  }
  
  private void assertIngredient(WebElement ingredientGroup, 
                                int ingredientIdx, String id, String name) {
    List<WebElement> proteins = ingredientGroup.findElements(By.tagName("div"));
    WebElement ingredient = proteins.get(ingredientIdx);
    assertEquals(id, 
        ingredient.findElement(By.tagName("input")).getAttribute("value"));
    assertEquals(name, 
        ingredient.findElement(By.tagName("span")).getText());
  }
  
}
