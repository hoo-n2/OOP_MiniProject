// HWFinder.java
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HWFinder {
    private SimpleDateFormat Deadline = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public WebDriver findHW(WebDriver driver, FileWriter fileWriter, String courseTitle) {
        try {
            Date curr = new Date(); //날짜 설정
            WebElement totalSections = driver.findElement(By.className("total_sections"));
            //"주차 별 학습 활동"탭의 클래스 명이 total_sections

            // 퀴즈의 클래스가 다음 3개이기에 activity quiz modtype_quiz 검색 후 저장
            List<WebElement> quizs = totalSections.findElements(By.cssSelector(".activity.quiz.modtype_quiz"));
            for (WebElement quiz : quizs) {
                if (deadCheck(quiz, curr)) {
                    String quizName = quiz.findElement(By.cssSelector(".instancename")).getText();
                    String output = courseTitle + " - 퀴즈: " + quizName;
                    fileWriter.write(output + "\n");
                }
            }

            //과제의 클래스가 다음 3개이기에 activity assign modtype_assign 탐색 후 저장
            List<WebElement> assigns = totalSections.findElements(By.cssSelector(".activity.assign.modtype_assign"));
            for (WebElement assign : assigns) {
                if (deadCheck(assign, curr)) {
                    String assignmentName = assign.findElement(By.cssSelector(".instancename")).getText();
                    String output = courseTitle + " - 과제: " + assignmentName;
                    fileWriter.write(output + "\n");
                }
            }

            //영상 강의의 클래스가 다음 3개 이기에 activity vod modtype_vod 검색 후 저장
            List<WebElement> vods = totalSections.findElements(By.cssSelector(".activity.vod.modtype_vod"));
            for (WebElement vod : vods) {
                if (deadCheck(vod, curr)) {
                    String vodName = vod.findElement(By.cssSelector(".instancename")).getText();
                    String output = courseTitle + " - 영상: " + vodName;
                    fileWriter.write(output + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();    //에러 체크용
        }
        return driver;
    }

    private boolean deadCheck(WebElement HW, Date curr) {
        try {
            WebElement DL_include = null;
            try{    //퀴즈&과제는 마감일이 영상과 다른 형식의 구조로 작성되어있어 구분해줌
                DL_include = HW.findElement(By.className("text-ubstrap"));  //영상용
            }catch (NoSuchElementException e){
                DL_include = HW.findElement(By.className("displayoptions"));//퀴즈 & 과제용
            }

            String deadRange = DL_include.getText().trim(); //기간 범위만 남기기

            String[] timeRange = deadRange.split("~");  //시작 기간 지우기

            if (timeRange.length < 2) {
                return false;
            }
            String endTime = timeRange[1].trim();   //종료일의 공백 제거

            if(endTime.compareTo("No time limit") == 0 || endTime.compareTo("기간 제한 없음") == 0) return false;
            //기간 제한 없는 경우의 오류 발생 방지

            Date DL_Date = Deadline.parse(endTime); //텍스트로 된 기간의 형식을 전환해줌

            return !curr.after(DL_Date);

        } catch (ParseException e) {
            e.printStackTrace();        //에러 체크용
            return false;
        } catch (NoSuchElementException e) {
            // 마감기한이 없는 경우 해당 활동은 무시
            return false;
        }
    }
}