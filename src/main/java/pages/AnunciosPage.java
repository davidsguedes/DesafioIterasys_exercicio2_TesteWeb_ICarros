package pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AnunciosPage {

	WebDriver driver;

	private By paginaDeRegistro = By.xpath("//*[@class='direita']//li[@class='selected']");

	private By listaDeModeloDosAnunciosRetornados = By.xpath("//*[@class='clearfix anuncios']//h2");
	private By listaDeValoresDosAnunciosRetornados = By.xpath("//*[@class='clearfix anuncios']//h3");

	private By listaDeDadosDosAnunciosRetornados = By.xpath("//*[@class='clearfix']/ul[@class='listahorizontal']");
	
	
	private By quantidadeDeAnunciosRetornados = By.xpath("//*[@class='clearfix anuncios']//li[@id and @class]");

	public AnunciosPage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public List<String> obterAmostraDeDadosDeVeiculos() {

		int pagina = Integer.parseInt(driver.findElement(paginaDeRegistro).getText());

		if (pagina == 1) {

			List<WebElement> dadosDoVeiculo = driver.findElements(listaDeDadosDosAnunciosRetornados).subList(0, 3);

			List<String> listaDadosDoVeiculo = new ArrayList<>();

			for (WebElement elemento : dadosDoVeiculo) {

				listaDadosDoVeiculo.add(elemento.getText());
			}

			return listaDadosDoVeiculo;
		}
		return null;
	}
	
	
	public List<String> obterAmostraDeModelosDeVeiculos() {

		List<WebElement> modelo = driver.findElements(listaDeModeloDosAnunciosRetornados).subList(0, 3);

		List<String> listaModelosDoVeiculo = new ArrayList<>();

		for (WebElement elemento : modelo) {
			listaModelosDoVeiculo.add(elemento.getText());
		}
		return listaModelosDoVeiculo;

	}

	public List<String> obterAmostraDeValoresDeVeiculos() {

		List<WebElement> valor = driver.findElements(listaDeValoresDosAnunciosRetornados).subList(0, 3);

		List<String> listaValorDoVeiculo = new ArrayList<>();
		for (WebElement elemento : valor) {
			listaValorDoVeiculo.add(elemento.getText());
		}
		return listaValorDoVeiculo;

	}

	
	public void criarArquivoDeDadosDaAmostra() throws IOException {

		List<WebElement> veiculos = driver.findElements(quantidadeDeAnunciosRetornados).subList(0, 3);
		String modelo = null;
		String valor = null;
		String dados = null;

		File arq = new File(
				"C:\\Users\\Guedes\\Documents\\David\\Iterasys-IRTS-TestesWeb\\exercicio2_TesteWeb_ICarros\\src\\main\\resources",
				"amostra_"+System.currentTimeMillis()+".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(arq));
		bw.write("Amostras obtidas\n\n\n");
		for (int i = 0; i < veiculos.size(); i++) {
				
			modelo = obterAmostraDeModelosDeVeiculos().get(i).toString().replaceAll("\n", " ");
			valor = obterAmostraDeValoresDeVeiculos().get(i).toString().replaceAll("\n", " ");
			dados = obterAmostraDeDadosDeVeiculos().get(i).toString().replaceAll("\n", " ");
			
			bw.write("-------------------------------------------------------\n\n\n");
			bw.write(i+1+"-"+modelo+"\n\n");
			bw.write(valor+"\n\n");
			bw.write(dados+"\n\n");
			bw.write("-------------------------------------------------------\n\n\n");
		}
		bw.flush();
		bw.close();
	}

	
	public String obterModeloDeCarro(int indice) {
		String modelo = driver
				.findElement(
						By.xpath("//*[@class='listavertical']/li[" + indice + "]//*[@class='esquerda titulo_anuncio']"))
				.getText();
		return modelo;
	}

	public String obterValorDeCarro(int indice) {
		String valor = driver
				.findElement(
						By.xpath("//*[@class='listavertical']/li[" + indice + "]//*[@class='direita preco_anuncio']"))
				.getText();
		return valor;
	}

}
