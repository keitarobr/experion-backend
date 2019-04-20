package br.ufsc.ppgcc.experion.backend.service;

import br.ufsc.ppgcc.experion.backend.model.repository.*;
import br.ufsc.ppgcc.experion.extractor.evidence.PhysicalEvidence;
import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import br.ufsc.ppgcc.experion.model.evidence.LogicalEvidence;
import br.ufsc.ppgcc.experion.model.evidence.builder.LogicalEvidenceBuilder;
import br.ufsc.ppgcc.experion.model.evidence.builder.engine.LogicalEvidenceBuilderEngine;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class LogicalEvidenceService {

    @Autowired
    LogicalEvidenceBuilderRepository logicalEvidenceBuilderRepository;

    @Autowired
    LogicalEvidenceRepository logicalEvidenceRepository;

    @Autowired
    ExpertRepository expertRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    EvidenceSourceInputRepository inputRepository;

    @Autowired
    PhysicalEvidenceRepository physicalEvidenceRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public LogicalEvidenceBuilder addLogicalEvidenceBuilder(
            String name,
            String fullClassName) {
        LogicalEvidenceBuilder registration = new LogicalEvidenceBuilder();
        registration.setName(name);
        registration.setEngineClassName(fullClassName);
        return logicalEvidenceBuilderRepository.save(registration);
    }

    public List<LogicalEvidenceBuilder> listLogicalEvidenceBuilders() {
        return (List<LogicalEvidenceBuilder>) logicalEvidenceBuilderRepository.findAll();
    }

    public Set<LogicalEvidence> listLogicalEvidences(String expertIdentification) {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        return logicalEvidenceRepository.getByExpert(expert);
    }

    public synchronized Set<LogicalEvidence> generateLogicalEvidences(
            String expertIdentification,
            String inputName,
            String languageCode,
            String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException {

        Expert expert = expertRepository.findByIdentification(expertIdentification);

            EvidenceSourceInput input = inputRepository.findByName(inputName);
            LogicalEvidenceBuilder generatorRegistration = logicalEvidenceBuilderRepository.findByName(builderName);

            // Clears old data
            // @todo try to keep all possible data or automatically regenerate it
            profileRepository.deleteByExpert(expert);
            expertiseRepository.deleteByExpert(expert);

            System.err.println("Deleting old logical evidence");
            logicalEvidenceRepository.deleteByExpertAndBuilderAndInputAndLanguage(expert, generatorRegistration, input, languageCode);
            System.err.println("Deleted old logical evidence");

            Class generatorClass = Class.forName(generatorRegistration.getEngineClassName());
            LogicalEvidenceBuilderEngine builder = (LogicalEvidenceBuilderEngine) generatorClass.getConstructor().newInstance();

            Set<PhysicalEvidence> physicalEvidences = physicalEvidenceRepository.getByExpertAndInputAndLanguage(expert, input, languageCode);

            Set<LogicalEvidence> generatedEvidences = builder.getLogicalEvidences(physicalEvidences);

            for (LogicalEvidence ev : generatedEvidences) {
                ev.setBuilder(generatorRegistration);
                ev.setExpert(expert);
                ev.setLanguage(languageCode);
                logicalEvidenceRepository.save(ev);
            }


            return generatedEvidences;

    }

}
