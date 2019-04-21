package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.service.ProfileService;
import br.ufsc.ppgcc.experion.view.profile.Profile;
import br.ufsc.ppgcc.experion.view.profile.builder.ProfileBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

@RestController
public class ProfileController {

    @Autowired
    ProfileService service;

    @PostMapping("/profile/add-profile-builder")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProfileBuilder addProfileBuilder(
            String name,
            String fullClassName) {
        return service.addProfileBuilder(name, fullClassName);
    }

    @GetMapping("/profile/list-profile-builders")
    @PreAuthorize("permitAll()")
    public List<ProfileBuilder> listProfileBuilders() {
        return service.listProfileBuilders();
    }

    @PostMapping("/profile/generate-profiles")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RESEARCHER')")
    public Set<Profile> generateProfiles(String expertIdentification, String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return service.generateProfiles(expertIdentification, builderName);
    }

    @GetMapping("/profile/list-profiles")
    @PreAuthorize("permitAll()")
    public List<Profile> listProfiles(String expertIdentification) {
        return service.listProfiles(expertIdentification);
    }

}
