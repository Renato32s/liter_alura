package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.iAutorRepository;
import br.com.alura.literalura.repository.iLivrosRepository;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;
import br.com.alura.literalura.service.LivroRequestAPI;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scan = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private static String API_URL = "https://gutendex.com/books/?search=";
    private List<Livro> dadosLivro = new ArrayList<>();
    private iLivrosRepository livrosRepositorio;
    private iAutorRepository autorRepositorio;
    public Principal(iLivrosRepository livrosRepositorio, iAutorRepository autorRepositorio) {
        this.livrosRepositorio = livrosRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    |***************************************************|
                    |*****                BEM-VINDO               ******|
                    |***************************************************|
                    
                    1 - Buscar livro por nome
                    
                    0 - sair
                    """;
            try {
                System.out.println(menu);
                opcao = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("|***************************************************|");
                System.out.println("|*****         insira um número válido        ******|");
                System.out.println("|***************************************************|");
                scan.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
            }
        }
    }

    private Livro pegaDadosLivros() {
        System.out.println("qual livro deseja buscar?: ");
        var nomeLivro = scan.nextLine().toLowerCase();
        var json = consumo.obterDados(API_URL + nomeLivro.replace(" ", "%20").trim());
        LivroRequestAPI dados = conversor.obterDados(json, LivroRequestAPI.class);
        if (dados != null && dados.getResultadoLivros() != null && !dados.getResultadoLivros().isEmpty()) {
            DadosLivro primeiroLivro = dados.getResultadoLivros().get(0);
            return new Livro(primeiroLivro);
        } else {
            System.out.println("livro não encontrado");
            return null;
        }
    }

    private void buscarLivro() {
        Livro livro = pegaDadosLivros();

        if (livro == null) {
            System.out.println("livro não encontrado, o valor é nulo");
            return;
        }
        try {
            boolean livroExiste = livrosRepositorio.existsByTitulo(livro.getTitulo());
            if (livroExiste) {
                System.out.println("esse livro já existe no banco de dados");
            } else {
                livrosRepositorio.save(livro);
                System.out.println(livro.toString());
            }
        } catch (InvalidDataAccessApiUsageException e) {
            System.out.println("não pode salvar o livro buscado");
        }
    }
}
