package br.com.fernando.automationframework.core;

import br.com.fernando.automationframework.support.CustomXWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.*;
import org.junit.rules.TestName;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;
import static br.com.fernando.automationframework.core.DriverFactory.killDriver;
import static br.com.fernando.automationframework.support.Generator.arquivoSaidaDocx;
import static br.com.fernando.automationframework.support.Generator.evidenciaArquivoWord;
import static br.com.fernando.automationframework.support.Screenshot.screenshotByte;

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
        killDriver();
    }

    @Before
    public void setUpTest() throws IOException {
        // Gera arquivo de evidencia a partir de um modelo
        evidenciaDocx = evidenciaArquivoWord(testName.getMethodName());
        dadosDoTeste = new HashMap<String, String>();
        resultadoDoTeste = "Failed";
    }
    @After
    public void tearDownTest() throws Exception {
        // Tira print do navegador
        evidence();

        // Salva a evidência montada em um arquivo word .docx
        fileOutput = arquivoSaidaDocx(testName.getMethodName(), evidenciaDocx, dadosDoTeste, resultadoDoTeste);
        evidenciaDocx.write(fileOutput);
        //converToPdf(evidenciaDocx, testName.getMethodName());
        fileOutput.flush();
        fileOutput.close();


        if (FrameworkProperties.CLOSE_BROWSER) {
            killDriver();
        }

    }

    protected static void inserirDadosTesteEvidencia(Map dadosDoTeste) {
        XWPFParagraph par1 = evidenciaDocx.createParagraph();
        XWPFRun runpar1 = par1.createRun();
        runpar1.setText("Dados utilizados no Teste:");
        runpar1.addBreak();
        runpar1.setText(dadosDoTeste.toString());
    }

    protected static void evidence() throws InvalidFormatException, InterruptedException {
        int width = (int) (1280 * 0.53);
        int height = (int) (720 * 0.53);
        Thread.sleep(500);
        String blipId = evidenciaDocx.addPictureData(screenshotByte(), XWPFDocument.PICTURE_TYPE_PNG);
        evidenciaDocx.createPicture(blipId,evidenciaDocx.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), width, height);
    }
}
