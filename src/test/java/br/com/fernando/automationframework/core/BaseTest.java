package br.com.fernando.automationframework.core;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;
import static br.com.fernando.automationframework.core.DriverFactory.killDriver;
import static br.com.fernando.automationframework.support.DataHoraGen.dataHoraParaArquivo;
import static br.com.fernando.automationframework.support.HTMLReportGen.htmlReport;
import static br.com.fernando.automationframework.support.HTMLReportGen.htmlReportConfig;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import br.com.fernando.automationframework.core.FrameworkProperties.Browsers;

public class BaseTest {

	protected static ExtentHtmlReporter htmlReporter;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static String reportName;
	protected static String className;
	protected StringBuilder nomeCenario = new StringBuilder();

	@Rule
	public TestName testName = new TestName();

	@BeforeClass
	public static void setUpClass() {

		PropertyConfigurator.configure("conf/log4j.properties");
		
		// start reporters
		reportName = htmlReport("ExtentReportHTML");
		htmlReporter = new ExtentHtmlReporter(reportName);
		// create ExtentReports and attach reporter(s)
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		killWindowsProccess();
		// Ao final do teste abre o arquivo de report HTML
		Desktop.getDesktop().open(new File(reportName));
	}

	@Before
	public void setUpTest() throws IOException {
		// creates a toggle for the given test, adds all log events under it
		htmlReportConfig(htmlReporter, getClass().getSimpleName());
		
		String test_name = testName.getMethodName().split("\\{")[0].replaceAll("_", " ");
		
		nomeCenario.append(capitalizeFully(test_name)).append(" - ").append(dataHoraParaArquivo("dd/MM/yy - HH:mm:ss"))
				.toString();
	}

	@After
	public void tearDownTest() throws Exception {
		try {
			extent.flush();
		} catch (Exception e) {
			test.log(Status.FAIL, e.getMessage());
			throw e;
		}

	}

	protected static void evidence() throws InvalidFormatException, InterruptedException {
		/*int width = (int) (1280 * 0.53);
		int height = (int) (720 * 0.53);
		Thread.sleep(500);
		String blipId = evidenciaDocx.addPictureData(screenshotByte(), XWPFDocument.PICTURE_TYPE_PNG);
		evidenciaDocx.createPicture(blipId, evidenciaDocx.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), width,
				height);*/
	}

	public static boolean killWindowsProccess() {
		String processo = null;

		// Chromedriver
		if (FrameworkProperties.BROWSER == Browsers.GOOGLE_CHROME) {
			processo = "chromedriver.exe";
		}
		// Geckodriver
		if (FrameworkProperties.BROWSER == Browsers.MOZILLA_FIREFOX) {
			processo = "geckodriver.exe";
		}

		// No Windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			try {
				String line;
				Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input.readLine()) != null) {
					if (!line.trim().equals("")) {
						if (line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processo)) {
							Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
							return true;
						}
					}
				}
				input.close();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		// No Linux

		// No Mac
		return false;
	}

}
