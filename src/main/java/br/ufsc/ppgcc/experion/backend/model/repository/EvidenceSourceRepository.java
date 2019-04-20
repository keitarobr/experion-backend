package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.extractor.source.EvidenceSource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EvidenceSourceRepository extends CrudRepository<EvidenceSource, Integer> {
    List<EvidenceSource> findByNameContaining(String name);
    EvidenceSource findByName(String name);
}
