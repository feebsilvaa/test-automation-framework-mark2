package br.com.fernando.automationframework.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
        protected synchronized WebDriver initialValue() {
            return initDriver();
        }
    };

    private static final String USERNAME = "feebsilvaa";
    private static final String AUTOMATE_KEY = "chave";

    private static String webPageUrl = "https://mark7.herokuapp.com/login";

    private DriverFactory() {}

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    public static WebDriver initDriver() {
        WebDriver driver = null;
        if (driver == null) {

            if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.LOCAL) {
                switch (FrameworkProperties.BROWSER) {
                    case MOZILLA_FIREFOX:
                        driver = new FirefoxDriver();
                        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
                        driver.get(webPageUrl);
                        break;
                    case GOOGLE_CHROME:
                        /*ChromeOptions options = new ChromeOptions();
                        options.addArguments("headless");
                        driver = new ChromeDriver(options);*/
                        driver = new ChromeDriver();
                        driver.manage().window().setPosition(new Point(1300, 100));
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

                        driver.get(webPageUrl);
                        break;
                    case INTERNET_EXPLORER:
                        driver = new InternetExplorerDriver();
                        driver.manage().window().maximize();
                        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
                        driver.get(webPageUrl);
                        break;
                    case MICROSOFT_EDGE:
                        driver = new EdgeDriver();
                        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
                        driver.get(webPageUrl);
                        break;
                }
            }

            if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.SELENIUM_GRID) {
                DesiredCapabilities cap = null;
                switch (FrameworkProperties.BROWSER) {
                    case MOZILLA_FIREFOX: cap = DesiredCapabilities.firefox(); break;
                    case GOOGLE_CHROME: cap = DesiredCapabilities.chrome(); break;
                    case INTERNET_EXPLORER: cap = DesiredCapabilities.internetExplorer(); break;
                    case MICROSOFT_EDGE: cap = DesiredCapabilities.edge(); break;
                }
                try {
                    String urlSeleniumGrid = "http://192.168.1.100:4444/wd/hub";
                    driver = new RemoteWebDriver(new URL(urlSeleniumGrid),cap);
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    driver.get(webPageUrl);
                } catch (MalformedURLException e) {
                    System.err.println("Falha na conex�o com o Grid");
                    e.printStackTrace();
                }
            }

            if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.CLOUD_SOUCE_LABS) {
                DesiredCapabilities cap = null;
                switch (FrameworkProperties.BROWSER) {
                    case MOZILLA_FIREFOX: cap = DesiredCapabilities.firefox(); break;
                    case GOOGLE_CHROME: cap = DesiredCapabilities.chrome(); break;
                    case INTERNET_EXPLORER: cap = DesiredCapabilities.internetExplorer(); break;
                    case MICROSOFT_EDGE: cap = DesiredCapabilities.edge(); break;
                }
                try {
                    StringBuilder URL_SAUCE_LABS = new StringBuilder();
                    URL_SAUCE_LABS.append("https://").append(USERNAME).append(":")
                            .append(AUTOMATE_KEY).append("@ondemand.saucelabs.com:80/wd/hub");
                    driver = new RemoteWebDriver(new URL(URL_SAUCE_LABS.toString()),cap);
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    driver.get(webPageUrl);
                } catch (MalformedURLException e) {
                    System.err.println("Falha na conex�o com a Sauce Labs");
                    e.printStackTrace();
                }
            }

            if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.CLOUD_BROWSER_STACK) {
                DesiredCapabilities cap = null;
                switch (FrameworkProperties.BROWSER) {
                    case MOZILLA_FIREFOX: cap = DesiredCapabilities.firefox(); break;
                    case GOOGLE_CHROME: cap = DesiredCapabilities.chrome(); break;
                    case INTERNET_EXPLORER: cap = DesiredCapabilities.internetExplorer(); break;
                    case MICROSOFT_EDGE: cap = DesiredCapabilities.edge(); break;
                }
                try {
                    StringBuilder URL_BROWSER_STACK = new StringBuilder();
                    URL_BROWSER_STACK.append("https://").append(USERNAME).append(":")
                            .append(AUTOMATE_KEY).append("@hub-cloud.browserstack.com/wd/hub");
                    driver = new RemoteWebDriver(new URL(URL_BROWSER_STACK.toString()),cap);
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    driver.manage().window().maximize();
                    driver.get(webPageUrl);
                } catch (MalformedURLException e) {
                    System.err.println("Falha na conex�o com o Browser Stack");
                    e.printStackTrace();
                }
            }


        }
        return driver;
    }

    public static void killDriver() {
        WebDriver driver = getDriver();

        if (driver != null) {
            driver.quit();
            driver = null;
        }

        if (threadDriver != null) {
            threadDriver.remove();
        }
    }
}
