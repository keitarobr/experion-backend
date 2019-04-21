package br.ufsc.ppgcc.experion.backend.service;

import br.ufsc.ppgcc.experion.backend.security.model.Roles;
import br.ufsc.ppgcc.experion.backend.security.model.User;
import br.ufsc.ppgcc.experion.backend.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(String email, String password) {
        if (users.findByUsername(email).isPresent()) {
            return null;
        } else {
            return this.users.save(User.builder()
                    .username(email)
                    .password(this.passwordEncoder.encode(password))
                    .roles(Arrays.asList(Roles.USER))
                    .build()
            );
        }
    }

    public User createAdmin(String email, String password) {
        if (users.findByUsername(email).isPresent()) {
            return null;
        } else {
            return this.users.save(User.builder()
                    .username(email)
                    .password(this.passwordEncoder.encode(password))
                    .roles(Arrays.asList(Roles.USER, Roles.ADMIN))
                    .build()
            );
        }
    }

    public boolean deleteUser(String email) {
        if (! users.findByUsername(email).isPresent()) {
            return false;
        } else {
            this.users.delete(users.findByUsername(email).get());
            return true;
        }
    }

    public List<User> listUsers() {
        return users.findAll();
    }

}
