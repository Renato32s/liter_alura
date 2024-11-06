package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iAutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findAll();

    List<Autor> findByAnoNascimentoLessThanOrAnoFalecimentoGreaterThanEqual(int anoBusca, int anoBusca2);

    Optional<Autor> findFirstByNomeContainsIgnoreCase(String autor);
}
