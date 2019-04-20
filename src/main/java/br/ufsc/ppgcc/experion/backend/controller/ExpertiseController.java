package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.service.ExpertiseService;
import br.ufsc.ppgcc.experion.view.expertise.Expertise;
import br.ufsc.ppgcc.experion.view.expertise.builder.ExpertiseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ExpertiseController {

    @Autowired
    private ExpertiseService service;

    @PostMapping("/expertise/add-expertise-builder")
    @PreAuthorize("hasRole('ADMIN')")
    public ExpertiseBuilder addExpertiseBuilder(
            String name,
            String fullClassName) {
        return service.addExpertiseBuilder(name, fullClassName);
    }

    @GetMapping("/expertise/find-all-expertise-builders")
    @PreAuthorize("permitAll()")
    public Set<ExpertiseBuilder> findAllExpertiseBuilder() {
        return service.findAllExpertiseBuilder();
    }

    @PostMapping("/expertise/generate-expertise")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESEARCHER')")
    public Set<Expertise> generateExpertise(String expertIdentification, String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return service.generateExpertise(expertIdentification, builderName);
    }

    @PostMapping("/expertise/find-all-expertise")
    @PreAuthorize("permitAll()")
    public Set<Expertise> findAllExpertise(String expertIdentification) {
        return service.findAllExpertise(expertIdentification);
    }

    @GetMapping("/expertise/download-description")
    @PreAuthorize("permitAll()")
    public ExpertiseService.DownloadeableExpertiseDescription downloadExpertiseDescription(Integer expertiseId) {
        return service.downloadExpertiseDescription(expertiseId);
    }

    @PostMapping("/expertise/find-all-expertise-description")
    @PreAuthorize("permitAll()")
    public Set<ExpertiseService.DownloadeableExpertiseDescription> findAllExpertiseDescription(String expertIdentification) {
        Set<Expertise> expertise = service.findAllExpertise(expertIdentification);
        return expertise.stream().map(exp -> service.downloadExpertiseDescription(exp.getId())).collect(Collectors.toSet());
    }
}
