// TodayMeal.java
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.time.Duration;
import java.util.List;

public class TodayMeal {
    public WebDriver getMeal(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            FileWriter fileWriter = new FileWriter("Res_meal.txt", true);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("eXPortal_PTL014Portlet_v0jwin_8_timetableData")));

            Thread.sleep(500);

            String[][] rests = {
                    {"비전타워", "4"},
                    {"교육대학원", "6"},
                    {"학생생활관", "8"}
            };

            for (String[] rest : rests) {
                String restName = rest[0];
                String restID = rest[1];

                WebElement selectRest = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//li[@data-id='" + restID + "']")));
                selectRest.click();     //비타-교대-3긱 각각의 메뉴 버튼 클릭해서 식당 선택

                WebElement MenuList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("eXPortal_PTL005Portlet_v0jwin_23_meal_dataList")));

                String output = "학식당: " + restName;
                fileWriter.write(output + "\n");

                List<WebElement> menus = MenuList.findElements(By.tagName("tr"));
                for (WebElement menu : menus) {
                    output = menu.getText().replace("\n", ", ");
                    fileWriter.write(output + "\n");
                }
                fileWriter.write("\n");
            }

            fileWriter.close();
            System.out.println("TodayMeal Part Done!");
        } catch (Exception e) {
            System.out.println("TodayMeal Part Error!");
            e.printStackTrace();    //에러 체크용
        }
        return driver;
    }
}