package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.model.expert.Expert;
import br.ufsc.ppgcc.experion.view.expertise.Expertise;
import br.ufsc.ppgcc.experion.view.expertise.builder.ExpertiseBuilder;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ExpertiseRepository extends CrudRepository<Expertise, Integer> {
    void deleteByExpert(Expert expert);
    void deleteByExpertAndBuilder(Expert expert, ExpertiseBuilder builder);
    Set<Expertise> findByExpert(Expert expert);
}
