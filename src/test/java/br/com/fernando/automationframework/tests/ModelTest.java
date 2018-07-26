package br.com.fernando.automationframework.tests;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;
import static br.com.fernando.automationframework.core.DriverFactory.killDriver;
import static br.com.fernando.automationframework.support.ErrMsgGen.comparisonErrorMessage;
import static com.aventstack.extentreports.Status.FAIL;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.fernando.automationframework.core.BaseTest;
import br.com.fernando.automationframework.core.FrameworkProperties;
import br.com.fernando.automationframework.pages.Page;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "ModelTest.csv")
public class ModelTest extends BaseTest {

	private Page page = new Page();

	@Before
	public void setupTest() {
		getDriver();
	}

	@After
	public void tearDownTest() {
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
