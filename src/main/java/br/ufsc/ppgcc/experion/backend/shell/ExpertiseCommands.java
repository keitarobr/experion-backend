package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.service.ExpertiseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@ShellComponent
public class ExpertiseCommands {

    @Autowired
    ExpertiseService service;

    @ShellMethod("Adds an expertise builder registration.")
    public String addExpertiseBuilder(
            String name,
            String fullClassName) {
        return service.addExpertiseBuilder(name, fullClassName).toString();
    }

    @ShellMethod("Lists all registered expertise builders.")
    public String listExpertiseBuilders() {
        return StringUtils.join(service.findAllExpertiseBuilder(), "\n");
    }

    @ShellMethod("Creates a representation based on all expert evidences")
    public String generateExpertise(String expertIdentification, String generatorName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return StringUtils.join(service.generateExpertise(expertIdentification, generatorName), "\n");
    }



}
