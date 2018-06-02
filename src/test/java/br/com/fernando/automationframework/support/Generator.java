package br.com.fernando.automationframework.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Generator {

    public static String dataHoraParaArquivo() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return new SimpleDateFormat("ddMMyyyy_HHmmss").format(ts);
    }
    public static String dataHoraParaArquivo(String padrao) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return new SimpleDateFormat(padrao).format(ts);
    }

    public static CustomXWPFDocument evidenciaArquivoWord(String methodName) throws IOException {

        StringBuilder nomeCenario = new StringBuilder(methodName.split("\\{")[0]);

        CustomXWPFDocument document = new CustomXWPFDocument(
                new FileInputStream(
                        new File("src\\test\\resources\\padrao.docx")));

        XWPFParagraph par1 = document.createParagraph();
        XWPFRun runpar1 = par1.createRun();
        runpar1.setText("Evid�ncia de execu��o automatizada.");
        runpar1.addBreak();
        runpar1.setText("Cen�rio: " + nomeCenario);
        runpar1.addBreak();
        runpar1.setText("Data e Hora da Execu��o: " + dataHoraParaArquivo("dd/MM/yyyy - HH:mm:ss"));

        return document;
    }

    public static FileOutputStream arquivoSaidaDocx(String methodName, CustomXWPFDocument document) throws FileNotFoundException {

        StringBuilder nomeCenario = new StringBuilder(methodName.split("\\{")[0]);

        StringBuilder pathSaidaArquivo = new StringBuilder();
        pathSaidaArquivo
                .append("report")
                .append(File.separator)
                .append(nomeCenario)
                .append(dataHoraParaArquivo())
                .append(".docx");

        return new FileOutputStream(new File(pathSaidaArquivo.toString()));
    }
}
