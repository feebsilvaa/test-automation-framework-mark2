package br.com.fernando.automationframework.tests;

import static org.junit.Assert.assertEquals;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import br.com.fernando.automationframework.core.BaseTest;
import br.com.fernando.automationframework.pages.Page;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "ModelTest.csv")
public class ModelTest extends BaseTest {

	private Page page = new Page();
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Test
	public void acessarPaginaGoogle(@Param(name = "tituloPagina") String tituloPagina) throws Exception {
		try {
			String title = page.tituloPagina();
			evidence();
			assertEquals(tituloPagina, title);

			resultadoDoTeste = "Passed";
		} catch (Exception e) {
			inserirErroEvidencia(e.toString());
			resultadoDoTeste = "Failed";
			throw e;
		}
	}

}
