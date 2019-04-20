package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.model.evidence.builder.LogicalEvidenceBuilder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogicalEvidenceBuilderRepository extends CrudRepository<LogicalEvidenceBuilder, Integer> {
    List<LogicalEvidenceBuilder> findByNameContaining(String name);
    LogicalEvidenceBuilder findByName(String name);
}
