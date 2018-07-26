package br.com.fernando.automationframework.support;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static br.com.fernando.automationframework.core.DriverFactory.getDriver;

public class Screenshot {
	/*
	 * public static void takeAScreenShot(WebDriver browser, String arquivo) { File
	 * screenshot = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
	 * try { FileUtils.copyFile(screenshot, new File(arquivo)); } catch (IOException
	 * e) { System.out.println("Houveram problemas ao copiar o arquivo a pasta: " +
	 * e.getMessage()); e.printStackTrace(); } }
	 */
	public static void takeAPrint(String picName) throws Exception {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		// Definindo a dimensão que quer capturar
		// pode ser definido o tamanho que desejar
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		// criando o print screen
		Robot robot = new Robot();
		BufferedImage screenCapturedImage = robot.createScreenCapture(screenRect);

		// depois disso é só procurar a imagem no local indicado abaixo, no meu caso em:
		// /Users/rodrigogomes/printScreen.png
		// Aqui você pode alterar o formato da imagem para, por exemplo, JPG
		// É só mudar o “png” para “jpg” e pronto
		StringBuilder path = new StringBuilder();
		path.append("report").append(File.separator).append(picName).append(".png");
		ImageIO.write(screenCapturedImage, "png", new File(path.toString()));
	}

	public static void takeAScreenshot(String methodName) throws IOException {
		TakesScreenshot ss = (TakesScreenshot) getDriver();
		File arquivo = ss.getScreenshotAs(OutputType.FILE);

		// Salva somente a primeira parte da String
		StringBuilder picname = new StringBuilder(methodName.split("\\{")[0]);
		// Acrescenta data e hora ao restante do nome da foto
		picname.append("_").append(DataHoraGen.dataHoraParaArquivo());

		StringBuilder path = new StringBuilder();
		path.append("report").append(File.separator).append(picname).append(".png");

		FileUtils.copyFile(arquivo, new File(path.toString()));
	}

	public static byte[] screenshotByte() {
		TakesScreenshot ss = (TakesScreenshot) getDriver();
		byte[] arquivo = ss.getScreenshotAs(OutputType.BYTES);
		return arquivo;
	}
}
