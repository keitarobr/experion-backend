package br.ufsc.ppgcc.experion.backend.controller;

import br.ufsc.ppgcc.experion.backend.service.SourceService;
import br.ufsc.ppgcc.experion.extractor.source.EvidenceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sources")
public class SourceController {

    @Autowired
    SourceService service;

    @PostMapping("/sources/add")
    @PreAuthorize("hasRole('ADMIN')")
    public EvidenceSource addSource(
            String name,
            String url) {
        return service.addSource(name, url);
    }

    @GetMapping("/sources/find-by-name")
    @PreAuthorize("permitAll()")
    public List<EvidenceSource> findSourceByName(String name) {
        return service.findSourceByName(name);
    }

    @GetMapping("find-all")
    @PreAuthorize("permitAll()")
    public List<EvidenceSource> listSources() {
        return service.listSources();
    }

}
