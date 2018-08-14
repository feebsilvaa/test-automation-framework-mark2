package br.com.fernando.automationframework.tests;

import static br.com.fernando.automationframework.support.ErrMsgGen.comparisonErrorMessage;
import static com.aventstack.extentreports.Status.FAIL;
import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.fernando.automationframework.core.BaseTest;
import br.com.fernando.automationframework.support.LoggerUtils;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "MassaCsvTest.csv")
public class MassaCsvTest extends BaseTest {

	private static final Logger log = Logger.getLogger(MassaCsvTest.class);
	private String className = MassaCsvTest.class.getName();

	@Test
	public void deve_consumir_dados_csv(@Param(name = "dado") String dado) throws Exception {
		try {
			log.info(LoggerUtils.INFO_TAG(className, "Iniciando teste de consumo de massa de dados via csv"));
			test = extent.createTest(nomeCenario.toString(), "Este teste realiza consumo de massa de dados via csv");

			assertEquals("Arquivo Csv acessado com sucesso", dado);

			test.pass("Passed");
		} catch (ComparisonFailure e) {
			test.log(FAIL, comparisonErrorMessage(e.getExpected(), e.getActual()));
			log.error(LoggerUtils.ERROR_TAG(className,
					"Ocorreu um erro ao comparar o resultado esperado com o encontrado"));
			log.error(LoggerUtils.ERROR_TAG(className, comparisonErrorMessage(e.getExpected(), e.getActual())));
			throw e;
		} catch (AssertionError e) {
			test.log(FAIL, e.getMessage());
			log.error(LoggerUtils.ERROR_TAG(className,
					"Ocorreu um erro ao comparar o resultado esperado com o encontrado"));
			log.error(LoggerUtils.ERROR_TAG(className, e.getMessage()));
			throw e;
		} catch (Exception e) {
			test.log(FAIL, "Erro: " + e.getMessage());
			log.error(LoggerUtils.ERROR_TAG(className, "Ocorreu um erro inesperado"));
			log.error(LoggerUtils.ERROR_TAG(className, "Erro: " + e.getMessage()));
			throw e;
		}
	}

	@Test
	public void deve_consumir_dados_csv_separados_por_virgula(@Param(name = "dado") String dado) throws Exception {
		try {
			log.info(LoggerUtils.INFO_TAG(className,
					"Iniciando teste de consumo de massa de dados via csv separados por vírgula"));
			test = extent.createTest(nomeCenario.toString(),
					"Este teste realiza consumo de massa de dados via csv separados por vírgula");

			assertEquals("Arquivo Csv separado com virgula acessado com sucesso", dado);

			test.pass("Passed");
		} catch (ComparisonFailure e) {
			test.log(FAIL, comparisonErrorMessage(e.getExpected(), e.getActual()));
			log.error(LoggerUtils.ERROR_TAG(className,
					"Ocorreu um erro ao comparar o resultado esperado com o encontrado"));
			log.error(LoggerUtils.ERROR_TAG(className, comparisonErrorMessage(e.getExpected(), e.getActual())));
			throw e;
		} catch (AssertionError e) {
			test.log(FAIL, e.getMessage());
			log.error(LoggerUtils.ERROR_TAG(className,
					"Ocorreu um erro ao comparar o resultado esperado com o encontrado"));
			log.error(LoggerUtils.ERROR_TAG(className, e.getMessage()));
			throw e;
		} catch (Exception e) {
			test.log(FAIL, "Erro: " + e.getMessage());
			log.error(LoggerUtils.ERROR_TAG(className, "Ocorreu um erro inesperado"));
			log.error(LoggerUtils.ERROR_TAG(className, "Erro: " + e.getMessage()));
			throw e;
		}
	}

	@Test
	public void deve_consumir_dados_csv_separados_por_ponto_e_virgula(@Param(name = "dado") String dado) throws Exception {
		try {
			log.info(LoggerUtils.INFO_TAG(className,
					"Iniciando teste de consumo de massa de dados via csv separados por ponto e vírgula"));
			test = extent.createTest(nomeCenario.toString(),
					"Este teste realiza consumo de massa de dados via csv separados por ponto e vírgula");

			assertEquals("Arquivo Csv separado com ponto e virgula acessado com sucesso", dado);

			test.pass("Passed");
		} catch (ComparisonFailure e) {
			test.log(FAIL, comparisonErrorMessage(e.getExpected(), e.getActual()));
			log.error(LoggerUtils.ERROR_TAG(className,
					"Ocorreu um erro ao comparar o resultado esperado com o encontrado"));
			log.error(LoggerUtils.ERROR_TAG(className, comparisonErrorMessage(e.getExpected(), e.getActual())));
			throw e;
		} catch (AssertionError e) {
			test.log(FAIL, e.getMessage());
			log.error(LoggerUtils.ERROR_TAG(className,
					"Ocorreu um erro ao comparar o resultado esperado com o encontrado"));
			log.error(LoggerUtils.ERROR_TAG(className, e.getMessage()));
			throw e;
		} catch (Exception e) {
			test.log(FAIL, "Erro: " + e.getMessage());
			log.error(LoggerUtils.ERROR_TAG(className, "Ocorreu um erro inesperado"));
			log.error(LoggerUtils.ERROR_TAG(className, "Erro: " + e.getMessage()));
			throw e;
		}
	}
	
	
}