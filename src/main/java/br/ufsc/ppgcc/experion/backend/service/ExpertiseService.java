package br.ufsc.ppgcc.experion.backend.service;

import br.ufsc.ppgcc.experion.backend.model.repository.*;
import br.ufsc.ppgcc.experion.model.evidence.LogicalEvidence;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import br.ufsc.ppgcc.experion.view.expertise.Expertise;
import br.ufsc.ppgcc.experion.view.expertise.builder.ExpertiseBuilder;
import br.ufsc.ppgcc.experion.view.expertise.builder.engine.ExpertiseBuilderEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;


@Transactional
@Service
public class ExpertiseService {

    @Autowired
    ExpertRepository expertRepository;

    @Autowired
    LogicalEvidenceRepository logicalEvidenceRepository;

    @Autowired
    ExpertiseBuilderRepository expertiseBuilderRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    ProfileRepository profileRepository;


    public static class DownloadablePhysicalEvidence {
        public String evidence;
        public String retrievedData;
    }

    public static class DownloadableLogicalEvidence {
        public String concept;
        public Set<DownloadablePhysicalEvidence> physicalEvidences = new HashSet<>();
    }

    public static class DownloadeableExpertiseDescription {
        public String expertise;
        public Set<DownloadableLogicalEvidence> logicalEvidences = new HashSet<>();
    }


    public DownloadeableExpertiseDescription downloadExpertiseDescription(Integer expertiseId) {
        Expertise expertise = this.expertiseRepository.findById(expertiseId).get();
        DownloadeableExpertiseDescription description = new DownloadeableExpertiseDescription();
        description.expertise = expertise.getDescription();

        expertise.getEvidences().stream().forEach(evidence -> {
            DownloadableLogicalEvidence dle = new DownloadableLogicalEvidence();
            dle.concept = evidence.getConcept();

            evidence.getPhysicalEvidences().stream().forEach(physicalEvidence -> {
                DownloadablePhysicalEvidence dpe = new DownloadablePhysicalEvidence();
                dpe.evidence = StringUtils.join(physicalEvidence.getKeywords(), ",");
                dpe.retrievedData = physicalEvidence.getUrl().getRetrievedData();

                dle.physicalEvidences.add(dpe);
            });

            description.logicalEvidences.add(dle);
        });

        return description;
    }

    public ExpertiseBuilder addExpertiseBuilder(
            String name,
            String fullClassName) {
        ExpertiseBuilder builder = new ExpertiseBuilder();
        builder.setName(name);
        builder.setEngineClassName(fullClassName);
        return expertiseBuilderRepository.save(builder);
    }

    public Set<ExpertiseBuilder> findAllExpertiseBuilder() {
        return expertiseBuilderRepository.findAll();
    }

    public Set<Expertise> generateExpertise(String expertIdentification, String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        ExpertiseBuilder builderRegistration = expertiseBuilderRepository.findByName(builderName);

        ExpertiseBuilderEngine builder = builderRegistration.getEngine();

        Set<LogicalEvidence> evidences = new HashSet<>();
        for (LogicalEvidence evidence : logicalEvidenceRepository.getByExpert(expert)) {
            evidences.add(evidence);
        }

        // Clears old data
        // @todo try to keep all possible data or automatically regenerate it
        profileRepository.deleteByExpert(expert);
        expertiseRepository.deleteByExpertAndBuilder(expert, builderRegistration);

        Set<Expertise> expertise = builder.buildExpertise(evidences);

        Set<Expertise> result = new HashSet<>();
        for (Expertise exp : expertise) {
            exp.setEvidences(logicalEvidenceRepository.getByExpert(expert));
            exp.setExpert(expert);
            exp.setBuilder(builderRegistration);
            result.add(expertiseRepository.save(exp));
        }

        return result;
    }

    public Set<Expertise> findAllExpertise(String expertIdentification) {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        return expertiseRepository.findByExpert(expert);
    }

}
