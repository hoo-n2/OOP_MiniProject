// Subject.java
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;


public class Subject {
    public WebDriver chkHomework(WebDriver driver){
        HWFinder HF = new HWFinder();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //맥시멈 10초
        try {
            FileWriter fileWriter = new FileWriter("Res_hw.txt", true);
            driver.get("https://cyber.gachon.ac.kr");//사캠으로 이동 후 열릴때까지 대기
            wait.until(ExpectedConditions.urlContains("cyber.gachon.ac.kr"));

            WebElement classGroup = driver.findElement(By.className("coursemos-layout-0"));

            // html코드상 위의 클래스 명을 가진 ul태그 하위로 li태그를 가진 과목들 존재
            List<WebElement> Eachclass = classGroup.findElements(By.tagName("li"));

            // 링크와 과목명을 저장할 리스트
            List<String> links = new ArrayList<>();
            List<String> CourseName = new ArrayList<>();

            // 각 li 태그 내에서 과목별 링크를 가진 a태그 검색 후 과목명과 링크 추출
            for (WebElement li : Eachclass) {
                WebElement I_Link = li.findElement(By.cssSelector("div a"));
                String link = I_Link.getAttribute("href");
                WebElement I_CourseName = li.findElement(By.cssSelector("div a .course-title h3"));
                String courseTitle = I_CourseName.getText();

                links.add(link);
                CourseName.add(courseTitle);
            }

            // 저장한 링크와 과목명으로 순차접근
            for (int i = 0; i < links.size(); i++) {
                String link = links.get(i);
                String courseTitle = CourseName.get(i);

                driver.get(link);   //각각의 과목 페이지로 이동
                String output = courseTitle + ": " + link;
                fileWriter.write("\n");
                fileWriter.write(output + "\n");

                HF.findHW(driver, fileWriter, courseTitle);    //이후 해당 과목 페이지에서 과제 추출

                // 페이지가 로드될 때까지 잠시 대기
                Thread.sleep(100);
            }
            fileWriter.close();
            System.out.println("Subject Part Done!");
        } catch (Exception e) {
            System.out.println("Subject Part Error!");
            e.printStackTrace();    //에러 체크용
        }
        return driver;
    }
}
