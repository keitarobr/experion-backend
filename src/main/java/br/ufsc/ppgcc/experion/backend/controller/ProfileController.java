package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.model.repository.ExpertRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.ExpertiseRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.ProfileRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.ProfileBuilderRepository;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import br.ufsc.ppgcc.experion.view.expertise.Expertise;
import br.ufsc.ppgcc.experion.view.profile.Profile;
import br.ufsc.ppgcc.experion.view.profile.builder.ProfileBuilder;
import br.ufsc.ppgcc.experion.view.profile.builder.engine.ProfileBuilderEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

@RestController
@Transactional
public class ProfileController {

    @Autowired
    ExpertRepository expertRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileBuilderRepository profileBuilderRepository;

    @PostMapping("/profile/add-profile-builder")
    @PreAuthorize("hasRole('ADMIN')")
    public ProfileBuilder addProfileBuilder(
            String name,
            String fullClassName) {

        ProfileBuilder builder = new ProfileBuilder();
        builder.setName(name);
        builder.setEngineClassName(fullClassName);
        return profileBuilderRepository.save(builder);
    }

    @GetMapping("/profile/list-profile-builders")
    @PreAuthorize("permitAll()")
    public List<ProfileBuilder> listProfileBuilders() {
        return (List<ProfileBuilder>) profileBuilderRepository.findAll();
    }

    @PostMapping("/profile/generate-profiles")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESEARCHER')")
    public Set<Profile> generateProfiles(String expertIdentification, String builderName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        ProfileBuilder builderRegistration = profileBuilderRepository.findByName(builderName);

        ProfileBuilderEngine builder = builderRegistration.getEngine();

        Set<Expertise> expertise = expertiseRepository.findByExpert(expert);

        Set<Profile> profiles = builder.buildProfiles(expertise);
        profileRepository.deleteByExpertAndBuilder(expert, builderRegistration);

        for (Profile prof : profiles) {
            prof.setExpertise(expertise);
            prof.setBuilder(builderRegistration);
            profileRepository.save(prof);
        }

        return profiles;
    }

    @GetMapping("/profile/list-profiles")
    @PreAuthorize("permitAll()")
    public List<Profile> listProfiles(String expertIdentification) {
        Expert expert = expertRepository.findByIdentification(expertIdentification);

        return profileRepository.getByExpert(expert);
    }

}
