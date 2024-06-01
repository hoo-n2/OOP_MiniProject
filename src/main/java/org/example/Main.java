package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Code\\IntelliJ\\seleniumtest\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless"); //사이트 창 안보이게 하는 설정
        WebDriver driver = new ChromeDriver(options);
        GachonLogin GL = new GachonLogin();
        TImetable Table = new TImetable();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //로딩 대기 맥시멈 10초
        TodayMeal menu = new TodayMeal();
        Subject HW = new Subject();

        //--------------------------------------------------------------------------

        driver = GL.Login(driver, 1);    //모드 1이 포털, 모드 2가 사이버캠퍼스
        wait.until(ExpectedConditions.urlContains("portal.gachon.ac.kr"));

        driver = Table.getTable(driver); //시간표 가져오는 내용
        System.out.println("");
        driver = menu.getMeal(driver);  //오늘의 학식 가져오는 내용
        System.out.println("");
        GL.Login(driver,2);
        driver = HW.chkHomework(driver);

        driver.quit();
    }
}