package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.service.InputService;
import br.ufsc.ppgcc.experion.extractor.input.EvidenceSourceInput;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class InputCommands {

    @Autowired
    private InputService service;


    @ShellMethod("Adds an input.")
    public String addInput(
            String name,
            String sourceName,
            String className) {
        return service.add(name, sourceName, className).toString();
    }

    @ShellMethod("Adds an input with multiple engines.")
    public String addInputMultiple(
            String name,
            String sourceName,
            String extractorNames) {
        return StringUtils.join(service.addMultiple(name, sourceName, extractorNames), "\n");
    }

    @ShellMethod("Finds an input by name.")
    public String findInputByName(String name) {
        return StringUtils.join(service.findByNameContaining(name), "\n");
    }

    @ShellMethod("Deletes an input by name.")
    public String deleteInputByName(String name) {
        EvidenceSourceInput input = service.deleteByName(name);
        if (input == null) {
            return "not found";
        } else {
            return "deleted " + input;
        }
    }

    @ShellMethod("Lists all inputs.")
    public String listInputs() {
        return StringUtils.join(service.findAll(), "\n");
    }


    @ShellMethod("Get evidences from an input for an expert .")
    public String getEvidences(String inputName, String expertIdentification, String language) throws Exception {
        return StringUtils.join(service.getEvidences(inputName, expertIdentification, language), "\n");
    }

}
