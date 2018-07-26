package br.com.fernando.automationframework.support;



import static br.com.fernando.automationframework.support.DataHoraGen.dataHoraParaArquivo;

import java.io.File;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class HTMLReportGen {
	
	public static String htmlReport(String reportName) {

		StringBuilder pathSaidaArquivo = new StringBuilder();
        pathSaidaArquivo
                .append("report")
                .append(File.separator)
                .append("html")
                .append(File.separator)
                .append(reportName)
                .append("_")
                .append(dataHoraParaArquivo())
                .append(".html");
		
		return pathSaidaArquivo.toString();
	}
	
	public static void htmlReportConfig(ExtentHtmlReporter htmlReporter, String reportTitle) {
		// make the charts visible on report open
		htmlReporter.config().setChartVisibilityOnOpen(true);
		// report title
		htmlReporter.config().setDocumentTitle(reportTitle);
		// encoding, default = UTF-8
		htmlReporter.config().setEncoding("UTF-8");
		// protocol (http, https)
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		// report or build name
		htmlReporter.config().setReportName(reportTitle);
		// chart location - top, bottom
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		// theme - standard, dark
		htmlReporter.config().setTheme(Theme.DARK);
		// set timeStamp format
		htmlReporter.config().setTimeStampFormat("mm/dd/yyyy hh:mm:ss a");
		// add custom css
		htmlReporter.config().setCSS("css-string");
		// add custom javascript
		htmlReporter.config().setJS("js-string");
		
	}
	

}
