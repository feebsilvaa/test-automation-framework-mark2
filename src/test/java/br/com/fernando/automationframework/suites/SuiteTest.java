package br.com.fernando.automationframework.suites;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.fernando.automationframework.support.LoggerUtils;
import br.com.fernando.automationframework.tests.ModelTest;
import br.com.fernando.automationframework.tests.SoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	ModelTest.class,
	SoTest.class })
public class SuiteTest {

	private static final Logger log = Logger.getLogger(SuiteTest.class);
	private static String className = SuiteTest.class.getName();

	@BeforeClass
	public static void initSuite() {
		PropertyConfigurator.configure("conf/log4j.properties");
		log.info(LoggerUtils.INFO_TAG(className, "=== Iniciando Framework de Testes ==="));
	}

}
