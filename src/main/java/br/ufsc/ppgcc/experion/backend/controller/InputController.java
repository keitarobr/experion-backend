package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.service.InputService;
import br.ufsc.ppgcc.experion.extractor.evidence.PhysicalEvidence;
import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@RestController
public class InputController {

    @Autowired
    InputService service;

    @PostMapping("/input/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EvidenceSourceInput add(
            String name,
            String sourceName,
            String engineName) {
        return service.add(name, sourceName, engineName);
    }

    @PostMapping("/input/add-multiple")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<EvidenceSourceInput> addMultiple(
            String name,
            String sourceName,
            String engineNames) {
        return service.addMultiple(name, sourceName, engineNames);
    }

    @GetMapping("/input/find-by-name-containing")
    @PreAuthorize("permitAll()")
    public Set<EvidenceSourceInput> findByNameContaining(String name) {
        return service.findByNameContaining(name);
    }

    @DeleteMapping("/input/delete-by-name")
    @PreAuthorize("hasAuthority('ADMIN')")
    public EvidenceSourceInput deleteByName(String name) {
        return service.deleteByName(name);
    }

    @GetMapping("/input/find-all")
    @PreAuthorize("permitAll()")
    public Set<EvidenceSourceInput> findAll() {
        return service.findAll();
    }

    @GetMapping("/input/get-evidences")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Set<PhysicalEvidence> getEvidences(String inputName, String expertIdentification, String language) throws Exception {
        return service.getEvidences(inputName, expertIdentification, language);
    }

    @GetMapping("/input/find-expert-by-name")
    @PreAuthorize("permitAll()")
    public List<Expert> findExpertByName(String inputName, String expertName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        return service.findExpertByName(inputName, expertName);
    }

}
