package br.ufsc.ppgcc.experion.backend.model.repository;


import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface EvidenceSourceInputRepository extends CrudRepository<EvidenceSourceInput, Integer> {
    Set<EvidenceSourceInput> findByNameContaining(String name);
    Set<EvidenceSourceInput> findByEngineClassNameContaining(String name);
    EvidenceSourceInput findByName(String name);
    Set<EvidenceSourceInput> findAll();
}
