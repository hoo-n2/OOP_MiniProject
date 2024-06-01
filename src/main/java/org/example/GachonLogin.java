package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GachonLogin {

    public WebDriver Login(WebDriver driver, int PorC){

        String URL = null;

        if(PorC == 1) URL = "https://sso.gachon.ac.kr/svc/tk/Auth.do?ac=Y&ifa=N&id=portal&"; //포털 로그인
        else if(PorC == 2) URL = "https://sso.gachon.ac.kr/svc/tk/Auth.do?ac=Y&ifa=N&id=cyber&RelayState=/exsignon/sso/sso.php"; //사캠 로그인
        else{
            System.out.println("사이트 모드 설정 오류");
            System.exit(-1);
        }

        try {
            driver.get(URL);

            ID_PWD user = new ID_PWD();
            WebElement E_ID = driver.findElement(By.id("user_id"));
            WebElement E_PWD = driver.findElement(By.id("user_password"));
            WebElement L_BTN = driver.findElement(By.className("btn_login"));

            E_ID.sendKeys(user.getID());
            E_PWD.sendKeys(user.getPWD());
            L_BTN.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //맥시멈 10초

            try{    //비번 변경 30일 지남이 뜨면 클릭하고 안뜨면 예외처리로 바로 스킵
                WebElement Nochg = wait.until(ExpectedConditions.elementToBeClickable(By.className("btnNo")));
                Nochg.click();
            }catch (NoSuchElementException e){}


        } catch (Exception e) {
            //e.printStackTrace();    //오류 체크용
        }finally {
            return driver;
        }
    }
}
