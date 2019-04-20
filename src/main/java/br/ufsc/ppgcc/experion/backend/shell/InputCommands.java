package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.controller.InputController;
import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;

@ShellComponent
@Transactional
public class InputCommands {

    @Autowired
    private InputController controller;


    @ShellMethod("Adds an input.")
    public String addInput(
            String name,
            String sourceName,
            String className) {
        return controller.add(name, sourceName, className).toString();
    }

    @ShellMethod("Adds an input with multiple engines.")
    public String addInputMultiple(
            String name,
            String sourceName,
            String extractorNames) {
        return StringUtils.join(controller.addMultiple(name, sourceName, extractorNames), "\n");
    }

    @ShellMethod("Finds an input by name.")
    public String findInputByName(String name) {
        return StringUtils.join(controller.findByNameContaining(name), "\n");
    }

    @ShellMethod("Deletes an input by name.")
    public String deleteInputByName(String name) {
        EvidenceSourceInput input = controller.deleteByName(name);
        if (input == null) {
            return "not found";
        } else {
            return "deleted " + input;
        }
    }


    @ShellMethod("Lists all inputs.")
    public String listInputs() {
        return StringUtils.join(controller.findAll(), "\n");
    }


    @ShellMethod("Get evidences from an input for an expert .")
    public String getEvidences(String inputName, String expertIdentification, String language) throws Exception {
        return StringUtils.join(controller.getEvidences(inputName, expertIdentification, language), "\n");
    }

}
