package br.com.fernando.automationframework.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DataHoraGen {

	public static String dataHoraParaArquivo() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return new SimpleDateFormat("ddMMyyyy_HHmmss").format(ts);
	}

	public static String dataHoraParaArquivo(String padrao) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return new SimpleDateFormat(padrao).format(ts);
	}

	public static String dataHoraParaPesquisa(String padrao, int dias) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DATE, -dias);

		return new SimpleDateFormat(padrao).format(dataAtual.getTime());
	}

}
