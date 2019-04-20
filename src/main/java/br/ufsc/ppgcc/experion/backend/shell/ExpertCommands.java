package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.controller.ExpertController;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@ShellComponent
@Transactional
public class ExpertCommands {

    @Autowired
    ExpertController controller;

    @ShellMethod("Adds an expert registration with birthdate.")
    public String addExpertWithBirthdate(
            String name,
            String identification,
            Date dateOfBirth) {
        return controller.addWithBirthdate(name, identification, dateOfBirth).toString();
    }

    @ShellMethod("Adds an expert registration.")
    public String addExpert(
            String name,
            String identification) {
        return controller.add(name, identification).toString();
    }

    @ShellMethod("Finds an expert by name.")
    public String findExpertByName(String name) {
        return StringUtils.join(controller.findByNameContaining(name), "\n");
    }

    @ShellMethod("Finds an expert by identification.")
    public String findExpertById(String name) {
        return StringUtils.join(controller.findByIdentificationContaining(name), "\n");
    }

    @ShellMethod("Lists all experts.")
    public String listExperts() {
        return StringUtils.join(controller.findAll(), "\n");
    }

    @ShellMethod("Lists all experts with per-source identification.")
    public String listExpertsWithIdentifications() {
        List<Expert> experts = controller.findAll();
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
        return controller.addExpertIdentification(expertIdentification, sourceName, identification).toString();
    }

    @ShellMethod("Collects expert evidences for a given input and language code (pt, en, es, etc.)")
    public String collectEvidences(String expertIdentification, String inputName, String language) {
        return StringUtils.join(controller.collectEvidences(expertIdentification, inputName, language), "\n");
    }

    @ShellMethod("Lists collected expert evidences for a given source")
    public String listCollectedEvidences(String expertIdentification, String inputName) {
        return StringUtils.join(controller.listEvidences(expertIdentification, inputName), "\n");
    }

    @ShellMethod("Lists collected expert evidences")
    public String listAllCollectedEvidences(String expertIdentification) {
        return StringUtils.join(controller.listAllCollectedEvidences(expertIdentification), "\n");
    }
}
