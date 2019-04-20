package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.controller.ExpertiseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@ShellComponent
@Transactional
public class ExpertiseCommands {

    @Autowired
    ExpertiseController controller;

    @ShellMethod("Adds an expertise builder registration.")
    public String addExpertiseBuilder(
            String name,
            String fullClassName) {
        return controller.addExpertiseBuilder(name, fullClassName).toString();
    }

    @ShellMethod("Lists all registered expertise builders.")
    public String listExpertiseBuilders() {
        return StringUtils.join(controller.findAllExpertiseBuilder(), "\n");
    }

    @ShellMethod("Creates a representation based on all expert evidences")
    public String generateExpertise(String expertIdentification, String generatorName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return StringUtils.join(controller.generateExpertise(expertIdentification, generatorName), "\n");
    }



}
