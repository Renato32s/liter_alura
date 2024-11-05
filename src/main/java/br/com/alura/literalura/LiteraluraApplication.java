package br.com.alura.literalura;

import br.com.alura.literalura.model.Dados;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumo = new ConsumoAPI();
		var conversor = new ConverteDados();
		var json = consumo.obterDados("https://gutendex.com/books/?search=dickens%20great");
		System.out.println(json);
		Dados dados = conversor.obterDados(json, Dados.class);
		System.out.println(dados);
	}
}
