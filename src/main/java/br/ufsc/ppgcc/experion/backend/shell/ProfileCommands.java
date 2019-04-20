package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.controller.ProfileController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@ShellComponent
@Transactional
public class ProfileCommands {

    @Autowired
    private ProfileController controller;


    @ShellMethod("Adds a profile builder registration.")
    public String addProfileBuilder(
            String name,
            String fullClassName) {
        return controller.addProfileBuilder(name, fullClassName).toString();
    }

    @ShellMethod("Lists all registered profile builders.")
    public String listProfileBuilders() {
        return StringUtils.join(controller.listProfileBuilders(), "\n");
    }

    @ShellMethod("Creates a profile representation based on all expertise gathered for an expert")
    public String generateProfiles(String expertIdentification, String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return StringUtils.join(controller.generateProfiles(expertIdentification, builderName), "\n");
    }

    @ShellMethod("Lists the profiles for a given expert")
    public String listProfiles(String expertIdentification) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return StringUtils.join(controller.listProfiles(expertIdentification), "\n");
    }

}
