package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

	WebDriver driver;
	
	private By carrosUsados = By.id("anunciosUsados");
	private By carrosNovos = By.id("anunciosNovos");
	private By marcaCarros = By.xpath("//*[@id=\"sltMake\"]");
	private By modeloCarros = By.xpath("//*[@id=\"sltModel\"]");
	private By criterioLocalizacao = By.xpath("//*[@id=\"buscaForm\"]/div[4]/div[1]/div[2]/div/span/a");
	private By localizacaoCidade = By.id("localizacaoCidade");
	private By cidade = By.id("cidade");
	
	private By buscarCarros = By.cssSelector("div:nth-child(7) > div.col-xs-4.p-top-xs > button");

	public HomePage(WebDriver driver) {
		super();
		this.driver = driver;
	}


	public void selecionarEstadoDoCarro(String estadoCarro) {

		WebElement novo = driver.findElement(carrosNovos);
		WebElement usado = driver.findElement(carrosUsados);

		novo.click();
		usado.click();

		if (estadoCarro.contains("Usados") && (!usado.isSelected())) {
			driver.findElement(carrosUsados).click();
		}

		if (estadoCarro.contains("0Km") && (!novo.isSelected())) {
			driver.findElement(carrosNovos).click();
		}

	}

	public void selecionarMarcaDoVeiculo(String marca) {
		selecionarOpcaoDropDown("marca", marca);
	}

	public void selecionarModeloDoVeiculo(String modelo) {
		selecionarOpcaoDropDown("modelo", modelo);
	}

	public void selecionarOpcaoDropDown(String tipoDropDown, String marcaDoCarro) {
		encontrarDropDownSize(tipoDropDown).selectByVisibleText(marcaDoCarro);
	}

	public Select encontrarDropDownSize(String dropDown) {

		switch (dropDown) {

		case "marca":

			return new Select(driver.findElement(marcaCarros));

		case "modelo":
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			return new Select(driver.findElement(modeloCarros));

		}
		return null;

	}

	public List<String> obterOpcoesSelecionadas(String dropDown) {

		List<WebElement> elementosSelecionados = encontrarDropDownSize(dropDown).getAllSelectedOptions();

		List<String> listaOpcoesSelecionadas = new ArrayList<>();
		for (WebElement elemento : elementosSelecionados) {
			listaOpcoesSelecionadas.add(elemento.getText());
		}
		return listaOpcoesSelecionadas;

	}
	
	
	public void alterarCriterioDeLocalizacao() {
		driver.findElement(criterioLocalizacao).click();
	}
	
	public void alterarCriterioDeLocalizacao_Cidade() {
		driver.findElement(localizacaoCidade).click();
		
	}
	
	public void alterarCriterioDeLocalizacao_InserirCidade(String texto) {
		alterarCriterioDeLocalizacao();
		alterarCriterioDeLocalizacao_Cidade();
		driver.findElement(cidade).sendKeys(texto);
	}
	

	public AnunciosPage buscarCarro() {
		driver.findElement(buscarCarros).click();
		return new AnunciosPage(driver);

	}
	
	

}
