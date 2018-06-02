package br.com.fernando.automationframework.core;

import br.com.fernando.automationframework.support.CustomXWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.io.FileOutputStream;
import java.io.IOException;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;
import static br.com.fernando.automationframework.core.DriverFactory.killDriver;
import static br.com.fernando.automationframework.support.Generator.arquivoSaidaDocx;
import static br.com.fernando.automationframework.support.Generator.evidenciaArquivoWord;
import static br.com.fernando.automationframework.support.Screenshot.screenshotByte;

public class BaseTest {

    public static FileOutputStream fileOutput = null;
    public static CustomXWPFDocument evidenciaDocx = null;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUpTest() throws IOException {
        getDriver();

        // Gera arquivo de evidencia a partir de um modelo
        evidenciaDocx = evidenciaArquivoWord(testName.getMethodName());


    }

    @After
    public void tearDownTest() throws Exception {

        // Tira print do navegador
        evidence();

        // Salva a evid�ncia montada em um arquivo word .docx
        fileOutput = arquivoSaidaDocx(testName.getMethodName(), evidenciaDocx);
        evidenciaDocx.write(fileOutput);
        fileOutput.flush();
        fileOutput.close();

        if (FrameworkProperties.CLOSE_BROWSER) {
            killDriver();
        }

    }

    protected static void evidence() throws InvalidFormatException {
        int width = (int) (1280 * 0.53);
        int height = (int) (720 * 0.53);
        String blipId = evidenciaDocx.addPictureData(screenshotByte(), XWPFDocument.PICTURE_TYPE_PNG);
        evidenciaDocx.createPicture(blipId,evidenciaDocx.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), width, height);
    }
}
