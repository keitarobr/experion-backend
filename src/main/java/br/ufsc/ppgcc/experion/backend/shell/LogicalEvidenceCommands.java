package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.service.LogicalEvidenceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@ShellComponent
public class LogicalEvidenceCommands {

    @Autowired
    private LogicalEvidenceService service;

    @ShellMethod("Adds an logical evidence builder registration.")
    public String addLogicalEvidenceBuilder(
            String name,
            String fullClassName) {
        return service.addLogicalEvidenceBuilder(name, fullClassName).toString();
    }

    @ShellMethod("List installed logical evidence generators.")
    public String listLogicalEvidenceGenerators() {
        return StringUtils.join(service.listLogicalEvidenceBuilders(), "\n");
    }

    @ShellMethod("Lists logical evidences for an expert")
    public String listLogicalEvidences(String expertIdentification) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return StringUtils.join(service.listLogicalEvidences(expertIdentification), "\n");
    }

    @ShellMethod("Generates logical evidences for an expert, given an input and a language")
    public String generateLogicalEvidences(
            String expertIdentification,
            String inputName,
            String languageCode,
            String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, InterruptedException {
        return StringUtils.join(service.generateLogicalEvidences(expertIdentification, inputName, languageCode, builderName), "\n");
    }


}
