package br.com.fernando.automationframework.tests;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;
import static br.com.fernando.automationframework.core.DriverFactory.killDriver;
import static br.com.fernando.automationframework.support.ErrMsgGen.comparisonErrorMessage;
import static com.aventstack.extentreports.Status.FAIL;

import org.apache.log4j.Logger;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.aventstack.extentreports.Status;

import br.com.fernando.automationframework.core.BaseTest;
import br.com.fernando.automationframework.core.FrameworkProperties;
import br.com.fernando.automationframework.pages.Page;
import br.com.fernando.automationframework.support.LoggerUtils;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "ModelTest.csv")
public class ModelTest extends BaseTest {
	
	private static final Logger log = Logger.getLogger(ModelTest.class);
	private String className = ModelTest.class.getName();

	private Page page = new Page();

	@Before
	public void setupTest() {		
		log.info(LoggerUtils.INFO_TAG(className, "Abrindo navegador"));
		getDriver();
	}

	@After
	public void tearDownTest() throws Exception {
		try {
			extent.flush();
		} catch (Exception e) {
			test.log(Status.FAIL, e.getMessage());
			throw e;
		}
		if (FrameworkProperties.CLOSE_BROWSER) {
			killDriver();
		}
	}

	@Test
	public void deve_acessar_pagina(@Param(name = "tituloPagina") String tituloPagina) throws Exception {
		test = extent.createTest(nomeCenario.toString(), "Este teste realiza acesso à aplicação web Mark7 Herokuapp");

		String title = page.tituloPagina();

		try {
			Assert.assertEquals(tituloPagina, title);
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
