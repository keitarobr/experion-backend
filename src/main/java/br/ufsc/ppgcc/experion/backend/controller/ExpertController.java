package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.service.ExpertService;
import br.ufsc.ppgcc.experion.extractor.evidence.PhysicalEvidence;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ExpertController {

    @Autowired
    private ExpertService service;

    @PostMapping("/expert/add-with-birthdate")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Expert addWithBirthdate(
            String name,
            String identification,
            Date dateOfBirth) {
        return service.addWithBirthdate(name, identification, dateOfBirth);
    }

    @PostMapping("/expert/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Expert add(
            String name,
            String identification) {
        return service.add(name, identification);
    }

    @GetMapping("/expert/find-by-name-containing")
    @PreAuthorize("permitAll()")
    public Set<Expert> findByNameContaining(String name) {
        return service.findByNameContaining(name);
    }

    @GetMapping("/expert/find-by-identification-containing")
    @PreAuthorize("permitAll()")
    public Set<Expert> findByIdentificationContaining(String id) {
        return service.findByIdentificationContaining(id);
    }

    @GetMapping("/expert/find-all")
    @PreAuthorize("permitAll()")
    public List<Expert> findAll() {
        return service.findAll();
    }

    @PostMapping("/expert/add-expert-identification")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Expert addExpertIdentification(String expertIdentification, String sourceName, String identification) {
        return service.addExpertIdentification(expertIdentification, sourceName, identification);
    }

    @PostMapping("/expert/delete-expert-identification")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Expert deleteExpertIdentification(String expertIdentification, String sourceName, String identification) {
        return service.deleteExpertIdentification(expertIdentification, sourceName, identification);
    }

    @PostMapping("/expert/collect-evidences")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Set<PhysicalEvidence> collectEvidences(String expertIdentification, String inputName, String language) {
        Expert expert = service.getByIdentification(expertIdentification);
        return service.collectEvidences(expertIdentification, inputName, language);
    }

    @GetMapping("/expert/list-evidences")
    @PreAuthorize("permitAll()")
    public Set<PhysicalEvidence> listEvidences(String expertIdentification, String inputName) {
        return service.listEvidences(expertIdentification, inputName);
    }

    @GetMapping("/expert/list-all-collected-evidences")
    @PreAuthorize("hasAuthority('USER')")
    public Set<PhysicalEvidence> listAllCollectedEvidences(String expertIdentification) {
        return service.listAllCollectedEvidences(expertIdentification);
    }

    @GetMapping("/expert/list-evidences-by-inputs")
    @PreAuthorize("hasAuthority('USER')")
    public Set<PhysicalEvidence> listEvidencesByInputs(String expertIdentification, String[] inputNames) {
        return service.listEvidencesByInputs(expertIdentification, inputNames);
    }


}
