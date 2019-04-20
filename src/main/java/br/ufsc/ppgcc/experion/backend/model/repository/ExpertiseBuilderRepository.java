package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.view.expertise.builder.ExpertiseBuilder;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ExpertiseBuilderRepository extends CrudRepository<ExpertiseBuilder, Integer> {
    ExpertiseBuilder findByName(String expertiseBuilderName);
    Set<ExpertiseBuilder> findAll();
}
