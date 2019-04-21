package br.ufsc.ppgcc.experion.backend.service;

import br.ufsc.ppgcc.experion.backend.model.repository.ExpertRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.ExpertiseRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.ProfileBuilderRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.ProfileRepository;
import br.ufsc.ppgcc.experion.model.expert.Expert;
import br.ufsc.ppgcc.experion.view.expertise.Expertise;
import br.ufsc.ppgcc.experion.view.profile.Profile;
import br.ufsc.ppgcc.experion.view.profile.builder.ProfileBuilder;
import br.ufsc.ppgcc.experion.view.profile.builder.engine.ProfileBuilderEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProfileService {

    @Autowired
    ExpertRepository expertRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileBuilderRepository profileBuilderRepository;

    public ProfileBuilder addProfileBuilder(
            String name,
            String fullClassName) {

        ProfileBuilder builder = new ProfileBuilder();
        builder.setName(name);
        builder.setEngineClassName(fullClassName);
        return profileBuilderRepository.save(builder);
    }

    public List<ProfileBuilder> listProfileBuilders() {
        return (List<ProfileBuilder>) profileBuilderRepository.findAll();
    }

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

    public List<Profile> listProfiles(String expertIdentification) {
        Expert expert = expertRepository.findByIdentification(expertIdentification);
        return profileRepository.getByExpert(expert);
    }

}
