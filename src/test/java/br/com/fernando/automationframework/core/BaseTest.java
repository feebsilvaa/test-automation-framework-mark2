package br.com.fernando.automationframework.core;

import static br.com.fernando.automationframework.support.DataHoraGen.dataHoraParaArquivo;
import static br.com.fernando.automationframework.support.HTMLReportGen.htmlReport;
import static br.com.fernando.automationframework.support.HTMLReportGen.htmlReportConfig;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
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
import br.com.fernando.automationframework.support.LoggerUtils;

public class BaseTest {

	private static final Logger log = Logger.getLogger(BaseTest.class);
	private static String className = BaseTest.class.getName();

	protected static ExtentHtmlReporter htmlReporter;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static String reportName;
	protected StringBuilder nomeCenario = new StringBuilder();

	@Rule
	public TestName testName = new TestName();

	@BeforeClass
	public static void setUpClass() {
		// Configurando Log4J
		PropertyConfigurator.configure("conf/log4j.properties");
		// start reporters
		log.info(LoggerUtils.INFO_TAG(className, "Inicializando report HTML"));
		reportName = htmlReport("ReportHtml");
		htmlReporter = new ExtentHtmlReporter(reportName);
		// create ExtentReports and attach reporter(s)
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	@Before
	public void setUpTest() throws IOException {
		String test_name = testName.getMethodName().split("\\{")[0].replaceAll("_", " ");
		log.info(LoggerUtils.INFO_TAG(className, "Inicializando setup do teste: " + capitalizeFully(test_name)));
		// creates a toggle for the given test, adds all log events under it
		htmlReportConfig(htmlReporter, getClass().getSimpleName());
		nomeCenario.append(capitalizeFully(test_name)).append(" - ").append(dataHoraParaArquivo("dd/MM/yy - HH:mm:ss"))
				.toString();
	}

	@After
	public void tearDownTest() throws Exception {
		String test_name = testName.getMethodName().split("\\{")[0].replaceAll("_", " ");
		log.info(LoggerUtils.INFO_TAG(className, "Finalizando teste: " + capitalizeFully(test_name)));
		try {
			log.info(LoggerUtils.INFO_TAG(className, "Salvando report HTML: " + capitalizeFully(test_name)));
			extent.flush();
		} catch (Exception e) {
			log.error(LoggerUtils.ERROR_TAG(className, e.getMessage()));
			test.log(Status.FAIL, e.getMessage());
			throw e;
		}

	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		// Ao final do teste abre o arquivo de report HTML
		log.info(LoggerUtils.INFO_TAG(className, "Exibindo report HTML: " + reportName));
		Desktop.getDesktop().open(new File(reportName));
		log.info(LoggerUtils.INFO_TAG(className, "Finalizando processos"));
		killWindowsProccess();
	}

	protected static void evidence() throws InvalidFormatException, InterruptedException {
		/*
		 * int width = (int) (1280 * 0.53); int height = (int) (720 * 0.53);
		 * Thread.sleep(500); String blipId =
		 * evidenciaDocx.addPictureData(screenshotByte(),
		 * XWPFDocument.PICTURE_TYPE_PNG); evidenciaDocx.createPicture(blipId,
		 * evidenciaDocx.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), width,
		 * height);
		 */
	}

	public static boolean killWindowsProccess() {
		String processo = null;

		// Chromedriver
		if (FrameworkProperties.BROWSER == Browsers.GOOGLE_CHROME) {
			processo = "chromedriver.exe";
		}
		// Chromedriver Headless
		if (FrameworkProperties.BROWSER == Browsers.GOOGLE_CHROME_HEADLESS) {
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
