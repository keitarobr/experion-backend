package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.service.SourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SourceCommands {

    @Autowired
    private SourceService service;

    @ShellMethod("Adds a source.")
    public String addSource(
            String name,
            String url) {
        return service.addSource(name, url) + "";
    }

    @ShellMethod("Finds a source by name.")
    public String findSourceByName(String name) {
        return StringUtils.join(service.findSourceByName(name), "\n");
    }

    @ShellMethod("Finds a source by class name.")
    public String findSourceById(String name) {
        return StringUtils.join(service.findSourceByName(name), "\n");
    }

    @ShellMethod("Lists all sources.")
    public String listSources() {
        return StringUtils.join(service.listSources(), "\n");
    }

}
