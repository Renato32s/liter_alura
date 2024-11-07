package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.model.Results;
import br.com.alura.literalura.repository.iAutorRepository;
import br.com.alura.literalura.repository.iLivrosRepository;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner scan = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private iLivrosRepository livrosRepositorio;
    private iAutorRepository autorRepositorio;
    private static String API_URL = "https://gutendex.com/books/?search=";

    List<Livro> livros;
    List<Autor> autor;

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
                    
                    |***************************************************|
                    |*****            ESCOLHA UMA OPÇÂO           ******|
                    |***************************************************|
                    
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
                case 0:
                    System.out.println("|********************************|");
                    System.out.println("|     ENCERRANDO A AOLICAÇÃO     |");
                    System.out.println("|********************************|\n");
                    break;
                default:
                    System.out.println("|********************************|");
                    System.out.println("|         OPCÃO INCORRETA        |");
                    System.out.println("|********************************|\n");
                    System.out.println("TENTE NOVAMENTE");
                    exibeMenu();
                    break;
            }
        }
    }

    private void buscarLivro() {
        System.out.println("qual livro deseja buscar?: ");
        var nomeLivro = scan.nextLine().toLowerCase();
        var json = consumo.obterDados(API_URL + nomeLivro.replace(" ", "%20").trim());
        var dados = conversor.obterDados(json, Results.class);
        if (dados.results().isEmpty()) {
            System.out.println("livro não encontrado");;
        } else {
            DadosLivro dadosLivro = dados.results().get(0);
            Livro livro = new Livro(dadosLivro);
            Autor autor = new Autor().pegaAutor(dadosLivro);
            salvarDados(livro, autor);
        }
    }

    private void salvarDados(Livro livro, Autor autor) {
        Optional<Livro> livroEncontrado = livrosRepositorio.findByTituloContains(livro.getTitulo());
        if (livroEncontrado.isPresent()) {
            System.out.println("esse livro já existe no banco de dados");
        } else {
            try {
                livrosRepositorio.save(livro);
                System.out.println("livro guardado");
                System.out.println(livro);
            } catch (Exception e) {
                System.out.println("erro: " + e.getMessage());
            }
        }

        Optional<Autor> autorEncontrado = autorRepositorio.findByNomeContains(autor.getNome());
        if (autorEncontrado.isPresent()) {
            System.out.println("esse autor já existe no banco de dados");
        } else {
            try {
                autorRepositorio.save(autor);
                System.out.println("autor guardado");
                System.out.println(autor);
            } catch (Exception e) {
                System.out.println("erro: " + e.getMessage());
            }
        }
    }

}
