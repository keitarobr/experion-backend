package br.ufsc.ppgcc.experion.backend.shell;

import br.ufsc.ppgcc.experion.backend.security.model.Roles;
import br.ufsc.ppgcc.experion.backend.security.model.User;
import br.ufsc.ppgcc.experion.backend.security.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ShellComponent
public class UserCommands {

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder passwordEncoder;


    @ShellMethod("Creates a user.")
    public String createUser(String email, String password) {
        if (users.findByUsername(email).isPresent()) {
            return "User already exists!";
        } else {
            this.users.save(User.builder()
                    .username(email)
                    .password(this.passwordEncoder.encode(password))
                    .roles(Arrays.asList(Roles.USER))
                    .build()
            );
            return "User created";
        }
    }

    @ShellMethod("Creates an administrator.")
    public String createAdmin(String email, String password) {
        if (users.findByUsername(email).isPresent()) {
            return "User already exists!";
        } else {
            this.users.save(User.builder()
                    .username(email)
                    .password(this.passwordEncoder.encode(password))
                    .roles(Arrays.asList(Roles.USER, Roles.ADMIN))
                    .build()
            );
            return "Administrator created";
        }
    }

    @ShellMethod("Deletes a user.")
    public String deleteUser(String email) {
        if (! users.findByUsername(email).isPresent()) {
            return "User does not exist!";
        } else {
            this.users.delete(users.findByUsername(email).get());
            return "User deleted";
        }
    }

    @ShellMethod("Lists users.")
    public String listUsers() {
        return StringUtils.join(users.findAll().stream().map(user -> user.getUsername()).collect(Collectors.toList()), "\n");
    }
}
