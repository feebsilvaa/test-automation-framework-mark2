package br.com.fernando.automationframework.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.fernando.automationframework.support.LoggerUtils;

public class DriverFactory {

	private static final Logger log = Logger.getLogger(DriverFactory.class);
	private static String className = DriverFactory.class.getName();

	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
		protected synchronized WebDriver initialValue() {
			return initDriver();
		}
	};

	private static final String USERNAME = "feebsilvaa";
	private static final String AUTOMATE_KEY = "chave";

	private static String webPageUrl = "https://mark7.herokuapp.com/login";

	private DriverFactory() {
	}

	public static WebDriver getDriver() {
		return threadDriver.get();
	}

	public static WebDriver initDriver() {
		WebDriver driver = null;
		if (driver == null) {

			if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.LOCAL) {
				log.info(LoggerUtils.INFO_TAG(className, "Iniciando configuração de execucao local do driver."));
				switch (FrameworkProperties.BROWSER) {
				case MOZILLA_FIREFOX:
					log.info(LoggerUtils.INFO_TAG(className, "Geckodriver"));
					driver = new FirefoxDriver();
					driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
					break;
				case GOOGLE_CHROME:
					log.info(LoggerUtils.INFO_TAG(className, "Chromedriver"));
					driver = new ChromeDriver();
					driver.manage().window().setPosition(new Point(1300, 100));
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
					break;
				case GOOGLE_CHROME_HEADLESS:
					log.info(LoggerUtils.INFO_TAG(className, "Chromedriver Ghost Mode"));
					ChromeOptions options = new ChromeOptions();
					options.addArguments("headless");
					driver = new ChromeDriver(options);
					driver.manage().window().setPosition(new Point(1300, 100));
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
					break;
				case INTERNET_EXPLORER:
					log.info(LoggerUtils.INFO_TAG(className, "Iedriver"));
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
					break;
				case MICROSOFT_EDGE:
					log.info(LoggerUtils.INFO_TAG(className, "Edgedriver"));
					driver = new EdgeDriver();
					driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
					break;
				}
			}

			if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.SELENIUM_GRID) {
				log.info(LoggerUtils.INFO_TAG(className,
						"Iniciando configuração de execução via selenium grid do driver."));
				DesiredCapabilities cap = null;
				switch (FrameworkProperties.BROWSER) {
				case MOZILLA_FIREFOX:
					cap = DesiredCapabilities.firefox();
					break;
				case GOOGLE_CHROME:
					cap = DesiredCapabilities.chrome();
					break;
				case INTERNET_EXPLORER:
					cap = DesiredCapabilities.internetExplorer();
					break;
				case MICROSOFT_EDGE:
					cap = DesiredCapabilities.edge();
					break;
				default:
					break;
				}
				try {
					log.info(LoggerUtils.INFO_TAG(className,"Selenium Grid"));
					String urlSeleniumGrid = "http://192.168.1.100:4444/wd/hub";
					log.info(LoggerUtils.INFO_TAG(className,"Acessando Selenium Grid: " + urlSeleniumGrid));
					driver = new RemoteWebDriver(new URL(urlSeleniumGrid), cap);
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
				} catch (MalformedURLException e) {
					log.error(LoggerUtils.ERROR_TAG(className, "Falha na conexao com o Grid"));
					log.error(LoggerUtils.ERROR_TAG(className, e.getMessage()));
					e.printStackTrace();
				}
			}

			if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.CLOUD_SOUCE_LABS) {
				log.info(LoggerUtils.INFO_TAG(className,
						"Iniciando configuração de execução cloud Souce Labs do driver."));
				DesiredCapabilities cap = null;
				switch (FrameworkProperties.BROWSER) {
				case MOZILLA_FIREFOX:
					cap = DesiredCapabilities.firefox();
					break;
				case GOOGLE_CHROME:
					cap = DesiredCapabilities.chrome();
					break;
				case INTERNET_EXPLORER:
					cap = DesiredCapabilities.internetExplorer();
					break;
				case MICROSOFT_EDGE:
					cap = DesiredCapabilities.edge();
					break;
				}
				try {
					log.info(LoggerUtils.INFO_TAG(className,"Sauce Labs cloud"));
					StringBuilder URL_SAUCE_LABS = new StringBuilder();
					URL_SAUCE_LABS.append("https://").append(USERNAME).append(":").append(AUTOMATE_KEY)
							.append("@ondemand.saucelabs.com:80/wd/hub");
					log.info(LoggerUtils.INFO_TAG(className,"Acessando Sauce labs: " + URL_SAUCE_LABS.toString()));
					driver = new RemoteWebDriver(new URL(URL_SAUCE_LABS.toString()), cap);
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
				} catch (MalformedURLException e) {
					log.error(LoggerUtils.ERROR_TAG(className, "Falha na conexão com a Sauce Labs"));
					log.error(LoggerUtils.ERROR_TAG(className, e.getMessage()));
					e.printStackTrace();
				}
			}

			if (FrameworkProperties.TIPO_EXECUCAO == FrameworkProperties.TipoExecucao.CLOUD_BROWSER_STACK) {
				log.info(LoggerUtils.INFO_TAG(className,
						"Iniciando configuração de execução cloud Browser Stack do driver."));
				DesiredCapabilities cap = null;
				switch (FrameworkProperties.BROWSER) {
				case MOZILLA_FIREFOX:
					cap = DesiredCapabilities.firefox();
					break;
				case GOOGLE_CHROME:
					cap = DesiredCapabilities.chrome();
					break;
				case INTERNET_EXPLORER:
					cap = DesiredCapabilities.internetExplorer();
					break;
				case MICROSOFT_EDGE:
					cap = DesiredCapabilities.edge();
					break;
				}
				try {
					log.info(LoggerUtils.INFO_TAG(className,"Browser Stack cloud"));					
					StringBuilder URL_BROWSER_STACK = new StringBuilder();
					URL_BROWSER_STACK.append("https://").append(USERNAME).append(":").append(AUTOMATE_KEY)
							.append("@hub-cloud.browserstack.com/wd/hub");
					log.info(LoggerUtils.INFO_TAG(className,"Acessando Sauce labs: " + URL_BROWSER_STACK.toString()));
					driver = new RemoteWebDriver(new URL(URL_BROWSER_STACK.toString()), cap);
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					log.info(LoggerUtils.INFO_TAG(className, "Acessando url: " + webPageUrl));
					driver.get(webPageUrl);
				} catch (MalformedURLException e) {
					log.error(LoggerUtils.ERROR_TAG(className, "Falha na conexao com o Browser Stack"));
					log.error(LoggerUtils.ERROR_TAG(className, e.getMessage()));
					e.printStackTrace();
				}
			}

		}
		return driver;
	}

	public static void killDriver() {
		log.info(LoggerUtils.INFO_TAG(className, "Finalizando driver"));
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
