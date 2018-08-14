package br.com.fernando.automationframework.tests;

import static br.com.fernando.automationframework.support.ErrMsgGen.comparisonErrorMessage;
import static com.aventstack.extentreports.Status.FAIL;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;

import br.com.fernando.automationframework.core.BaseTest;
import br.com.fernando.automationframework.support.LoggerUtils;

public class SoTest extends BaseTest {

	private static final Logger log = Logger.getLogger(SoTest.class);
	private String className = SoTest.class.getName();
	
	@Test
	public void deve_verficar_nome_so() throws Exception {
		try {
			log.info(LoggerUtils.INFO_TAG(className, "Iniciando teste de verificacao do sistema operacional"));
			test = extent.createTest(nomeCenario.toString(), "Este teste verifica o nome do sistema operacional");
			String sysName = System.getProperty("os.name").toLowerCase();
			Assert.assertTrue(sysName.contains("windows"));
			test.pass("Passed");
		} catch (ComparisonFailure e) {
			test.log(FAIL, comparisonErrorMessage(e.getExpected(), e.getActual()));
			throw e;
		} catch (Exception e) {
			test.log(FAIL, "Erro: " + e.getMessage());
			throw e;
		}

	}
}