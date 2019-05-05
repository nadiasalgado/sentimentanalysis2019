package com.sentimentanalysis.project.web.rest;
import com.sentimentanalysis.project.domain.Search;
import com.sentimentanalysis.project.repository.SearchRepository;
import com.sentimentanalysis.project.web.rest.errors.BadRequestAlertException;
import com.sentimentanalysis.project.web.rest.util.HeaderUtil;
import com.sentimentanalysis.project.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Search.
 */
@RestController
@RequestMapping("/api")
public class SearchResource {

    private final Logger log = LoggerFactory.getLogger(SearchResource.class);

    private static final String ENTITY_NAME = "search";

    private final SearchRepository searchRepository;

    public SearchResource(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    /**
     * POST  /searches : Create a new search.
     *
     * @param search the search to create
     * @return the ResponseEntity with status 201 (Created) and with body the new search, or with status 400 (Bad Request) if the search has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/searches")
    public ResponseEntity<Search> createSearch(@RequestBody Search search) throws URISyntaxException {
        log.debug("REST request to save Search : {}", search);
        if (search.getId() != null) {
            throw new BadRequestAlertException("A new search cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Search result = searchRepository.save(search);
        return ResponseEntity.created(new URI("/api/searches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /searches : Updates an existing search.
     *
     * @param search the search to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated search,
     * or with status 400 (Bad Request) if the search is not valid,
     * or with status 500 (Internal Server Error) if the search couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/searches")
    public ResponseEntity<Search> updateSearch(@RequestBody Search search) throws URISyntaxException {
        log.debug("REST request to update Search : {}", search);
        if (search.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Search result = searchRepository.save(search);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, search.getId().toString()))
            .body(result);
    }

    /**
     * GET  /searches : get all the searches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of searches in body
     */
    @GetMapping("/searches")
    public ResponseEntity<List<Search>> getAllSearches(Pageable pageable) {
        log.debug("REST request to get a page of Searches");
        Page<Search> page = searchRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/searches");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /searches/:id : get the "id" search.
     *
     * @param id the id of the search to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the search, or with status 404 (Not Found)
     */
    @GetMapping("/searches/{id}")
    public ResponseEntity<Search> getSearch(@PathVariable Long id) {
        log.debug("REST request to get Search : {}", id);
        Optional<Search> search = searchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(search);
    }

    /**
     * DELETE  /searches/:id : delete the "id" search.
     *
     * @param id the id of the search to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/searches/{id}")
    public ResponseEntity<Void> deleteSearch(@PathVariable Long id) {
        log.debug("REST request to delete Search : {}", id);
        searchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
