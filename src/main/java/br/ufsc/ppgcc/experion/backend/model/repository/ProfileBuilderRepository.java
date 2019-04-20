package br.ufsc.ppgcc.experion.backend.model.repository;

import br.ufsc.ppgcc.experion.view.profile.builder.ProfileBuilder;
import org.springframework.data.repository.CrudRepository;

public interface ProfileBuilderRepository extends CrudRepository<ProfileBuilder, Integer> {
    ProfileBuilder findByName(String name);

}
