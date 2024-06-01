// TImetable.java
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.time.Duration;

public class TImetable {

    public WebDriver getTable(WebDriver driver){
        try {
            FileWriter fileWriter = new FileWriter("Res_time.txt", true);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 페이지 로드 및 요소가 나타날 때까지 대기
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("eXPortal_PTL014Portlet_v0jwin_8_timetableData")));

            // 시간표 데이터 추출
            WebElement TT_Data = driver.findElement(By.id("eXPortal_PTL014Portlet_v0jwin_8_timetableData"));
            if (TT_Data != null) {
                for (WebElement lecture : TT_Data.findElements(By.className("lecture_list2"))) {
                    // 교시 정보 추출
                    WebElement TT_dt = lecture.findElement(By.tagName("dt"));
                    String period = TT_dt.getAttribute("title");
                    String time = TT_dt.findElement(By.tagName("span")).getAttribute("title");

                    // 과목명 및 강의실 정보 추출
                    WebElement TT_dd = lecture.findElement(By.tagName("dd")).findElement(By.tagName("span"));
                    String courseInfo = TT_dd.getAttribute("title");

                    // 출력
                    String output = "교시: " + period + "\n" +
                            "시간: " + time + "\n" +
                            "과목명 및 강의실: " + courseInfo + "\n";
                    fileWriter.write(output + "\n");
                }
            } else {
                String output = "시간표 데이터를 찾을 수 없습니다.";
                fileWriter.write(output + "\n");
            }

            fileWriter.close();
            System.out.println("TimeTable Part Done!");
        } catch (Exception e) {
            System.out.println("TimeTable Part Error!");
            e.printStackTrace();
        }

        return driver;
    }

}