package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import br.ufsc.ppgcc.experion.model.evidence.LogicalEvidence;
import br.ufsc.ppgcc.experion.model.evidence.builder.LogicalEvidenceBuilder;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface LogicalEvidenceRepository extends CrudRepository<LogicalEvidence, Integer> {
    void deleteByExpertAndBuilder(Expert expert, LogicalEvidenceBuilder builder);
    void deleteByExpert(Expert expert);
    Set<LogicalEvidence> getByExpert(Expert expert);

    @Query("delete from LogicalEvidence le WHERE le.expert=:expert AND le.builder=:builder AND le.language=:language AND EXISTS (select pe FROM le.physicalEvidences pe WHERE pe.input=:input)")
    @Modifying
    void deleteByExpertAndBuilderAndInputAndLanguage(Expert expert, LogicalEvidenceBuilder builder, EvidenceSourceInput input, String language);
}
