package org.shashi.tele;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Logger;


public class GetReel {

    private final static Logger logger =  Logger.getLogger( GetReel.class.getName() );

    public File getReels(String linkofReel){
        // Configuring the system properties of chrome driver

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\Downloads\\ReelScraper\\ReelScraper\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        //System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

        //FirefoxOptions options =   new FirefoxOptions();
        //options.setHeadless(true);
        options.addArguments("window-size=1920,1080");
        //options.set

        //options.setBinary("/home/shashi/Downloads/ReelScraper-master/google-chrome-stable_current_amd64.deb");
        WebDriver driver = new ChromeDriver(options);
        //WebDriver driver = new FirefoxDriver(options);

        driver.get("https://instafinsta.com/reels");

        //driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);


        Duration duration = Duration.ofSeconds(10,0);

        WebDriverWait wait = new WebDriverWait(driver, duration);

        WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(20,0));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("link")));

        WebElement link = driver.findElement(By.id("link"));

        //LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);


        link.clear();

        link.sendKeys(linkofReel);

        wait1.until(ExpectedConditions.presenceOfElementLocated(By.id("get")));

        WebElement search =  driver.findElement(By.id("get"));


        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(By.id("get")));

        search.click();

        By byLocator = By.tagName("video");
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .presenceOfElementLocated(byLocator));
        WebElement findElement = driver.findElement(byLocator);
        System.out.println(findElement.getText());
        System.out.println("Src file : " + findElement.getAttribute("src"));

        wait1.until(ExpectedConditions.presenceOfElementLocated(By.tagName("video")));

        WebElement video = driver.findElement(By.tagName("video"));


        WebElement download = driver.findElement(By.xpath("//*[@id=\"result\"]/div/div/div[2]/a"));


        WebElement DownloadServer1 = driver.findElement(By.xpath("//*[@id=\"result\"]/div/div/div[2]/div/a[1]"));
                                                                    //*[@id="result"]/div/div/div[2]/div/a[1]
        WebElement DownloadServer2 = driver.findElement(By.xpath("//*[@id=\"result\"]/div/div/div[2]/div/a[2]"));

        ////*[@id="result"]/div/div/div[2]/div/a[1]
        ////*[@id="result"]/div/div/div[2]/div/a[2]




        
        logger.info(download.getAttribute("href"));
        logger.info(DownloadServer1.getAttribute("href"));
        logger.info(DownloadServer2.getAttribute("href"));

        String videoLink = "";
        if(!download.getAttribute("href").isEmpty() && download.getAttribute("href") != "" ){
            videoLink = download.getAttribute("href");


        }
        else if(!DownloadServer1.getAttribute("href").isEmpty() && DownloadServer1.getAttribute("href") != ""){
            videoLink = DownloadServer1.getAttribute("href");

        }
        else if(!DownloadServer2.getAttribute("href").isEmpty() && DownloadServer2.getAttribute("href") != ""){
            videoLink = DownloadServer2.getAttribute("href");
        }

        System.out.println(videoLink);


        if(!videoLink.isEmpty()) {
            try (BufferedInputStream in = new BufferedInputStream(new URL(videoLink).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("reel.mp4")) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                return new File("reel.mp4");
            } catch (IOException e) {
                // handle exception
            }
        }
        else {
        logger.info("Video link is not avaialble to download");
        }

        return null;
        //driver.quit();
    }

}
