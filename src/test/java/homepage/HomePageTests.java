package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.AnunciosPage;

public class HomePageTests extends BaseTests {

	// 1) Crie uma consulta que retorne uma lista com pelo menos 3 carros usados da
	// mesma marca modelo, os demais critérios da consulta são a sua escolha
	
	// 2) Valide o modelo e o valor à vista do primeiro e do segundo carro da lista
	// produzida pela consulta
	
	// 3) O script deve ler a lista de resultados e criar um arquivo de dados
	// contendo marca modelo, ano, km, cor, câmbio e valor à vista de cada veiculo
	// retornado (apenas da primeira página de retorno)

	// 4) Outro script deve ler a lista de carros criada na tabela anterior, buscar
	// pelos veiculos e validar se os dados, especialmente o valor à vista continuam
	// os mesmos.

	AnunciosPage anunciosPage;

	@Test
	public void testValidarBuscaDeCarro_UsadosMesmaMarcaEModelo_MininimoDe3CarrosRetornados() throws IOException {

		String estadoDoCarro = "Usados";
		String marcaDoCarro = "Chevrolet";
		String modeloDoCarro = "Cruze";
		String localizacao = "Salvador - BA";

		carregarPaginaInicial();
		homePage.selecionarEstadoDoCarro(estadoDoCarro);

		homePage.obterOpcoesSelecionadas("marca");
		homePage.selecionarMarcaDoVeiculo(marcaDoCarro);
		homePage.obterOpcoesSelecionadas("modelo");
		homePage.selecionarModeloDoVeiculo(modeloDoCarro);
		homePage.alterarCriterioDeLocalizacao_InserirCidade(localizacao);

		anunciosPage = homePage.buscarCarro();

		List<String> modelos = anunciosPage.obterAmostraDeModelosDeVeiculos();
		List<String> valores = anunciosPage.obterAmostraDeValoresDeVeiculos();

		assertThat(modelos.size(), is(3));
		assertThat(valores.size(), is(3));
	}

	@Test
	public void testValidarBuscaDeCarro_ModeloEValorAVista_2Amostras() throws IOException {

		testValidarBuscaDeCarro_UsadosMesmaMarcaEModelo_MininimoDe3CarrosRetornados();

		String modelo1Obtido = anunciosPage.obterModeloDeCarro(1);
		String valor1Obtido = anunciosPage.obterValorDeCarro(1).replaceAll("\n", " ");
		String modelo2Obtido = anunciosPage.obterModeloDeCarro(2);
		String valor2Obtido = anunciosPage.obterValorDeCarro(2).replaceAll("\n", " ");
		List<String> modelos = anunciosPage.obterAmostraDeModelosDeVeiculos();
		List<String> valores = anunciosPage.obterAmostraDeValoresDeVeiculos();
		String modelo1 = modelos.subList(0, 1).toString().replaceAll("\\[(.*?)\\]", "$1");
		String modelo2 = modelos.subList(1, 2).toString().replaceAll("\\[(.*?)\\]", "$1");
		String valor1 = valores.subList(0, 1).toString().replaceAll("\n", " ").replaceAll("\r\n" + " preço à vista", "").replaceAll("\\[(.*?)\\]", "$1");
		String valor2 = valores.subList(1, 2).toString().replaceAll("\n", " ").replaceAll("\r\n" + " preço à vista", "").replaceAll("\\[(.*?)\\]", "$1");

		System.out.println(modelo1);
		System.out.println(valor1);

		System.out.println(modelo2);
		System.out.println(valor2);

		assertThat(modelo1, is(modelo1Obtido));
		assertThat(valor1, is(valor1Obtido));

		assertThat(modelo2, is(modelo2Obtido));
		assertThat(valor2, is(valor2Obtido));
		
		//gerar o arquivo de dados com as amostras
		anunciosPage.obterAmostraDeDadosDeVeiculos();
		anunciosPage.criarArquivoDeDadosDaAmostra();
	}

}
