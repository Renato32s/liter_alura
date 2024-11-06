package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface iLivrosRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);

    Livro findByTituloContainsIgnoreCase(String titulo);

    List<Livro> findByIdiomas(String idiomas);

    @Query("SELECT l FROM Livro l ORDER BY l.downloads DESC LIMIT 10")
    List<Livro> findTop10ByTituloByDownloads();
}
