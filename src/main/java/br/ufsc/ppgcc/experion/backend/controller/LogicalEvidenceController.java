package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.service.LogicalEvidenceService;
import br.ufsc.ppgcc.experion.model.evidence.LogicalEvidence;
import br.ufsc.ppgcc.experion.model.evidence.builder.LogicalEvidenceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

@RestController
public class LogicalEvidenceController {

    @Autowired
    private LogicalEvidenceService service;

    @PostMapping("/logical-evidence/add-logical-evidence-builder")
    @PreAuthorize("hasAuthority('ADMIN')")
    public LogicalEvidenceBuilder addLogicalEvidenceBuilder(
            String name,
            String fullClassName) {
        return service.addLogicalEvidenceBuilder(name, fullClassName);
    }

    @GetMapping("/logical-evidence/list-logical-evidence-builder")
    @PreAuthorize("permitAll()")
    public List<LogicalEvidenceBuilder> listLogicalEvidenceBuilders() {
        return service.listLogicalEvidenceBuilders();
    }

    @GetMapping("/logical-evidence/list")
    @PreAuthorize("permitAll()")
    public Set<LogicalEvidence> listLogicalEvidences(String expertIdentification) {
        return service.listLogicalEvidences(expertIdentification);
    }

    @PostMapping("/logical-evidence/generate")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Set<LogicalEvidence> generateLogicalEvidences(
            String expertIdentification,
            String inputName,
            String languageCode,
            String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException {

        return service.generateLogicalEvidences(expertIdentification, inputName, languageCode, builderName);
    }
}
