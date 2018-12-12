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
		// Definindo a dimensÃ£o que quer capturar
		// pode ser definido o tamanho que desejar
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		// criando o print screen
		Robot robot = new Robot();
		BufferedImage screenCapturedImage = robot.createScreenCapture(screenRect);

		// depois disso Ã© sÃ³ procurar a imagem no local indicado abaixo, no meu caso em:
		// /Users/rodrigogomes/printScreen.png
		// Aqui vocÃª pode alterar o formato da imagem para, por exemplo, JPG
		// Ã‰ sÃ³ mudar o â€œpngâ€� para â€œjpgâ€� e pronto
		StringBuilder path = new StringBuilder();
		path.append("report").append(File.separator).append(picName).append(".png");
		ImageIO.write(screenCapturedImage, "png", new File(path.toString()));
	}

	public static String takeAScreenshot() throws IOException {
		TakesScreenshot ss = (TakesScreenshot) getDriver();
		File arquivo = ss.getScreenshotAs(OutputType.FILE);

		String format = ".png";

		// Salva somente a primeira parte da String
		StringBuilder picname = new StringBuilder("autoframemark2_print").append(format);

		StringBuilder path = new StringBuilder();
		path.append("report").append(File.separator).append("html").append(File.separator).append(picname);
		
		FileUtils.copyFile(arquivo, new File(path.toString()));

		return picname.toString();
	}

	public static byte[] screenshotByte() {
		TakesScreenshot ss = (TakesScreenshot) getDriver();
		byte[] arquivo = ss.getScreenshotAs(OutputType.BYTES);
		return arquivo;
	}
}
