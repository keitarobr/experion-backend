package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.model.expert.Expert;
import br.ufsc.ppgcc.experion.view.profile.Profile;
import br.ufsc.ppgcc.experion.view.profile.builder.ProfileBuilder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {
    List<Profile> getByExpert(Expert expert);
    void deleteByExpertAndBuilder(Expert expert, ProfileBuilder profile);
    void deleteByExpert(Expert expert);
}
