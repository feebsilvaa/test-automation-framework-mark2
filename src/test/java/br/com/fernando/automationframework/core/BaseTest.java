package br.com.fernando.automationframework.core;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;
import static br.com.fernando.automationframework.core.DriverFactory.killDriver;
import static br.com.fernando.automationframework.support.Generator.arquivoSaidaDocx;
import static br.com.fernando.automationframework.support.Generator.evidenciaArquivoWord;
import static br.com.fernando.automationframework.support.Screenshot.screenshotByte;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import br.com.fernando.automationframework.support.CustomXWPFDocument;

public class BaseTest {
    protected Map<String, String> dadosDoTeste;

    protected String resultadoDoTeste;

    public static FileOutputStream fileOutput = null;
    public static CustomXWPFDocument evidenciaDocx = null;

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void setUpClass() {
        getDriver();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUpTest() throws IOException {
        // Gera arquivo de evidencia a partir de um modelo
        evidenciaDocx = evidenciaArquivoWord(testName.getMethodName());
        dadosDoTeste = new HashMap<String, String>();
        resultadoDoTeste = "Failed";
    }
    @After
    public void tearDownTest() {
    	try {
        	// Salva a evidência montada em um arquivo word .docx
            fileOutput = arquivoSaidaDocx(testName.getMethodName(), evidenciaDocx, dadosDoTeste, resultadoDoTeste);
            evidenciaDocx.write(fileOutput);
            //converToPdf(evidenciaDocx, testName.getMethodName());
            fileOutput.flush();
            fileOutput.close();

            if (FrameworkProperties.CLOSE_BROWSER) {
                killDriver();
            }

		} catch (Exception e) {
            resultadoDoTeste = "Failed";
            inserirErroEvidencia(e.toString());
		}

    }

    protected static void inserirDadosTesteEvidencia(Map dadosDoTeste) {

        Set<Map.Entry<String, String>> set = dadosDoTeste.entrySet();
        Iterator it = set.iterator();

        XWPFParagraph par1 = evidenciaDocx.createParagraph();
        XWPFRun runpar1 = par1.createRun();
        runpar1.setText("DADOS UTILIZADOS NO TESTE");
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) it.next();
            runpar1.addBreak();
            StringBuilder str = new StringBuilder();
            str.append(String.valueOf(entry.getKey()))
                    .append(": ")
                    .append(String.valueOf(entry.getValue()));
            runpar1.setText(str.toString());
        }
    }

    protected static void inserirErroEvidencia(String erro) {
        XWPFParagraph par1 = evidenciaDocx.createParagraph();
        XWPFRun runpar1 = par1.createRun();
        runpar1.addBreak();
        runpar1.addBreak();
        runpar1.setText(erro);
        runpar1.setColor("FF0000");
    }

    protected static void evidence() throws InvalidFormatException, InterruptedException {
        int width = (int) (1280 * 0.53);
        int height = (int) (720 * 0.53);
        Thread.sleep(500);
        String blipId = evidenciaDocx.addPictureData(screenshotByte(), XWPFDocument.PICTURE_TYPE_PNG);
        evidenciaDocx.createPicture(blipId,evidenciaDocx.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), width, height);
    }
}
