package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.service.ProfileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@ShellComponent
public class ProfileCommands {

    @Autowired
    private ProfileService service;


    @ShellMethod("Adds a profile builder registration.")
    public String addProfileBuilder(
            String name,
            String fullClassName) {
        return service.addProfileBuilder(name, fullClassName).toString();
    }

    @ShellMethod("Lists all registered profile builders.")
    public String listProfileBuilders() {
        return StringUtils.join(service.listProfileBuilders(), "\n");
    }

    @ShellMethod("Creates a profile representation based on all expertise gathered for an expert")
    public String generateProfiles(String expertIdentification, String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return StringUtils.join(service.generateProfiles(expertIdentification, builderName), "\n");
    }

    @ShellMethod("Lists the profiles for a given expert")
    public String listProfiles(String expertIdentification) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return StringUtils.join(service.listProfiles(expertIdentification), "\n");
    }

}
