package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.extractor.evidence.PhysicalEvidence;
import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PhysicalEvidenceRepository extends CrudRepository<PhysicalEvidence, Integer> {
    Set<PhysicalEvidence> getByExpert(Expert expert);
    void deleteByExpertAndInputAndLanguage(Expert expert, EvidenceSourceInput input, String language);
    Set<PhysicalEvidence> getByExpertAndInput(Expert expert, EvidenceSourceInput input);
    Set<PhysicalEvidence> getByExpertAndInputAndLanguage(Expert expert, EvidenceSourceInput input, String languageCode);
}
