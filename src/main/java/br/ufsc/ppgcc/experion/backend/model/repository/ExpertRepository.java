package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ExpertRepository extends CrudRepository<Expert, Integer> {
    Set<Expert> findByNameContaining(String name);
    Set<Expert> findByIdentificationContaining(String identification);
    Expert findByIdentification(String identification);
}
