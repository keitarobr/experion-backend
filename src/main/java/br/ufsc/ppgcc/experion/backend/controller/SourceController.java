package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.model.repository.EvidenceSourceInputRepository;
import br.ufsc.ppgcc.experion.backend.model.repository.EvidenceSourceRepository;
import br.ufsc.ppgcc.experion.extractor.source.EvidenceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/sources")
public class SourceController {

    @Autowired
    EvidenceSourceRepository repository;

    @Autowired
    EvidenceSourceInputRepository inputRepository;

    @PostMapping("/sources/add")
    @PreAuthorize("hasRole('ADMIN')")
    public EvidenceSource addSource(
            String name,
            String url) {
        EvidenceSource source = new EvidenceSource(name);
        source.setUrl(url);
        source = repository.save(source);
        return source;
    }

    @GetMapping("/sources/find-by-name")
    @PreAuthorize("permitAll()")
    public List<EvidenceSource> findSourceByName(String name) {
        return repository.findByNameContaining(name);
    }

    @GetMapping("find-all")
    @PreAuthorize("permitAll()")
    public List<EvidenceSource> listSources() {
        return (List<EvidenceSource>)repository.findAll();
    }

}
