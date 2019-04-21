package br.ufsc.ppgcc.experion.backend.service;

import br.ufsc.ppgcc.experion.backend.model.repository.EvidenceSourceInputRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.EvidenceSourceRepository;
import br.ufsc.ppgcc.experion.extractor.source.EvidenceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SourceService {

    @Autowired
    EvidenceSourceRepository repository;

    @Autowired
    EvidenceSourceInputRepository inputRepository;

    public EvidenceSource addSource(
            String name,
            String url) {
        EvidenceSource source = new EvidenceSource(name);
        source.setUrl(url);
        source = repository.save(source);
        return source;
    }

    public List<EvidenceSource> findSourceByName(String name) {
        return repository.findByNameContaining(name);
    }

    public List<EvidenceSource> listSources() {
        return (List<EvidenceSource>)repository.findAll();
    }


}
