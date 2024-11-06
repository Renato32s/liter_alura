package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idiomas;
    private Integer downloads;

    public Livro () {}

    public Livro (DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        if (dadosLivro.autores() != null && dadosLivro.autores().isEmpty()) {
            this.autor = new Autor(dadosLivro.autores().get(0));
        } else {
            this.autor = null;
        }
        this.idiomas = idiomaMod(dadosLivro.idiomas());
        this.downloads = dadosLivro.downloads();
    }

    private String idiomaMod(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()) {
            return "desconhecido";
        } else {
            return idiomas.get(0);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idiomas='" + idiomas + '\'' +
                ", downloads=" + downloads +
                '}';
    }
}
