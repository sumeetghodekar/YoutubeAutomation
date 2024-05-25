package demo;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    ChromeDriver driver;

    @BeforeSuite
    public void setUp() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().browserVersion("125.0.6422.61").setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);
    }

    @AfterSuite
    public void tearDown() {
        System.out.println("End Test: TestCases");
        driver.quit();
    }
    
    @Test(priority = 1)
    public void TestCase01() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            // Navigate to Youtube
            navigateToyoutubeURL(driver, "https://www.youtube.com/");

            // Scroll till About and click on it
            scrollToAbout(driver, By.xpath("//a[text()='About']"), By.xpath("//section[@class='ytabout__content']"));
            js.executeScript("window.scrollBy(0,700)");

        } catch (Exception e) {
            System.out.println("Failure occurred while searching About: " + e.getMessage());
        }
    }
    @Test(priority = 2)
    public void TestCase02()
    {
        try 
        {
           
            navigatetoMoviesTab(driver, By.xpath("//yt-formatted-string[text()='Movies']"),
            By.xpath("//span[text()='Movies']"));

            moviescroll(driver,By.xpath("(//button[@aria-label='Next'])[1]"));

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Failure occurred while searching Movies Tab: " + e.getMessage());
        }
    }
    @Test(priority = 3)
    public void TestCase03()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try 
        {
            
            navigatetomusicTab(driver, By.xpath("//yt-formatted-string[text()='Music']"),
            By.xpath("(//yt-formatted-string[@id='title'])[2]"),
            By.xpath("(//button[@aria-label='Next'])[2]"),
            By.xpath("(//h3[@class='style-scope ytd-compact-station-renderer'])[11]"),
            By.xpath("(//p[@id='video-count-text'])[11]"));
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(3000);
              
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Failure occurred while searching Movies titles: " + e.getMessage());
        }
    }
    @Test(priority = 4)
    public void TestCase04()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try 
        {
            navigatetoNewsTab(driver, By.xpath("//yt-formatted-string[text()='News']"));
            js.executeScript("window.scrollBy(0,400)");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Failure occurred while searching News titles: " + e.getMessage());
        }
    }


    private static void navigateToyoutubeURL(ChromeDriver driver, String expectedurl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            if (!(driver.getCurrentUrl().equals(expectedurl))) {
                driver.get(expectedurl);
                wait.until(ExpectedConditions.urlToBe(expectedurl));

                String actualurl = driver.getCurrentUrl();
                Assert.assertEquals(actualurl, expectedurl, "Failed to load url");
            }

        } catch (Exception e) {
            System.out.println("Exception occurred while navigating: " + e.getMessage());
        }
    }

    private static void scrollToAbout(ChromeDriver driver, By scrollselector, By textselector) {
        try {
            WebElement scrolltoAbout = driver.findElement(scrollselector);
            scrolltoAbout.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("about"));

            String url = driver.getCurrentUrl();
            if (url.contains("about")) {
                System.out.println("Successfully clicked and navigated to about URL: " + url);

                WebElement text = driver.findElement(textselector);
                String aboutText = text.getText();
                System.out.println(aboutText);
                driver.navigate().back();
            } else {
                System.out.println("About keyword does not contain");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while scrolling to About section: " + e.getMessage());
        }
    }

    private static void navigatetoMoviesTab(ChromeDriver driver,By movtab,By movieTitle)
    {
        try 
        {
            WebElement movie = driver.findElement(movtab);
            movie.click();   
            
           Thread.sleep(3000);
            WebElement movtitle = driver.findElement(movieTitle);
            String title = movtitle.getText();

            if(title.contains("Movies"))
            {
                System.out.println("Successfully Navigate to Movies");
            }
            else
            {
                System.out.println("Not able to navigate Movies");
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Failure occurred while navigating movie tab: " + e.getMessage());
        }
    }
    private static void moviescroll(ChromeDriver driver,By nxtbutton)
    {
        SoftAssert softAssert = new SoftAssert();
        try 
        {
            WebElement nextButton = driver.findElement(nxtbutton);  
            nextButton.click();
            nextButton.click();
            nextButton.click();
            Thread.sleep(2000);
            WebElement title = driver.findElement(By.xpath("//span[@title='The Wolf of Wall Street']"));
            String titletext = title.getText();
            if(titletext.contains("Wolf"))  
            {
                System.out.println("successfully scroll till extreme right");
            }
            else
            {
                System.out.println("Not able to scroll");
            }
            
            WebElement rating = driver.findElement(By.xpath("(//p[@class='style-scope ytd-badge-supported-renderer'])[32]"));
            String ratingtext = rating.getText();
            System.out.println(ratingtext);
            Thread.sleep(3000);
            softAssert.assertTrue(ratingtext.contains("A"), "Movie " + titletext + " is not marked 'A' for Mature.");


            WebElement comedy = driver.findElement(By.xpath("(//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer'])[16]"));
            String comedytext = comedy.getText();
            System.out.println(comedytext);
            Thread.sleep(2000);
            softAssert.assertTrue(comedytext.contains("Comedy"),"Movie " +titletext+ "is not Comedy or Animation");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Failure occurred while retreiving movie title: " + e.getMessage());
        }
        softAssert.assertAll();
    }

   private static void navigatetomusicTab(ChromeDriver driver,By clickmusictab,By musicword,By nxtbtn,By movtitle,By trackcount)
   {

    SoftAssert softAssert = new SoftAssert();
    try 
    {
        WebElement tab = driver.findElement(clickmusictab);
        tab.click();  
        Thread.sleep(3000);
        WebElement word = driver.findElement(musicword);
        String musictext = word.getText();
        if(musictext.contains("Music"))
        {
            System.out.println("Successfully navigate to Music tab");
        }
        else
        {
            System.out.println("Not able to navigate to music tab");
        }

        WebElement nextbutton = driver.findElement(nxtbtn);
        nextbutton.click();
        nextbutton.click();
        nextbutton.click();

        Thread.sleep(2000);

        WebElement movietitle = driver.findElement(movtitle);
        String titletext = movietitle.getText();
        if(titletext.contains("Bollywood Dance"))
        {
            System.out.println("Successfully scroll till extreme right");
        }
        else
        {
            System.out.println("Not able to scroll");
        }

        WebElement track = driver.findElement(trackcount);
        String tracktext = track.getText();
        int numTracks = Integer.parseInt(tracktext.split(" ")[0]);
        softAssert.assertTrue(numTracks <= 50,"Playlist " + tracktext + " has more than 50 tracks.");
        
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Failure occurred while navigating to music tab: " + e.getMessage());
    }
    softAssert.assertAll();
   }

   public static void navigatetoNewsTab(ChromeDriver driver,By newstitle)
   {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    try 
    {
        WebElement news = driver.findElement(newstitle);
        news.click();
        //Thread.sleep(6000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ytd-post-renderer[@class='style-scope ytd-rich-item-renderer']")));
        
        List<WebElement> newstitlelist = driver.findElements(By.xpath("//a[@id='author-text']"));
        List<WebElement> bodylist = driver.findElements(By.xpath("//yt-formatted-string[@id='home-content-text']"));
        List<WebElement> likelist = driver.findElements(By.xpath("//span[@id='vote-count-middle']"));
        for(int i=0;i<3;i++)
        {
            WebElement threenewstitle = newstitlelist.get(i);
            WebElement threebodytitle = bodylist.get(i);
            WebElement threelikenumber = likelist.get(i);

            String newsword = threenewstitle.getText();
            System.out.println("Title of latest news "+newsword);
           
            
            String newsbody = threebodytitle.getText();
            System.out.println("Body of latest news "+newsbody);
            

            String like = threelikenumber.getText();
            System.out.println("Likes for latest news "+like);
            System.out.println("");    
            
        }

    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Failure occurred while navigating to news tab: " + e.getMessage());
    }
   }
}