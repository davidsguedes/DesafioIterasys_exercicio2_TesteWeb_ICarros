package base;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import pages.HomePage;


public class BaseTests {
	
	private static WebDriver driver;
	protected HomePage homePage;
	private static String dir = "C:\\Users\\Guedes\\driver\\chromedriver.exe";
	
		
	@BeforeAll
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", dir);
		driver = new ChromeDriver();
	}
	
	@BeforeEach
	public void carregarPaginaInicial() {
		driver.get("https://www.icarros.com.br/principal/index.jsp");
		homePage = new HomePage(driver);
	}
	
	@AfterAll
	public static void finalizar() throws IOException{
		
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File evidencia = screenshot.getScreenshotAs(OutputType.FILE);
		Files.copy(evidencia, new File(System.currentTimeMillis()+"screenshot.jpg"));
		driver.quit();
	}

}
