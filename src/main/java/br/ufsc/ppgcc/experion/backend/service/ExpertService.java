package br.ufsc.ppgcc.experion.backend.service;

import br.ufsc.ppgcc.experion.backend.model.repository.*;
import br.ufsc.ppgcc.experion.extractor.evidence.PhysicalEvidence;
import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import br.ufsc.ppgcc.experion.extractor.source.EvidenceSource;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import br.ufsc.ppgcc.experion.model.expert.ExpertSourceIdentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpertService {

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private EvidenceSourceRepository sourceRepository;

    @Autowired
    private EvidenceSourceInputRepository inputRepository;

    @Autowired
    private PhysicalEvidenceRepository evidenceRepository;

    @Autowired
    private ExpertiseRepository expertiseRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LogicalEvidenceRepository logicalEvidenceRepository;

    public Expert addWithBirthdate(
            String name,
            String identification,
            Date dateOfBirth) {
        Expert expert = new Expert(identification, name, dateOfBirth);
        expert = expertRepository.save(expert);
        return expert;
    }

    public Expert add(
            String name,
            String identification) {
        Expert expert = new Expert(identification, name);
        expert = expertRepository.save(expert);
        return expert;
    }

    public Set<Expert> findByNameContaining(String name) {
        return expertRepository.findByNameContaining(name);
    }

    public Set<Expert> findByIdentificationContaining(String id) {
        return expertRepository.findByIdentificationContaining(id);
    }

    public List<Expert> findAll() {
        return (List<Expert>) expertRepository.findAll();
    }

    public Expert addExpertIdentification(String expertIdentification, String sourceName, String identification) {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        EvidenceSource source = sourceRepository.findByName(sourceName);
        expert.getIdentifications().add(new ExpertSourceIdentification(expert, source, identification));
        return expertRepository.save(expert);
    }

    public Expert deleteExpertIdentification(String expertIdentification, String sourceName, String identification) {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        EvidenceSource source = sourceRepository.findByName(sourceName);
        Set<ExpertSourceIdentification> ids = expert.getIdentifications().stream().filter(id -> id.getIdentification().equals(identification) && id.getSource().equals(source)).collect(Collectors.toSet());
        expert.getIdentifications().removeAll(ids);
        return expertRepository.save(expert);
    }

    public synchronized Set<PhysicalEvidence> collectEvidences(String expertIdentification, String inputName, String language) {
        // Clears old data
        // @todo try to keep all possible data or automatically regenerate it
        Expert expert = expertRepository.findByIdentification(expertIdentification);

            profileRepository.deleteByExpert(expert);
            expertiseRepository.deleteByExpert(expert);
            logicalEvidenceRepository.deleteByExpert(expert);

            EvidenceSourceInput input = inputRepository.findByName(inputName);
            evidenceRepository.deleteByExpertAndInputAndLanguage(expert, input, language);
            input.getEngine().setLanguage(language);
            Set<PhysicalEvidence> evidences = input.getEngine().getNewEvidences(expert, input);

            Set<PhysicalEvidence> result = new HashSet<>();
            for (PhysicalEvidence ev : evidences) {
                ev.setExpert(expert);
                ev.setInput(input);
                result.add(evidenceRepository.save(ev));
            }
            return result;
    }

    public Set<PhysicalEvidence> listEvidences(String expertIdentification, String inputName) {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        EvidenceSourceInput input = inputRepository.findByName(inputName);
        return evidenceRepository.getByExpertAndInput(expert, input);
    }

    public Set<PhysicalEvidence> listAllCollectedEvidences(String expertIdentification) {
        Expert expertRegistration = expertRepository.findByIdentification(expertIdentification);
        return evidenceRepository.getByExpert(expertRegistration);
    }

    public Set<PhysicalEvidence> listEvidencesByInputs(String expertIdentification, String[] inputNames) {
        Set<PhysicalEvidence> evidences = new HashSet<>();
        Arrays.stream(inputNames).forEach(input -> evidences.addAll(this.listEvidences(expertIdentification, input)));
        return evidences;
    }

    public Expert getByIdentification(String expertIdentification) {
        return expertRepository.findByIdentification(expertIdentification);
    }



}
