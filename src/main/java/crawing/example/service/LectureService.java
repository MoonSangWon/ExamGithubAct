package crawing.example.service;

import crawing.example.domain.Lectures;

import crawing.example.repository.LecturesRepo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service    @RequiredArgsConstructor @Slf4j
public class LectureService implements LectureServiceIn{
    private final LecturesRepo lecturesRepo;
    List<WebElement> lectures =null;
    List<WebElement> webProfessor = null;
    List<WebElement> webLocation = null;
    @PostConstruct
    public void getLectureService() throws InterruptedException, IOException {
        System.setProperty("Webdriver.chrome.driver","assets/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");

        driver.get("https://everytime.kr/login");
        log.info("페이지 로딩 중...{}",driver.getCurrentUrl());
        try {
            //아이디 입력//
            WebElement text_box_id = driver.findElement(By.name("userid"));
            text_box_id.clear();
            text_box_id.sendKeys("kuim75");
            //비밀번호 입력//
            WebElement text_box_pw = driver.findElement(By.name("password"));
            text_box_pw.clear();
            text_box_pw.sendKeys("anstkd1257!@");
            //로그인 버튼 클릭//
            WebElement btn_click = driver.findElement(By.xpath("//*[@id=\"container\"]/form/p[3]/input"));
            btn_click.click();
            log.info("로그인 성공");

            //2022/1학기 시간표로 이동//
            driver.navigate().to("https://everytime.kr/timetable/2022/1");

            log.info("시간표 이동 중...{}",driver.getCurrentUrl());
            log.info("시간표 불러오는 중 2022년 1학기...");

            driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(2000, TimeUnit.SECONDS);

            log.info("시간표 불러오는 중 2022년 1학기 시간표");

            lectures = driver.findElements(By.xpath("//*[@id=\"container\"]/div/div[2]/table/tbody/tr/td[2]/div[1]/div[1]/ul"));
            lectures = driver.findElements(By.cssSelector("h3"));


            String [] lecture = new String[30];
            String [] professor = new String[30];
            String [] location = new String[30];

            int i=0;

            for(WebElement webElement : lectures) {
                lecture[i] = webElement.getText();
                i++;
            }
            List<String> lecturesArr = Arrays.asList(lecture)
                    .stream()
                    .distinct() //중복 검사
                    .collect(Collectors.toList());
            lecturesArr.removeAll(Arrays.asList("",null));


            lectures = driver.findElements(By.cssSelector("em"));
            i=0;

            for(WebElement webElement : lectures) {
                professor[i] = webElement.getText();
                i++;
            }
            List<String> professorArr = Arrays.asList(professor)
                    .stream()
                    .distinct() //중복 검사
                    .collect(Collectors.toList());
            professorArr.removeAll(Arrays.asList("",null));
            for(int j=0;j<professorArr.size();j++){

            }
            i=0;

            lectures = driver.findElements(By.cssSelector("span"));
            for(WebElement webElement : lectures) {
                location[i] = webElement.getText();
                i++;
            }
            List<String> locationArr = Arrays.asList(location)
                    .stream()
                    .distinct() //중복 검사
                    .collect(Collectors.toList());
            locationArr.removeAll(Arrays.asList("",null));
            locationArr.removeAll(Arrays.asList("에브리타임"));
            locationArr.removeAll(Arrays.asList("인제대"));
            locationArr.removeAll(Arrays.asList("22"));

            for(int j=0;j<locationArr.size();j++){
                saveLecture(new Lectures(null,lecturesArr.get(j),professorArr.get(j),locationArr.get(j)));
            }
        }finally {
            driver.close();
            driver.quit();
        }
    }

    @Override
    public Lectures saveLecture(Lectures lecture) {
        log.info("{} {} {}",lecture.getLecture(),lecture.getProfessor(),lecture.getLocation());
        return lecturesRepo.save(lecture);
    }

    @Override
    public Lectures getLecture(String lecture) {
        return lecturesRepo.findByLecture(lecture);
    }

    @Override
    public List<Lectures> getLectures() {
        return lecturesRepo.findAll();
    }
}
