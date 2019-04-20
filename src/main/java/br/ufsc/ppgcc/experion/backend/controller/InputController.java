package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.model.repository.EvidenceSourceInputRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.EvidenceSourceRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.ExpertRepository;
import br.ufsc.ppgcc.experion.extractor.evidence.PhysicalEvidence;
import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import br.ufsc.ppgcc.experion.extractor.input.engine.EvidenceSourceInputEngine;
import br.ufsc.ppgcc.experion.extractor.source.EvidenceSource;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import br.ufsc.ppgcc.experion.model.expert.ExpertSourceIdentification;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
@Transactional
public class InputController {

    @Autowired
    EvidenceSourceRepository sourceRepository;

    @Autowired
    EvidenceSourceInputRepository inputRepository;

    @Autowired
    ExpertRepository expertRepository;

    @PostMapping("/input/add")
    @PreAuthorize("hasRole('ADMIN')")
    public EvidenceSourceInput add(
            String name,
            String sourceName,
            String engineName) {
        EvidenceSource source = sourceRepository.findByName(sourceName);
        EvidenceSourceInput input = new EvidenceSourceInput();
        input.setName(name);
        input.setEngineClassName(engineName);
        input.setSource(source);
        return inputRepository.save(input);
    }

    @PostMapping("/input/add-multiple")
    @PreAuthorize("hasRole('ADMIN')")
    public List<EvidenceSourceInput> addMultiple(
            String name,
            String sourceName,
            String engineNames) {

        EvidenceSource source = sourceRepository.findByName(sourceName);
        List<EvidenceSourceInput> result = new LinkedList<>();
        for (String engineName : StringUtils.split(engineNames, ',')) {

            EvidenceSourceInput input = new EvidenceSourceInput();
            input.setName(name + "-" + engineName);
            input.setEngineClassName(engineName);
            input.setSource(source);
            result.add(inputRepository.save(input));
        }

        return result;
    }

    @GetMapping("/input/find-by-name-containing")
    @PreAuthorize("permitAll()")
    public Set<EvidenceSourceInput> findByNameContaining(String name) {
        return inputRepository.findByNameContaining(name);
    }

    @DeleteMapping("/input/delete-by-name")
    @PreAuthorize("hasRole('ADMIN')")
    public EvidenceSourceInput deleteByName(String name) {
        EvidenceSourceInput input = inputRepository.findByName(name);
        if (input != null) {
            inputRepository.delete(input);
            return input;
        } else {
            return null;
        }
    }

    @GetMapping("/input/find-all")
    @PreAuthorize("permitAll()")
    public Set<EvidenceSourceInput> findAll() {
        //org.apache.commons.configuration2.beanutils.BeanHelper helper;
        return inputRepository.findAll();
    }

    @GetMapping("/input/get-evidences")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESEARCHER')")
    public Set<PhysicalEvidence> getEvidences(String inputName, String expertIdentification, String language) throws Exception {
        EvidenceSourceInput input = inputRepository.findByName(inputName);

        EvidenceSourceInputEngine engine = input.getEngine();
        engine.setLanguage(language);

        Expert expertRegistration = expertRepository.findByIdentification(expertIdentification);

        String expertIdInSource = null;

        for (ExpertSourceIdentification id : expertRegistration.getIdentifications()) {
            if (id.getSource().equals(input.getSource())) {
                expertIdInSource = id.getIdentification();
            }
        }

        if (expertIdInSource != null) {
            Expert expert = new Expert(expertIdInSource, expertRegistration.getName());
            return engine.getNewEvidences(expert, input);
        } else {
            return new HashSet<>();
        }
    }

    @GetMapping("/input/find-expert-by-name")
    @PreAuthorize("permitAll()")
    public List<Expert> findExpertByName(String inputName, String expertName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        EvidenceSourceInput sourceRegistration = inputRepository.findByName(inputName);
        List<Expert> result = new LinkedList<>();
        sourceRegistration.getEngine().findExpertByName(expertName).forEach(expert -> result.add(expert));
        return result;
    }

}
