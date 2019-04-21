package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.service.ExpertService;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Date;
import java.util.List;

@ShellComponent
public class ExpertCommands {

    @Autowired
    ExpertService service;

    @ShellMethod("Adds an expert registration with birthdate.")
    public String addExpertWithBirthdate(
            String name,
            String identification,
            Date dateOfBirth) {
        return service.addWithBirthdate(name, identification, dateOfBirth).toString();
    }

    @ShellMethod("Adds an expert registration.")
    public String addExpert(
            String name,
            String identification) {
        return service.add(name, identification).toString();
    }

    @ShellMethod("Finds an expert by name.")
    public String findExpertByName(String name) {
        return StringUtils.join(service.findByNameContaining(name), "\n");
    }

    @ShellMethod("Finds an expert by identification.")
    public String findExpertById(String name) {
        return StringUtils.join(service.findByIdentificationContaining(name), "\n");
    }

    @ShellMethod("Lists all experts.")
    public String listExperts() {
        return StringUtils.join(service.findAll(), "\n");
    }

    @ShellMethod("Lists all experts with per-source identification.")
    public String listExpertsWithIdentifications() {
        List<Expert> experts = service.findAll();
        StringBuilder result = new StringBuilder();
        for (Expert expert : experts) {
            result.append(expert).append("\n").append(StringUtils.join(expert.getIdentifications(), "\n")).append("\n");
        }
        return result.toString();
    }

    @ShellMethod("Adds an expert identification in a source.")
    public String addExpertIdentification(
            String expertIdentification,
            String sourceName,
            String identification) {
        return service.addExpertIdentification(expertIdentification, sourceName, identification).toString();
    }

    @ShellMethod("Collects expert evidences for a given input and language code (pt, en, es, etc.)")
    public String collectEvidences(String expertIdentification, String inputName, String language) {
        return StringUtils.join(service.collectEvidences(expertIdentification, inputName, language), "\n");
    }

    @ShellMethod("Lists collected expert evidences for a given source")
    public String listCollectedEvidences(String expertIdentification, String inputName) {
        return StringUtils.join(service.listEvidences(expertIdentification, inputName), "\n");
    }

    @ShellMethod("Lists collected expert evidences")
    public String listAllCollectedEvidences(String expertIdentification) {
        return StringUtils.join(service.listAllCollectedEvidences(expertIdentification), "\n");
    }
}
