package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.security.model.User;
import br.ufsc.ppgcc.experion.backend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
public class UserCommands {

    @Autowired
    UserService service;

    @ShellMethod("Creates a user.")
    public String createUser(String email, String password) {
        User user = service.createUser(email, password);
        if (user == null) {
            return "User already exists!";
        } else {
            return "User created";
        }
    }

    @ShellMethod("Creates an administrator.")
    public String createAdmin(String email, String password) {
        User user = service.createAdmin(email, password);
        if (user == null) {
            return "User already exists!";
        } else {
            return "Administrator created";
        }
    }

    @ShellMethod("Deletes a user.")
    public String deleteUser(String email) {
        boolean deleted = service.deleteUser(email);
        if (! deleted) {
            return "User does not exist!";
        } else {
            return "User deleted";
        }
    }

    @ShellMethod("Lists users.")
    public String listUsers() {
        return StringUtils.join(service.listUsers().stream().map(user -> user.getUsername()).collect(Collectors.toList()), "\n");
    }
}
