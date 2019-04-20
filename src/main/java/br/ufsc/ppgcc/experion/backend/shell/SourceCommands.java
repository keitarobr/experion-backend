package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.controller.SourceController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SourceCommands {

    @Autowired
    private SourceController controller;

    @ShellMethod("Adds a source.")
    public String addSource(
            String name,
            String url) {
        return controller.addSource(name, url) + "";
    }

    @ShellMethod("Finds a source by name.")
    public String findSourceByName(String name) {
        return StringUtils.join(controller.findSourceByName(name), "\n");
    }

    @ShellMethod("Finds a source by class name.")
    public String findSourceById(String name) {
        return StringUtils.join(controller.findSourceByName(name), "\n");
    }

    @ShellMethod("Lists all sources.")
    public String listSources() {
        return StringUtils.join(controller.listSources(), "\n");
    }

}
